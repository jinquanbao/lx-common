package com.laoxin.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        new SpringApplicationBuilder(Application.class)
                .initializers((GenericApplicationContext c) -> c.setAllowBeanDefinitionOverriding(true) )
                .run(args);

    }
}
