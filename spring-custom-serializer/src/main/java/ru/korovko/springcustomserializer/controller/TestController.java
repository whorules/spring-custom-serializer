package ru.korovko.springcustomserializer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.korovko.springcustomserializer.model.Subscription;

@RestController
public class TestController {

    @PostMapping
    public void process(@RequestBody Subscription subscription) {
        // by this moment we already have dates with server time

        // calling service methods...
    }
}
