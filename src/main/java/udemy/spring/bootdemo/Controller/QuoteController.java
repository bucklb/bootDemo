package udemy.spring.bootdemo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Quo;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// stuff to allow access to other URLs (as long as they return straight text)
import org.springframework.web.client.RestTemplate;
import udemy.spring.bootdemo.Domain.*;
import udemy.spring.bootdemo.Service.QuoteService;


import javax.validation.Valid;
import java.lang.reflect.Type;
import java.net.URI;

/**
 *  @Author     me
 *  @Purpose    based around grabbing quotes from t'internet (or via a local endpoint)
 */
@RequestMapping("/quote")
@RestController
public class QuoteController {

    // Allow injection of the service that does the work ...
    private QuoteService quoteService;
    public QuoteController(QuoteService quoteService){
        this.quoteService = quoteService;
    }

    // Pull in the port we're running on
    @Value("${server.port}")
    int tomcatPort;

    // Pass back raw quote.  Leave it to caller to worry about jSon
    @ApiOperation( value    = "get Random quote from t'internet and return as Quote object",
                   response = String.class)
    @RequestMapping(value="", method = RequestMethod.GET)
    public Quote getQuote(){
        return quoteService.getQuote();
    }

    // Take in a quote (as part of the post) and use it to "amend" something we then hand back (as JSONObject)
    @RequestMapping( value="", method = RequestMethod.POST)
    public Quote postQuote(@RequestBody @Valid final Quote quote) {

        // Will  want to determine/output whatever we got given at some point
        System.out.println("In postQuote = "+quote.toString());

        // Mimic the effect of the post
        return quoteService.postQuote( quote );
    }


    // Pass back quote as simple string (that might contain a complex payload)
    @ApiOperation( value    = "get Random quote from t'internet and return stringified (in a non-jSon way)",
            response = String.class)
    @RequestMapping(value="/asString", method = RequestMethod.GET)
    public String getQuoteAsString(){
        return quoteService.getQuoteAsString();
    }


    // Play with returning JSon object, directly. GET
    @RequestMapping(value="/asJson",method = RequestMethod.GET)
    @ApiOperation( value    = "get Random quote from t'internet and return as json object",
                   response = JSONObject.class)
    public JSONObject getQuoteAsJson() {
        return quoteService.getQuoteAsJson();
    }


    // Take in a quote (as part of the post) and use it to "amend" something we then hand back (as JSONObject)
    @RequestMapping( value="/asJson", method = RequestMethod.POST)
    public JSONObject postJson(@RequestBody @Valid final Quote quote) {
        // People expecting jSon
        return quoteService.postQuoteAsJson( quote );
    }


    // Exercise both get & post methods
    @RequestMapping(value="/jsonTest",method = RequestMethod.GET)
    public String testJson() {

        // Want to try and grab stuff from elsewhere.  If the call results in text then we're OK.  Anything beyond that ...
        String url="http://localhost:"+tomcatPort+"/quote/";
        RestTemplate restTemplate = new RestTemplate();


        // Get a quote and mark it (or do so in the Post)?
        Quote q =restTemplate.getForObject( url, Quote.class );


        // Pseudo Post to it, so we can see it got there & grab some response ...
        ResponseEntity<Quote> responsePostEntityQuote=restTemplate.postForEntity( url, q, Quote.class );
        Quote r = responsePostEntityQuote.getBody();


        // Build something vaguely elegant that might be displayed nicely (assumes they can do prettyPrint
        JSONObject json=new JSONObject();
        json.put("quote",q);
        json.put("post",r);

        JSONObject resp=new JSONObject();
        resp.put("/jsonTestOutput",json);
        return resp.toJSONString();

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
