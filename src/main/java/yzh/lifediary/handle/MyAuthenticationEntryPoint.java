package yzh.lifediary.handle;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import yzh.lifediary.util.MyUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//没有权限的时候时候，不管找不找的到
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (e.getMessage().equals("Full authentication is required to access this resource")) {
            MyUtils.jsonResponse(httpServletResponse, -1, "没有权限，请先登录", "");
            return;
        }
        MyUtils.jsonResponse(httpServletResponse, -1, e.getMessage(), "");

    }
}
