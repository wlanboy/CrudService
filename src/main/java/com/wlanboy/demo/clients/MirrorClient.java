package com.wlanboy.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wlanboy.demo.model.HelloParameters;

import feign.Headers;

@FeignClient(name = "mirrorservice", fallbackFactory = MirrorClientFallbackFactory.class)
public interface MirrorClient {

    @RequestMapping(method = RequestMethod.POST, value = "/mirror")
    @Headers("Content-Type: application/json")
    HelloParameters postMirrorRequest(HelloParameters mirrorObject);
}
