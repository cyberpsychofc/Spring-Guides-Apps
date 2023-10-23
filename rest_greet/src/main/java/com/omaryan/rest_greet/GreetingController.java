package com.omaryan.rest_greet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static String template = "Hello, %s Your Visitor Number is %d";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting") //GET reqs. are mapped to below method
    //RequestParam binds value w/ parameter,
    public Greeting greeting(@RequestParam(value = "name",defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(),String.format(template,name,counter.get()));
    }
}
