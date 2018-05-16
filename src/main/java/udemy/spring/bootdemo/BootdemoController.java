package udemy.spring.bootdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

// stuff to allow access to other URLs (as long as they return straight text)
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;
import udemy.spring.bootdemo.Domain.Quote;


@RequestMapping("")
@RestController
public class BootdemoController {

    @Value("${server.port}")
    int tomcatPort;

    //
    // This stuff is all about returning OBJECTS (incl Strings) directly, rather than via an Entity/httpResponse
    //


    // in case no link is requested, give them something to look at.  A clue as to what will respond would be good
    @RequestMapping(value="",method = RequestMethod.GET)
    public String sayHome() {
        String s = "There's no place like it\n\n"+
                "stringTest: get      : returns simple string\n"+
                "jsonTest  : get      : returns a basic json object\n"+
                "asJson    : post/get : works in json\n"+
                "asString  : post/get : works in String\n"+
                "remote    : post/get : stuff from another server (localhost:9090/mvc/)\n"+
                "headed/.. : post/get : other endpoints that relate to quote/entity etc\n";
        return s;
    }

    // Pass back quote as simple string (that might contain a complex payload)
    @RequestMapping(value="/quote", method = RequestMethod.GET)
    public String getQuote(){
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject(
                "http://gturnquist-quoters.cfapps.io/api/random",
                Quote.class);
        return quote.toString();
    }

    // Play with returning JSon object, directly. GET
    @RequestMapping(value="/asJson",method = RequestMethod.GET)
    public JSONObject sayJson() {
        JSONObject json=new JSONObject();
        json.put("name","Namey McNameFace");
        return json;
    }

    // Play with returning JSon object, directly. POST
    @RequestMapping(value="/asJson",method = RequestMethod.POST)
    public JSONObject postJson() {

        // How do I get to the payload?
        JSONObject json=new JSONObject();
        json.put("number","69");
        return json;
    }

    // Play with getting json as response from a GET and POST via an overall call
    @RequestMapping(value="/jsonTest",method = RequestMethod.GET)
    public String hearJson() {
        // Want to try and grab stuff from elsewhere.  If the call results in text then we're OK.  Anything beyond that ...
        RestTemplate restTemplate = new RestTemplate();

        // Use GET and record the returned string
        JSONObject getResult =
                restTemplate.getForObject(
                        "http://localhost:"+tomcatPort+"/asJson/", JSONObject.class, "42", "21"
                );

        JSONObject postResult =
                restTemplate.postForObject(
                        "http://localhost:"+tomcatPort+"/asJson/",null, JSONObject.class, "42", "21"
                );

        // Build something vaguely elegant that might be displayed nicely (assumes they can do prettyPrint
        JSONObject json=new JSONObject();
        json.put("get",getResult);
        json.put("post",postResult);
        JSONObject resp=new JSONObject();
        resp.put("/jsonTest",json);

        return resp.toJSONString();
    }

    // make sure we can use other URLs on this site/app by grabbing stuff from home page
    @RequestMapping(value="/stringTest",method = RequestMethod.GET)
    public String sayReflect() {

        // Want to try and grab stuff from elsewhere.  If the call results in text then we're OK.  Anything beyond that ...
        RestTemplate restTemplate = new RestTemplate();

        // Use GET and record the returned string
        String getResult =
                restTemplate.getForObject(
                        "http://localhost:"+tomcatPort+"/asString", String.class
                );
        // Use POST and record the returned string
        String postResult =
                restTemplate.postForObject(
                        "http://localhost:"+tomcatPort+"/asString",null, String.class
                );
        JSONObject json=new JSONObject();
        json.put("post",postResult);
        json.put("get",getResult);

        JSONObject resp=new JSONObject();
        resp.put("/stringTest",json);

//        return "On reflection : " + getResult + " <-> " + postResult;
        return resp.toJSONString();
    }

    // make sure we can use other URLs on this site/app by grabbing stuff from home page
    @RequestMapping(value="/asString",method = RequestMethod.POST)
    public String postReflect() {
        return "Posted a reflection";
    }
    // make sure we can use other URLs on this site/app by grabbing stuff from home page
    @RequestMapping(value="/asString",method = RequestMethod.GET)
    public String getReflect() {
        return "Got a reflection";
    }



    // Make sure we can grab stuff from a remote site (of mine).  Superseded by the quote & quoteTest stuff to a great extent
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
