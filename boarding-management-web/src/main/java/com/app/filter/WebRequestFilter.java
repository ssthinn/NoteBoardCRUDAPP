package com.app.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class WebRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {

			var loginUser = request.getSession().getAttribute("loginUser");

			// check session login user data
			// if there is no active session, filter will redirect to login page
			if (null == loginUser && !(request.getServletPath().equals("/index"))
					&& !(request.getServletPath().equals("/"))) {
				logger.debug("No active login user");
				response.sendRedirect("/index/401");
				return;
			}

		} catch (Exception e) {
			logger.error("Fail to filter a web request. Error : " + e.getMessage());
			response.sendRedirect("/index/500");
			return;
		}

		filterChain.doFilter(request, response);

	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		AntPathMatcher pathMatcher = new AntPathMatcher();
		List<String> excludeUrlPatterns = List.of("/css/**","/js/**","/vendor/**" ,"/images/**" ,"/fonts/**","/", "/index/**", "/login/**", "/logout/**");
		return excludeUrlPatterns.stream().anyMatch(p -> pathMatcher.match(p, request.getServletPath()));

	}

}
