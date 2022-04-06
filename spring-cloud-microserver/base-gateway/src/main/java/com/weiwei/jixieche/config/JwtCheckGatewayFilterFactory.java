package com.weiwei.jixieche.config;

/**
 * @ClassName JwtCheckGatewayFilterFactory
 * @Description JWT验证的过滤器
 * @Author houji
 * @Date 2019/5/23 14:31
 * @Version 1.0.1
 **/

import com.alibaba.fastjson.JSON;


import com.google.common.base.Strings;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.jwt.JwtConstant;
import com.weiwei.jixieche.jwt.JwtUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.stream.Stream;

@Slf4j
public class JwtCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtCheckGatewayFilterFactory.Config> {


	@Value("${auth.skip.urls}")
	private String[] skipAuthUrls;

	@Resource
	private JwtUtil jwtUtil;

	public JwtCheckGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			//跳过不需要验证的路径
			String url = exchange.getRequest().getURI().getPath();
			log.info("访问地址: == " +url);
			boolean isAuth = Stream.of(skipAuthUrls).anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
			if (isAuth) {
				return chain.filter(exchange);
			}
			//不合法-----start
			//(响应未登录的异常的准备)
			ServerHttpResponse response = exchange.getResponse();
			//设置headers
			HttpHeaders httpHeaders = response.getHeaders();
			httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
			httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");

			String token = exchange.getRequest().getHeaders().getFirst("token");
			//token不为空的时候
/*			if(!Strings.isNullOrEmpty(token)){
				//判断token是否过期，过期则处理
				Claims c = null;
				try {
					c = jwtUtil.parseJWT(token);
				} catch (ExpiredJwtException ex) {
					//token过期异常捕捉
					DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSON.toJSONString(new ResponseMessage(ErrorCodeConstants.TOKEN_EXPIRED_TIME.getId(), "token已经过期,请重新登录")).getBytes());
					return response.writeWith(Mono.just(bodyDataBuffer));
				}

				//校验jwtToken的合法性,防止签名被篡改
				*//*if (c.getIssuer().equals(JwtConstant.ISSUER)) {
					DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSON.toJSONString(new ResponseMessage(ErrorCodeConstants.TOKEN_CHANGE.getId(), "token签名被篡改")).getBytes());
					return response.writeWith(Mono.just(bodyDataBuffer));
				}*//*
			}else{
				//token为空操作
				DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSON.toJSONString(new ResponseMessage(ErrorCodeConstants.TOKEN_EMPTY.getId(), "登录token为空")).getBytes());
				return response.writeWith(Mono.just(bodyDataBuffer));
			}*/
			// 合法并封装下一个请求
			return chain.filter(exchange);

		};
	}


	public static class Config {
		//Put the configuration properties for your filter here
	}

}
