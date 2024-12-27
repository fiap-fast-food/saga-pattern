package br.com.microservices.orchestrated.product_validation_service.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class WebConfig implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        }
        chain.doFilter(request, response);
    }
}
