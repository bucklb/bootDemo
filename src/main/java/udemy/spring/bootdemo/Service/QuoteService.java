package udemy.spring.bootdemo.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import udemy.spring.bootdemo.Domain.Quote;

import javax.validation.Valid;

/**
 *  @Author     me
 *  @Purpose    handle the stuff the controller needs
 */
@Service
public class QuoteService {

    String QUOTE_URL = "http://gturnquist-quoters.cfapps.io/api/random";


    // Default becomes to NOT use gSon.  Allows backward compatibility (after a fashion)
    public JSONObject getQuoteAsJSONObject(Quote quote) {
        return getQuoteAsJSONObject(quote,false);
    }

    // Extract the code options for creating a JSONObject from a Quote object. Can specify mechanism ...
    public JSONObject getQuoteAsJSONObject( Quote quote, boolean useGson) {

        // Get it as JSONObject.  At least a couple of approaches ...
        // Both seem to rely on conversion to string ... Use the type to pass back which we used ...
        JSONObject json = null;

        if( useGson ){
            // Do it wholly with gson, via a string.  No exception to handle

            quote.setType("using gson");

            Gson gson=new Gson();
            String jsonString =gson.toJson(quote);
            json=gson.fromJson(jsonString, JSONObject.class);

        } else {
            // Do it wholly with jax/objectMapper

            try {
                quote.setType("using mapper");

                ObjectMapper OM=new ObjectMapper();
                String jsonString = OM.writeValueAsString(quote);
                json = OM.readValue(jsonString, JSONObject.class);
            } catch (Exception e) {
                // ought to do something, even if it's just recording a stack trace
                e.printStackTrace();
            }
        }

        // Pass it back
        return json;
    }


    // get a quote from t'internet
    public Quote getQuote(){
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject( QUOTE_URL, Quote.class );
        return quote;
    }

    // get a quote, so allow POST too
    public Quote postQuote( Quote quote ) {

        // Dummy up a quote to return
        Quote q = new Quote("Groucho", quote.getValue().getId(), "Go & never darken my towels again!" );
        return q;

    }

    // At string (NOTE: not the same as json - rather less sophisticated
    public String getQuoteAsString(){
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject( QUOTE_URL, Quote.class );
        return quote.toString();
    }


    public JSONObject getQuoteAsJson() {

        // Use getForEntity to get a quote from afar
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Quote> responseEntityQuote = restTemplate.getForEntity( QUOTE_URL, Quote.class );

        // Extract the quote proper from the response and pass back as Json
        Quote quote = responseEntityQuote.getBody();
        return getQuoteAsJSONObject(quote);
    }

    // get a quote, so allow POST too
    public JSONObject postQuoteAsJson( Quote quote ) {

        // Dummy up a quote to return
        Quote q = new Quote("Groucho", quote.getValue().getId(), "Go & never darken my towels again!" );
        return getQuoteAsJSONObject( q );
    }










}
