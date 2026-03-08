package com.journeymanager.journeybackend.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String path = request.getRequestURI();

        if (path.startsWith("/api/auth")) {
            return true;
        }

        String tenant = (String) request.getAttribute("tenant");

        if (tenant == null) {
            throw new RuntimeException("Tenant missing in JWT token");
        }

        return true;
    }
}
