package com.pm.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                             @Value("${auth.service.url}") String authServiceUrl) {
        // http://auth-service:4005
        // http://ecs.aws.sdsfgfdg:5000
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    // Filter class in Springboot is Custom class
    // Allow us to intercept http request and apply custom logic and decide whether to continue or not, processing request
    // use the filter to call service validation endpoint to check token is valid or not
    @Override
    public GatewayFilter apply(Object config) {
        // variables are passed by AbstractGatewayFilterFactory<Object>
        // exchange variable is an object that gets passed to us by Spring Gateway that holds properties for current request
        // chain variable manages the chain of filters, that exists in filter chain
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // webClient takes baseUrl and add uri to create complete uri
            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));
            // (then) method called when everything goes good
            // chain.filter(exchange) basically tells to continue with request, could be another filter
        };
    }

}
