package com.example.remote;

import com.example.DubboRemoteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RemoteDemo {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @DubboReference(version = "${dubbo.global.version}")
    private DubboRemoteService dubboRemoteService;

    @PostConstruct
    public void init() {
        callDubboProvider();
    }

    public void callDubboProvider() {
        String result = dubboRemoteService.call("dubbo-spring-boot-provider");
        logger.info("dubbo远程调用完成，返回结果：{}", result);
    }
}
