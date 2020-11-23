package com.demo.api.controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiControllerTest {
    private final ApiController apiController = new ApiController();

    @Test
    public void helloUsesGivenName() {
        Assertions.assertThat(apiController.hello("Elias")).isEqualTo("Hello Elias!");
        Assertions.assertThat(apiController.hello("Sandra")).isEqualTo("Hello Sandra!");
        Assertions.assertThat(apiController.hello("Andreas")).isEqualTo("Hello Andreas!");
    }

    @Test
    public void pingReturnPong() {
        Assertions.assertThat(apiController.ping()).isEqualTo("pong");
    }
}