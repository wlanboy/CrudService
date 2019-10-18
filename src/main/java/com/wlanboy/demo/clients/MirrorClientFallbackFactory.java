package com.wlanboy.demo.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wlanboy.demo.model.HelloParameters;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@Component
public class MirrorClientFallbackFactory implements FallbackFactory<MirrorClient> {

	private static final Logger logger = LoggerFactory.getLogger(MirrorClientFallbackFactory.class);
	
	@Override
	public MirrorClient create(Throwable cause) {
	     String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status()) : "";
	     String exception = cause instanceof FeignException ? ((FeignException) cause).getMessage() : "";

	     return ((HelloParameters mirrorObject) -> {
				logger.error("Object: {} caused httpStatus {}", mirrorObject,httpStatus);
				mirrorObject.setStatus(httpStatus);
				mirrorObject.setTarget(exception);
				return mirrorObject;
			}
	    );
	}

}
