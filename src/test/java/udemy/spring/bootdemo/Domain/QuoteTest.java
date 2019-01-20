package udemy.spring.bootdemo.Domain;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import udemy.spring.bootdemo.Domain.Value;

public class QuoteTest {

    long ID      = 69l;
    String QUOTE = "pithy words";
    String TYPE  = "qwerty";

    @Test
    public void testConstructor() {

        Quote quote = new Quote( TYPE, ID, QUOTE);

        Value value = quote.getValue();
        assertThat( value.getId(),    is( ID ) );
        assertThat( value.getQuote(), is( QUOTE ) );

        assertThat( quote.getType(),  is( TYPE ) );

    }


}
