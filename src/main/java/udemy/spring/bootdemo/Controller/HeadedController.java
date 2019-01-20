package udemy.spring.bootdemo.Controller;


//import com.sun.org.apache.xpath.internal.operations.Quo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

// stuff to allow access to other URLs (as long as they return straight text)
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;
import udemy.spring.bootdemo.Domain.Quote;

import java.util.ArrayList;
import java.util.List;

//import static com.sun.deploy.net.HttpRequest.CONTENT_TYPE;


//
// Do playing with Entity/header stuff to one side
//
@RequestMapping("/headed")
@RestController
public class HeadedController {

    String BR="<br>";

    @Value("${server.port}")
    int tomcatPort;

    // Quick summary of what's here
    @RequestMapping(value="", method = RequestMethod.GET)
    public String getHeaded() {
        String s = "looking at entity & header\n\n"+BR+BR+
                "quoteString: get      : returns simple string representation of a quote\n"+BR+
                "quoteEntity: post/get : returns a quote as an entity\n";

        return s;
    }

    @RequestMapping(value="/quoteString", method = RequestMethod.GET)
    public String getQuote(){

        System.out.println(tomcatPort);


        // Vaguely OK with picking through the RESPONSE http structure.  Still need to look at REQUEST though

        RestTemplate restTemplate = new RestTemplate();

        // Get it as String
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://gturnquist-quoters.cfapps.io/api/random",
                String.class);

        System.out.println("Full response : " + response.toString());
        System.out.println("Body response : " + response.getBody().toString());

        // Can I cast it and get something
        ResponseEntity<Quote> responseEntityQuote = restTemplate.getForEntity(
                "http://gturnquist-quoters.cfapps.io/api/random",
                Quote.class);
        System.out.println("QuoteEntity : " + responseEntityQuote.toString());

        // The bits - so I can get some idea of what's going on in the response entity
        System.out.println("QuoteStatus : " + responseEntityQuote.getStatusCode().toString());
        System.out.println("QuoteHeaders: " + responseEntityQuote.getHeaders().toString());
        System.out.println("QuoteBody   : " + responseEntityQuote.getBody().toString());
        System.out.println("QuoteClass  : " + responseEntityQuote.getClass() );

        // ?? Is the body (convertible to) a Quote object
        Quote q =  responseEntityQuote.getBody();
        System.out.println("Quote's val : " + q.getValue());

