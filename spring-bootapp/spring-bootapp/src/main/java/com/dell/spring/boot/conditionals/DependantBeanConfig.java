package com.dell.spring.boot.conditionals;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependantBeanConfig {
    @Bean
    OptionalBean optionalBean() {
        return new OptionalBean();
    }

    @Bean
    @ConditionalOnBean(OptionalBean.class)
        //create this bean only if OptionalBean is available , if not dont
    DependantBean dependantBean() {
        return new DependantBean();
    }

    static class DependantBean {
        public DependantBean() {
            System.out.println("DependantBean is ready");
        }
    }

    static class OptionalBean {
        public OptionalBean() {
           System.out.println("OptionalBean is ready");

        }
    }
}
