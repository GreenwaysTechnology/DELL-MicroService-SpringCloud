package com.dell.spring.boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan
//@EnableAutoConfiguration
//@SpringBootConfiguration
@SpringBootApplication
public class SpringBootappApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootappApplication.class, args);

//        SpringApplication application = new SpringApplication(SpringBootappApplication.class);
//        application.setWebApplicationType(WebApplicationType.NONE);
//        application.setBannerMode(Banner.Mode.OFF);
//        ConfigurableApplicationContext context = application.run(args);
////		HelloWorld helloWorld = context.getBean(HelloWorld.class);
////		System.out.println(helloWorld.sayHello());

        //commandLine runner as lambda expresison.
        CommandLineRunner commandLineRunner = args1 -> {
            System.out.println("Lambda initalizer");
        };
        try {
            commandLineRunner.run("dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