        return q.toString();
    }

    //
    // Time to move away from String & JSon ??  In this case we expect to respond with a quote
    //
    @RequestMapping(value="/quoteEntity", method = RequestMethod.GET)
    public ResponseEntity<Quote> getQuoteAsEntity() {

        if(1==1) {
            // Use getForEntity to get a quote from afar
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Quote> responseEntityQuote = restTemplate.getForEntity(
                    "http://gturnquist-quoters.cfapps.io/api/random",
                    Quote.class);

            // Pull the quote from the response.  Would allow us to bugger around with it ??
            Quote quote = responseEntityQuote.getBody();

            // Pass back the quote & status in an Entity ...
            return new ResponseEntity<Quote>(quote, HttpStatus.OK);
        } else {
            // was useful to have a fixed response.  Hopefully less so now
            return new ResponseEntity<Quote>(new Quote(), HttpStatus.OK);
        }
    }

    @RequestMapping(value="/quoteEntity", method = RequestMethod.POST)
    public ResponseEntity<Quote> postQuoteAsEntity(@RequestBody Quote quote) {

        // Basically, screw around with the quote.  Perhaps just set type = "Plagiarism"
        if( quote != null) {
            quote.setType("Thoroughly plagiarised");
        } else {
            quote=new Quote();
            quote.setType("Spontaneously appeared!!!");
        }

        return new ResponseEntity<Quote>(quote,HttpStatus.OK);
    }

    // Pass back many quotes (glean them one by one).  Could allow caller to specify number to return ...
    @RequestMapping(value = "/quoteEntities", method=RequestMethod.GET)
    public ResponseEntity<List<Quote>> getQuoteList(){
        RestTemplate restTemplate = new RestTemplate();
        // Create a list to add quotes to
        List<Quote> quoteList=new ArrayList<>();

        for(int i=0; i<5; i++ ) {
            quoteList.add(restTemplate.getForEntity(
                    "http://localhost:" + tomcatPort + "/headed/quoteEntity/", Quote.class).getBody());
        }
        return new ResponseEntity<List<Quote>>(quoteList,HttpStatus.OK);
    }
    
    @RequestMapping(value="/quoteTest", method = RequestMethod.GET)
    public String testQuoteAsEntity(){
        RestTemplate restTemplate = new RestTemplate();
        Quote quote=new Quote();
        Quote getQuote;
        Quote postQuote;

        // Grab a quote - one way or another
        if(0==1) {
            // Use getForObject
            getQuote = restTemplate.getForObject(
                    "http://localhost:"+tomcatPort+"/headed/quoteEntity/", Quote.class);
        } else {
            // Use getForEntity (gives us scope to bugger around with headers??)
            System.out.println("Using getForEntity");
            ResponseEntity<Quote> responseGetEntityQuote = restTemplate.getForEntity(
                    "http://localhost:"+tomcatPort+"/headed/quoteEntity/", Quote.class);
            getQuote= responseGetEntityQuote.getBody();
            System.out.println(responseGetEntityQuote.toString());
        }
       System.out.println(getQuote.toString());

        // Post a quote and expect "Plagiarised" in response
        if(0==1){
            postQuote=restTemplate.postForObject(
                    "http://localhost:"+tomcatPort+"/headed/quoteEntity/",getQuote,Quote.class);
        } else {
            System.out.println("Using postForEntity");
            ResponseEntity<Quote> responsePostEntityQuote=restTemplate.postForEntity(
                    "http://localhost:"+tomcatPort+"/headed/quoteEntity/",getQuote,Quote.class);
            postQuote=responsePostEntityQuote.getBody();
            System.out.println(responsePostEntityQuote.toString());
        }

        System.out.println(postQuote.toString());

        return "Was " + getQuote.toString() + "\nNow " + postQuote.toString();
    }

    //
    // Look at what a post object could/should look like ...
    //
    @RequestMapping(value="/quoteViaMapAndObjectPost", method = RequestMethod.GET)
    public String postIt() {

        Quote quote=new Quote();
        quote.setType("EMPTY");
        HttpEntity<Quote> request= new HttpEntity<>(quote);

        RestTemplate restTemplate = new RestTemplate();

        System.out.println("request toString : " + request.toString());
        System.out.println("request headers  : " + request.getHeaders().toString());

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("quote", quote);

        Object response = restTemplate.postForObject(
////        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:"+tomcatPort+"/asJson/", parts, String.class);
//        ResponseEntity<String> response=restTemplate.postForEntity("http::/localhost:"+tomcatPort+"/json/",parts,String.class);

        System.out.println("=====================================================================\n POST with MultiValueMap-Object");
        System.out.println("request   : " + request.toString());
        System.out.println("response  : " + response.toString());

        return response.toString();
    }

    // Might finally have something that vaguely looks like a meaningful POST approach
    @RequestMapping(value="/quoteViaMapAndEntityPost", method = RequestMethod.GET)
    public String x(){

        RestTemplate restTemplate = new RestTemplate();

        // Pull together some header stuff
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", "first.last@example.com");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:"+tomcatPort+"/asJson/", request , String.class );

        System.out.println("=====================================================================\n POST with MultiValueMap-Entity");
        System.out.println("request   : " + request.toString());
        System.out.println("response  : " + response.toString());

//        return response.toString();
        return response.getBody().toString();
    }

    // Mostly a test for the SMI/verification guff
    // Want to be able to pass in some parameters, but not ALL of them, all of the time
    @RequestMapping(value="/testParams", method = RequestMethod.GET)
    public ResponseEntity<String> y(@RequestParam(value="first", required = false) String first){

        System.out.println("==================================================");
        System.out.println("first : " + first);



        //        Bare minimum
        return new ResponseEntity<String>("a response, after a fashion",HttpStatus.OK);
    }


}
