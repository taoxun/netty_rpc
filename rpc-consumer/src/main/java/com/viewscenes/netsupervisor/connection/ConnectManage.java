package com.viewscenes.netsupervisor.connection;

import com.viewscenes.netsupervisor.netty.client.NettyClient;
import com.viewscenes.netsupervisor.netty.client.ThreadPool;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ConnectManage{

    @Autowired
    NettyClient nettyClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private AtomicInteger roundRobin = new AtomicInteger(0);
    private CopyOnWriteArrayList<Channel> channels = new CopyOnWriteArrayList<>();
    private Map<SocketAddress, Channel> channelNodes = new ConcurrentHashMap<>();

    public  Channel chooseChannel() {
        if (channels.size()>0) {
            int size = channels.size();
            int index = (roundRobin.getAndAdd(1) + size) % size;
            return channels.get(index);
        }else{
            return null;
        }
    }

    public synchronized void updateConnectServer(List<String> addressList){
        if (addressList.size()==0 || addressList==null){
            logger.error("没有可用的服务器节点, 全部服务节点已关闭!");
            for (final Channel channel : channels) {
                SocketAddress remotePeer = channel.remoteAddress();
                Channel handler_node = channelNodes.get(remotePeer);
                handler_node.close();
            }
            channels.clear();
            channelNodes.clear();
            return;
        }
        HashSet<SocketAddress> newAllServerNodeSet = new HashSet<>();
        for (int i = 0; i < addressList.size(); ++i) {
            String[] array = addressList.get(i).split(":");
            if (array.length == 2) {
                String host = array[0];
                int port = Integer.parseInt(array[1]);
                final SocketAddress remotePeer = new InetSocketAddress(host, port);
                newAllServerNodeSet.add(remotePeer);
            }
        }

        for (final SocketAddress serverNodeAddress : newAllServerNodeSet) {
            Channel channel = channelNodes.get(serverNodeAddress);
            if (channel!=null && channel.isOpen()){
                logger.info("当前服务节点已存在,无需重新连接.{}",serverNodeAddress);
            }else{
                connectServerNode(serverNodeAddress);
            }
        }
        for (int i = 0; i < channels.size(); ++i) {
            Channel channel = channels.get(i);
            SocketAddress remotePeer = channel.remoteAddress();
            if (!newAllServerNodeSet.contains(remotePeer)) {
                logger.info("删除失效服务节点 " + remotePeer);
                Channel channel_node = channelNodes.get(remotePeer);
                if (channel_node != null) {
                    channel_node.close();
                }
                channels.remove(channel);
                channelNodes.remove(remotePeer);
            }
        }
    }

    private void connectServerNode(SocketAddress address){
        try {
            Channel channel = nettyClient.doConnect(address);
            addChannel(channel,address);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("未能成功连接到服务器:{}",address);
        }
    }
    private void addChannel(Channel channel,SocketAddress address) {
        logger.info("加入Channel到连接管理器.{}",address);
        channels.add(channel);
        channelNodes.put(address, channel);
    }

    public void removeChannel(Channel channel){
        logger.info("从连接管理器中移除失效Channel.{}",channel.remoteAddress());
        SocketAddress remotePeer = channel.remoteAddress();
        channelNodes.remove(remotePeer);
        channels.remove(channel);
    }
}
