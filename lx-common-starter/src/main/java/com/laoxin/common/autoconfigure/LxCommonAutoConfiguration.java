package com.laoxin.common.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.laoxin.core","com.laoxin.dao"})
public class LxCommonAutoConfiguration {
}
