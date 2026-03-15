package com.journeymanager.journeybackend.config;

import com.journeymanager.journeybackend.common.feature.FeatureInterceptor;
import com.journeymanager.journeybackend.tenant.TenantInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;
    private final FeatureInterceptor featureInterceptor;

    public WebConfig(
            TenantInterceptor tenantInterceptor,
            FeatureInterceptor featureInterceptor) {

        this.tenantInterceptor = tenantInterceptor;
        this.featureInterceptor = featureInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Existing tenant isolation
        registry.addInterceptor(tenantInterceptor)
                .addPathPatterns("/api/**");

        // New premium feature guard
        registry.addInterceptor(featureInterceptor)
                .addPathPatterns("/api/**");
    }
}