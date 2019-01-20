package udemy.spring.bootdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class TestConfig {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();

        // TODO : needed ??
//        Jackson2ObjectMapperBuilder objectMapperBuilder = WebConfig.createJackson2ObjectMapperBuilder();
//        objectMapperBuilder.configure(objectMapper);

        MappingJackson2HttpMessageConverter mappingConverter = new MappingJackson2HttpMessageConverter();
        mappingConverter.setObjectMapper(objectMapper);

        return mappingConverter;
    }

}
