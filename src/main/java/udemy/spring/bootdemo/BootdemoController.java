package udemy.spring.bootdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// stuff to allow access to other URLs (as long as they return straight text)
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;


@RequestMapping("")
@RestController
public class BootdemoController {

    // in case no link is requested, give them something to look at
    @RequestMapping(value="",method = RequestMethod.GET)
    public String sayHome() {
        return "There's no place like it";
    }

    // Play with returning JSon
    @RequestMapping(value="/json",method = RequestMethod.GET)
    public JSONObject sayJson() {
        JSONObject json=new JSONObject();
        json.put("name","Namey McNameFace");
        return json;
    }

    // Play with returning JSon
    @RequestMapping(value="/json",method = RequestMethod.POST)
    public JSONObject postJson() {
        JSONObject json=new JSONObject();
        json.put("number","69");
        return json;
    }

    // Play with getting json as response from a GET and POST
    @RequestMapping(value="/echo",method = RequestMethod.GET)
    public String hearJson() {
        // Want to try and grab stuff from elsewhere.  If the call results in text then we're OK.  Anything beyond that ...
        RestTemplate restTemplate = new RestTemplate();

        // Use GET and record the returned string
        JSONObject getResult =
                restTemplate.getForObject(
                        "http://localhost:8080/json/", JSONObject.class, "42", "21"
                );

        JSONObject postResult =
                restTemplate.postForObject(
                        "http://localhost:8080/json/",null, JSONObject.class, "42", "21"
                );

        return getResult.toJSONString() + " <->  " + postResult.toJSONString();
    }




    // make sure we can use other URLs on this site/app by grabbing stuff from home page
    @RequestMapping(value="/reflect",method = RequestMethod.GET)
    public String sayReflect() {

        // Want to try and grab stuff from elsewhere.  If the call results in text then we're OK.  Anything beyond that ...
        RestTemplate restTemplate = new RestTemplate();

        // Use GET and record the returned string
        String getResult =
                restTemplate.getForObject(
                        "http://localhost:8080/",
                        String.class,"42", "21"
                );
        // Use POST and record the returned string
        String postResult =
                restTemplate.postForObject(
                        "http://localhost:8080/postman",null,
                        String.class,"42", "21"
                );

        return "On reflection : " + getResult + " <-> " + postResult;
    }

    // make sure we can use other URLs on this site/app by grabbing stuff from home page
    @RequestMapping(value="/postman",method = RequestMethod.POST)
    public String postReflect() {
        return "Posted a reflection";
    }

    // Make sure we can grab stuff from a remote site
    @RequestMapping(value="/remote",method = RequestMethod.GET)
    public String sayRemote() {

        // Want to try and grab stuff from elsewhere.  If the call results in text then we're OK.  Anything beyond that ...
        RestTemplate restTemplate = new RestTemplate();
        String result =
                restTemplate.getForObject(
                        "http://localhost:9090/mvc/",
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
