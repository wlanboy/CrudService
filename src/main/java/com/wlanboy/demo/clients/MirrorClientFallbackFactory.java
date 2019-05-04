package com.wlanboy.demo.clients;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.wlanboy.demo.model.HelloParameters;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@Component
public class MirrorClientFallbackFactory implements FallbackFactory<MirrorClient> {

	private static final Logger logger = Logger.getLogger(MirrorClientFallbackFactory.class.getCanonicalName());
	
	@Override
	public MirrorClient create(Throwable cause) {
	     String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status()) : "";
	     String exception = cause instanceof FeignException ? ((FeignException) cause).getMessage() : "";

	     return new MirrorClient() {
			@Override
			public HelloParameters postMirrorRequest(HelloParameters mirrorObject) {
				logger.log(Level.SEVERE,String.format("Object: %s caused httpStatus %s.", mirrorObject,httpStatus));
				mirrorObject.setStatus(httpStatus);
				mirrorObject.setTarget(exception);
				return mirrorObject;
			}
	    };
	}

}
