package com.hello.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class HelloWebClientController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HelloServiceFeignClient feignClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    //call api using rest template
    @GetMapping(value = "/client/static")
    public ResponseEntity<Hello> helloV1() {
        String url = "http://localhost:3000/hello";
        Hello helloResponse = restTemplate.getForObject(url, Hello.class);
        return ResponseEntity.status(200).body(helloResponse);
    }

    @GetMapping("/")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping(value = "/client/dynamic")
    public ResponseEntity helloV2() {
        List<ServiceInstance> instances = discoveryClient.getInstances("hello-service");
        Hello helloResponse = null;
        if (instances != null && instances.size() > 0) {
            ServiceInstance serviceInstance = instances.get(0);
            System.out.println(serviceInstance.getHost() + " " + serviceInstance.getPort() + " " + serviceInstance.getServiceId());
            System.out.println(serviceInstance.getInstanceId());
            String service_Name = serviceInstance.getServiceId();
            String url = "http://" + service_Name + "/hello";
            helloResponse = restTemplate.getForObject(url,
                    Hello.class);
            System.out.println(helloResponse);
        }
        return  ResponseEntity.ok(helloResponse);

    }

    @GetMapping(value = "/client/feign")
    public ResponseEntity<Hello> helloV3() {
        Hello helloResponse = feignClient.hello().getBody();
        return ResponseEntity.status(200).body(helloResponse);
    }

    @GetMapping("/client/loadbalanced")
    public ResponseEntity<Hello> loadBalanced() {
        //accessing hello-service
        String url = "http://hello-service/hello";
        Hello helloResponse = restTemplate.getForObject(url, Hello.class);
        return ResponseEntity.status(200).body(helloResponse);
    }


}