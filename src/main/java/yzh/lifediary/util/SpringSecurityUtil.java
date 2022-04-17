package yzh.lifediary.util;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSession;

public class SpringSecurityUtil {

    public static String currentUser(HttpSession session) {
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return ((UserDetails)securityContext.getAuthentication().getPrincipal()).getUsername();
    }
}