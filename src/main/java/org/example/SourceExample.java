// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 */
@EnableBinding(Source.class)
@RestController
public class SourceExample {

    @Autowired
    private Source source;

    @PostMapping("/messages")
    public String postMessage(@RequestParam String message) {
        System.out.println("manually send message,message=" + message);
        this.source.output().send(new GenericMessage<>(message));
        return message;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
}
