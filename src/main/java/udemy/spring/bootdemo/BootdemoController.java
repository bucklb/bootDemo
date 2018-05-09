package udemy.spring.bootdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class BootdemoController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "Hello, World!";
    }
}
