package udemy.spring.bootdemo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// stuff to allow access to other URLs (as long as they return straight text)
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;
import udemy.spring.bootdemo.Domain.Quote;


//
// Do playing with Entity/header stuff to one side
//
@RequestMapping("/headed")
@RestController
public class AnotherController {

    @RequestMapping(value="", method = RequestMethod.GET)
    public String getQuote(){

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
        Quote q=  responseEntityQuote.getBody();
        System.out.println("Quote's val : " + q.getValue());

        return response.toString();
    }


}
