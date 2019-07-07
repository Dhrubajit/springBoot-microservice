package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.EurekaClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MicroServiceAApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceAApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}


@RestController
class MyMessageA{

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EurekaClient eurekaclient;
	@Autowired
	MicroBProxyService microBProxyService;

	@RequestMapping("/myMessageA")
	public String getMessage() {
		//Application app = eurekaclient.getApplication("microB");
		//InstanceInfo info= app.getInstances().get(0);
		//String message= restTemplate().getForObject("http://"+info.getIPAddr()+":"+info.getPort()+"/myMessageB", String.class);

		String message= microBProxyService.getMessage();		
		return message;
	}
}

@FeignClient(name="microB")
interface MicroBProxyService{

	@RequestMapping("/myMessageB")
	String getMessage();

}