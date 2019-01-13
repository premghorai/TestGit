package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;
@EnableEurekaClient
@SpringBootApplication
public class ItemCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemCatalogApplication.class, args);
	}

}

 @Data
 @AllArgsConstructor
 @NoArgsConstructor
 @ToString
 @Entity
 class Item {

     public Item(String name) {
         this.name = name;
     }

     @Id
     @GeneratedValue
     private Long id;

     private String name;
 }

 @RepositoryRestResource
 interface ItemRepository extends JpaRepository<Item, Long> {}

 @Component
 class ItemInitializer implements CommandLineRunner {

     private final ItemRepository ItemRepository;

     ItemInitializer(ItemRepository itemRepository) {
        // this.itemRepository = itemRepository;
         this.ItemRepository=itemRepository;
     }

     @Override
     public void run(String... args) throws Exception {
         Stream.of("Lining", "PUMA", "Bad Boy", "Air Jordan", "Nike", "Adidas", "Reebok" )
                 .forEach(item -> ItemRepository.save(new Item(item)));

         ItemRepository.findAll().forEach(System.out::println);
     }
 }