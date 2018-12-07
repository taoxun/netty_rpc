package com.viewscenes.netsupervisor.configurer.rpc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by MACHENIKE on 2018-12-03.
 */
@Component
public class RpcScannerConfigurer implements BeanDefinitionRegistryPostProcessor {

    String basePackage = "com.viewscenes.netsupervisor.service";

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathRpcScanner scanner = new ClassPathRpcScanner(beanDefinitionRegistry);

        scanner.setAnnotationClass(null);
        scanner.registerFilters();

        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
