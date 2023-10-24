package com.omaryan.restcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestConApplication {
    //To send output to the console
    private static final Logger log = LoggerFactory.getLogger(RestConApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RestConApplication.class, args);
    }
    //To process DATA in JSON
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
    //Runs RestTemplate
    @Bean
    @Profile("!test")
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception{
        return args -> {
            //Sends a GET request to Quoters Application running at Port:8080
            Quote quote = restTemplate.getForObject("http://localhost:8080/api/random",Quote.class);
            log.info(quote.toString());
        };
    }
}