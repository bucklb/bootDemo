package udemy.spring.bootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.stereotype.Service;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "udemy.spring.bootdemo")
//@SpringBootApplication
//@Service
public class BootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootdemoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner run( Environment environment) {
//		return (args) -> {
//			System.out.println("Booty!");
//
//		};
//	}




}
