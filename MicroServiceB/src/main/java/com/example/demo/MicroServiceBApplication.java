package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class MicroServiceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceBApplication.class, args);
	}

}

@RestController
class MyMessagBA {

	@HystrixCommand(fallbackMethod = "reliable")
	@RequestMapping("/myMessageB")
	public String getMessage() {

		int num = 10 / 0;
		return "Inside MyMessageB";
	}

	public String reliable() {
		return "Cloud Native Java (O'Reilly)";
	}
}