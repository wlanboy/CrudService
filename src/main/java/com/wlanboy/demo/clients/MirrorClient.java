package com.wlanboy.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.wlanboy.demo.model.HelloParameters;

import feign.Headers;

@FeignClient(name = "mirrorservice", url = "${feign.client.url.mirrorserviceurl}", fallbackFactory = MirrorClientFallbackFactory.class)
public interface MirrorClient {

    @PostMapping(value = "/mirror")
    @Headers("Content-Type: application/json")
    HelloParameters postMirrorRequest(HelloParameters mirrorObject);
}
