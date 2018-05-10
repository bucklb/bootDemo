package udemy.spring.bootdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// stuff to allow access to other URLs (as long as they return straight text)
import org.springframework.web.client.RestTemplate;

@RequestMapping("")
@RestController
public class BootdemoController {

    // in case no link is requested, give them something to look at
    @RequestMapping(value="",method = RequestMethod.GET)
    public String sayHome() {
        return "There's no place like it";
    }

    // make sure we can use other URLs on this site/app by grabbing stuff from home page
    @RequestMapping(value="/reflect",method = RequestMethod.GET)
    public String sayReflect() {

        // Want to try and grab stuff from elsewhere.  If the call results in text then we're OK.  Anything beyond that ...
        RestTemplate restTemplate = new RestTemplate();
        String result =
                restTemplate.getForObject(
                        "http://localhost:8080/",
                        String.class,"42", "21"
                );

        return "On reflection : " + result;
    }

    // Make sure we can grab stuff from a remote site
    @RequestMapping(value="/remote",method = RequestMethod.GET)
    public String sayRemote() {

        // Want to try and grab stuff from elsewhere.  If the call results in text then we're OK.  Anything beyond that ...
        RestTemplate restTemplate = new RestTemplate();
        String result =
                restTemplate.getForObject(
                        "http://localhost:9090/webapp/",
                        String.class,"42", "21"
                );

        /*      Failing code from Spring's website
        String result2 =
                restTemplate.getForObject(
                "http://example.com/hotels/{hotel}/bookings/{booking}",
                        String.class, "42", "21"
        );
        */

        return "Remote URL says : ->" + result + "<-"; // ->"+result2;
    }
}
