package com.journeymanager.journeybackend.common.feature;

import com.journeymanager.journeybackend.tenant.subscription.Feature;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresFeature {

    Feature value();

}
