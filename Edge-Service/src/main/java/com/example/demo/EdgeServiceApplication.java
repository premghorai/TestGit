package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.Item.ItemClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class EdgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeServiceApplication.class, args);
	}

}

 
class Item {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@FeignClient("item-catalog-service")
	interface ItemClient {

		@GetMapping("/items")
		Resources<Item> readItems();
	}
}

@RestController
  class GoodItemApiAdapterRestController {
	
	

	    private final ItemClient itemClient;

	    public GoodItemApiAdapterRestController(ItemClient ItemClient) {
	        this.itemClient = ItemClient;
	    }

	    @GetMapping("/top-brands")
	    public Collection<Item> goodItems() {
	        return itemClient.readItems()
	                .getContent()
	                .stream()
	                .filter(this::isGreat)
	                .collect(Collectors.toList());
	    }

	    private boolean isGreat(Item item) {
	        return !item.getName().equals("Nike") &&
	                !item.getName().equals("Adidas") &&
	                !item.getName().equals("Reebok");
	    }

}

