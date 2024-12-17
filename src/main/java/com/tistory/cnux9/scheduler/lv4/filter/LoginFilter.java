package com.tistory.cnux9.scheduler.lv4.filter;

import com.tistory.cnux9.scheduler.lv4.util.Const;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class LoginFilter implements Filter {

//    private static final String[] WHITE_LIST = {"/", "/user/signup", "/login", "/logout", "/users/login"};
    private static final String[] WHITE_LIST = {
        "/users",
        "/users/login"
    };
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);

//            if (session == null || session.getAttribute("sessionKey") == null) {
            if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                System.out.println(session == null);
                throw new RuntimeException("로그인 해주세요.");
            }

            log.info("로그인에 성공했습니다.");
        }

        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
