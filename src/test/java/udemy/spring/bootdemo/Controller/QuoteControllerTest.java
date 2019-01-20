package udemy.spring.bootdemo.Controller;

//import org.powermock.core.classloader.annotations.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import udemy.spring.bootdemo.TestConfig;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class QuoteControllerTest {

    private final TestConfig config = new TestConfig();

    @Autowired
    private MockMvc mockMvc;
//    @Mock



}
