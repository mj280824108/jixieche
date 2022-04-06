package com.weiwei.jixieche.resolver;

import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.jwt.JwtUtil;
import com.weiwei.jixieche.jwt.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 注入用户对象
 */
@Component
public class AuthHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(AuthorizationUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {

		String jwt = request.getHeader("token");
		User user = jwtUtil.getSubject(jwt);
		AuthorizationUser authUser = new AuthorizationUser();
		authUser.setUserId(user.getUserId());
		authUser.setRoleId(user.getRoleId());
		authUser.setThirdId(user.getThirdId());
		return authUser;
	}
}
