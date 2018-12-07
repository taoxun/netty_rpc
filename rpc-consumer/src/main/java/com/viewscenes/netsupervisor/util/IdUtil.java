package com.viewscenes.netsupervisor.util;
/**
 *

 * @ClassName: IdUtil

 * @Description: TODO(ID生成类，生成适用于各类场景的ID)

 * @author shiqizhen

 * @date 2017-9-15 上午10:05:13

 *
 */
public class IdUtil {

    private final static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
    /**
     * 消息ID
     * @return
     */
    public static String getId(){
        return String.valueOf(idWorker.nextId());
    }
}
