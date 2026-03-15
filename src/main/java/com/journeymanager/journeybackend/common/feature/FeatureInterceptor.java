package com.journeymanager.journeybackend.common.feature;

import com.journeymanager.journeybackend.tenant.subscription.Feature;
import com.journeymanager.journeybackend.tenant.subscription.FeatureAccessService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class FeatureInterceptor implements HandlerInterceptor {

    private final FeatureAccessService featureAccessService;

    public FeatureInterceptor(FeatureAccessService featureAccessService) {
        this.featureAccessService = featureAccessService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        RequiresFeature annotation =
                method.getMethodAnnotation(RequiresFeature.class);

        if (annotation == null) {
            return true;
        }

        Feature feature = annotation.value();

        if (!featureAccessService.isEnabled(feature)) {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(
                    "Feature requires subscription upgrade"
            );

            return false;
        }

        return true;
    }
}
