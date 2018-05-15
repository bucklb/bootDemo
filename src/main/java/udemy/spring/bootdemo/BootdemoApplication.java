package udemy.spring.bootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import udemy.spring.bootdemo.Domain.Quote;

// Needed so we can use the annotation
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "udemy.spring.bootdemo")
//@SpringBootApplication
//@Service
public class BootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootdemoApplication.class, args);
	}

	/* might introduce some incompatability?
	// Single instance of the RestTemplate that we can (auto)inject on demand ...
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}
	*/


	// This appears to allow a process to be called.  Looks like an ORDER can be specified too.  Note that BootRunner is #2
	// - looks like there's no guarantee that FIRST job entirely finishes before SECOND starts.
	// In theory, could use this to launch a standalone task (but not sure that's sensible use of Spring Boot)
	@Bean
	@Order(1)
	public CommandLineRunner demo() {

		// Amend to get something from external website
		// Presumably "JsonIgnoreProperties" means that it auto maps according to the object setters/getters
		System.out.println("=================================================================================");
		RestTemplate restTemplate = new RestTemplate();
		// Get an object that gets automatically set as a "Quote" object
		Quote quote = restTemplate.getForObject(
				"http://gturnquist-quoters.cfapps.io/api/random",
				Quote.class
		);
		System.out.println(quote.toString());
		System.out.println("=================================================================================");



		System.out.println("Running commandLineRunner: demo.  Argument list below ...");
		return (args) -> {
			System.out.println("Run args : " + args );
		};
	}

}
