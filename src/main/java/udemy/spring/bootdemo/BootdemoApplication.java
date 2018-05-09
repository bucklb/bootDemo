package udemy.spring.bootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "udemy.spring.bootdemo")
//@SpringBootApplication
//@Service
public class BootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootdemoApplication.class, args);
	}

	// This appears to allow a process to be called.  Looks like an ORDER can be specified too.  Note that BootRunner is #2
	// - looks like there's no guarantee that FIRST job entirely finishes before SECOND starts.
	// In theory, could use this to launch a standalone task (but not sure that's sensible use of Spring Boot)
	@Bean
	@Order(1)
	public CommandLineRunner demo() {
		System.out.println("Running commandLineRunner: demo.  Argument list below ...");
		return (args) -> {
			System.out.println("Run args : " + args );
		};
	}

}
