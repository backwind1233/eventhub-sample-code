package org.example;



import com.microsoft.azure.spring.integration.core.AzureHeaders;
import com.microsoft.azure.spring.integration.core.api.reactor.AzureCheckpointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;





@SpringBootApplication
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

    }

    @Bean
    public Consumer<Message<String>> consume() {
        return message -> {
            AzureCheckpointer checkpointer = (AzureCheckpointer) message.getHeaders().get(AzureHeaders.CHECKPOINTER);

            LOGGER.info("Message is consuming, message.getHeaders()=" + message.getHeaders() + "message.getPayload()" + message.getPayload());
            checkpointer.success()
                        .doOnSuccess(success -> LOGGER.info("Message '{}' successfully checkpointed", message.getPayload()))
                        .doOnError(error -> LOGGER.error("Exception: {}", error.getMessage()))
                        .subscribe();
        };
    }
}
