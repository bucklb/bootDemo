package udemy.spring.bootdemo.Controller;

//import org.powermock.core.classloader.annotations.Mock;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import udemy.spring.bootdemo.Domain.Quote;
import udemy.spring.bootdemo.Service.QuoteService;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *  Can't test the controller with a real service, as the stuff it use would be outside our control
 */
@RunWith(SpringRunner.class)
@WebMvcTest(QuoteController.class)
public class QuoteControllerTest {

    String QUOTE_TYPE = "test";
    long   QUOTE_ID   = 69;
    String QUOTE_TEXT = "If you were my wife I'd drink it";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


//    private final TestConfig config = new TestConfig();

    // Don't want the overhead of full context.  Not sure this needs to be autowired if we set it up in "setup" anyway
//    @Autowired
    private MockMvc mockMvc;

    // Need a service we can "control" (i.e dictate what it does when it's hit)
    @Mock
    private QuoteService quoteService;

    // Make sure the controller gets our mock(s)
    @InjectMocks
    private QuoteController quoteControllerUnderTest;

    // quote in json (String)
    private String quoteAsJson(Quote quote ){
        Gson gson=new Gson();
        return gson.toJson(quote);
    }

    // quote as JSONObject
    private JSONObject quoteAsJsonObject(Quote quote ){
        Gson gson=new Gson();
        return gson.fromJson(quoteAsJson(quote), JSONObject.class);
    }

    /**
     *  Look for bare minimum to make progress with tests. Copied from cis-client
     */
    @Before
    public void setup() {
        initMocks(this);
//        HttpMessageConverter httpMessageConverter = config.mappingJackson2HttpMessageConverter();
        mockMvc = MockMvcBuilders
                .standaloneSetup(quoteControllerUnderTest)
//                .setControllerAdvice(new GlobalExceptionHandler(config.messages()))
//                .setMessageConverters(httpMessageConverter)
                .build();
    }



    // Basic command is just to get a Quote (i.e Quote object)
    @Test
    public void testGetQuote() throws Exception {

        // Set up a dummy quote
        Quote quote=new Quote( QUOTE_TYPE, QUOTE_ID, QUOTE_TEXT );
        when(quoteService.getQuote()).thenReturn(quote);

        // Might be better to look at just whether the text is as expected
        this.mockMvc
                .perform(get("/quote/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString( QUOTE_TYPE )))
                .andExpect(content().string(containsString( QUOTE_TEXT )))
                .andExpect(content().string(containsString( String.valueOf(QUOTE_ID) )))
        ;

    }

    // Post a quote (in text, effectively) then expect a quote object back
    @Test
    public void testPostQuote() throws Exception {

        // Set up dummy quotes. A trigger (empty) quote and a response quote
        Quote quote=new Quote( QUOTE_TYPE, QUOTE_ID, QUOTE_TEXT );
        Quote q=new Quote();

        // Any quote will trigger return of what we want, but possibly not NULL
        when(quoteService.postQuote( any( Quote.class ) )).thenReturn(quote);

        // Might be better to look at just whether the text is as expected
        this.mockMvc
                .perform(post("/quote/")
                        .content( quoteAsJson( q ) )    // http is rather stringy
                        .contentType(contentType)
                )
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString( QUOTE_TYPE )))
                .andExpect(content().string(containsString( QUOTE_TEXT )))
                .andExpect(content().string(containsString( String.valueOf(QUOTE_ID) )))
        ;
    }

    // Get quote in asString format (differs from jSon format)
    @Test
    public void testGetQuoteAsString() throws Exception {

        // Set up a dummy quote
        Quote quote=new Quote( QUOTE_TYPE, QUOTE_ID, QUOTE_TEXT );
        when(quoteService.getQuote()).thenReturn(quote);

        // Might be better to look at just whether the text is as expected
        this.mockMvc
                .perform(get("/quote/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString( QUOTE_TYPE )))
                .andExpect(content().string(containsString( QUOTE_TEXT )))
                .andExpect(content().string(containsString( String.valueOf(QUOTE_ID) )))
        ;

    }

    // Get quote in jSon format
    @Test
    public void testGetQuoteAsJson() throws Exception {

        JSONObject j;

        // Set up a dummy quote
        Quote quote=new Quote( QUOTE_TYPE, QUOTE_ID, QUOTE_TEXT );
        when(quoteService.getQuoteAsJson()).thenReturn( quoteAsJsonObject( quote ) );

        // Might be better to look at just whether the text is as expected
        this.mockMvc
                .perform(get("/quote/asJson/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString( QUOTE_TYPE )))
                .andExpect(content().string(containsString( QUOTE_TEXT )))
                .andExpect(content().string(containsString( String.valueOf(QUOTE_ID) )))
        ;

    }

    // Post a quote (in text, effectively) then expect a quote object back
    @Test
    public void testPostQuoteAsJson() throws Exception {

        // Set up dummy quotes. A trigger (empty) quote and a response quote
        Quote quote=new Quote( QUOTE_TYPE, QUOTE_ID, QUOTE_TEXT );
        Quote q=new Quote();

        // Any quote will trigger return of what we want, but possibly not NULL
        when(quoteService.postQuoteAsJson( any( Quote.class ) )).thenReturn( quoteAsJsonObject(quote) );

        // Might be better to look at just whether the text is as expected
        this.mockMvc
                .perform(post("/quote/asJson")
                        .content( quoteAsJson( q ) )    // http is rather stringy
                        .contentType(contentType)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString( QUOTE_TYPE )))
                .andExpect(content().string(containsString( QUOTE_TEXT )))
                .andExpect(content().string(containsString( String.valueOf(QUOTE_ID) )))
        ;
    }





}
