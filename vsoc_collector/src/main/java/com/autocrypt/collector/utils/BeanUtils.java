package com.autocrypt.collector.utils;

import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

@UtilityClass
public class BeanUtils {
    // Bean Utils for Customize DI ( return Bean by Class )
    public static Object getBean(Class<?> classType) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(classType);
    }
}
