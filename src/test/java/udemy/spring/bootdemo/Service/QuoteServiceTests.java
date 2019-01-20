package udemy.spring.bootdemo.Service;

import com.google.gson.internal.LinkedTreeMap;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.is;

import org.json.simple.JSONObject;
import org.junit.Assert;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import udemy.spring.bootdemo.Domain.Quote;

import java.util.LinkedHashMap;

/**
 * @Author  : me
 * @Purpose : test the new quoteService
 */
public class QuoteServiceTests {

    // How we build up a quote
    long ID      = 69;
    String QUOTE = "pithy words";
    String TYPE  = "qwerty";

    // Service
    QuoteService quoteService = new QuoteService();


    @Test
    public void NodAndSmile() {
    }

    // Is the quote to Json as expected
    @Test
    public void TestGetQuoteAsJsonObject(){

        Quote testQuote = new Quote( TYPE, ID, QUOTE );
        JSONObject json = quoteService.getQuoteAsJSONObject( testQuote );

        // Sure there's a better way to achieve this, but for now ... Revisit need to cast long too
        LinkedHashMap words = (LinkedHashMap) json.get("value");
        assertThat( words.get("id"), is( (int)ID ) );
        assertThat( words.get("quote"), is( QUOTE ) );

    }

    // Is the quote to Json as expected
    @Test
    public void TestGetQuoteAsJsonObjectChoice(){

        // The mapping ought to work the same way regardless
        Quote testQuote = new Quote( TYPE, ID, QUOTE );
        JSONObject json = quoteService.getQuoteAsJSONObject( testQuote, false );
        JSONObject ison = quoteService.getQuoteAsJSONObject( testQuote, true );

        // Sure there's a better way to achieve this, but for now ... Revisit need to cast long too
        LinkedHashMap jWords = (LinkedHashMap) json.get("value");
        assertThat( jWords.get("id"), is( (int)ID ) );
        assertThat( jWords.get("quote"), is( QUOTE ) );

        // This option is a function of gson.  Hugely contrived way to check the id is a match (gson records as double)
        LinkedTreeMap iWords = (LinkedTreeMap) ison.get("value");
        assertThat( (int)Double.parseDouble(iWords.get("id").toString()), is( (int)ID ) );
        assertThat( iWords.get("quote"), is( QUOTE ) );

    }


}
