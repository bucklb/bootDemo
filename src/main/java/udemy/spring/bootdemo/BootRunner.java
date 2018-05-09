package udemy.spring.bootdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)   // in case we add other runners, need to state an order to run them
public class BootRunner implements CommandLineRunner{

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Runner is running ...");
    }
}
