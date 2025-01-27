package com.omar.hotel_reservation.filter;

import com.omar.hotel_reservation.token.TokenService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (exchange.getRequest().getPath().toString().startsWith("/api/v1/auth") || exchange.getRequest().getPath().toString().startsWith("/api/v1/user/exists")
                || exchange.getRequest().getPath().toString().startsWith("/api/v1/user/validate")
        || exchange.getRequest().getPath().toString().startsWith("/v3/api-docs")
        || exchange.getRequest().getPath().toString().startsWith("/swagger-ui")){
            return chain.filter(exchange);
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.replace("Bearer ", "");
        try{
            String role = tokenService.validateAndExtractRole(token);
            if (!isAuthorized(role, exchange.getRequest().getPath().toString(), exchange.getRequest().getMethod().toString())){
                return exchange.getResponse().setComplete();
            }
        } catch (Exception e){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    private boolean isAuthorized(String role, String path, String httpMethod) {
        if (path.startsWith("/api/v1/user") && !role.equals("ADMIN")) { //authentication-service
            return false;
        }
        if (path.startsWith("/api/v1/hotel") && !role.equals("ADMIN") && (httpMethod.equals("POST") || httpMethod.equals("PUT"))){ //hotel-service
            return false;
        }
        if (path.startsWith("/api/v1/room") && !role.equals("ADMIN") && (httpMethod.equals("POST") || httpMethod.equals("PUT"))){ //room-service
            return false;
        }
        return true;
    }
}
