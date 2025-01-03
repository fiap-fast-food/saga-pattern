package br.com.microservices.orchestrated.payment_service.config.mercadopago;

import com.mercadopago.client.preference.PreferenceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfig {
    @Bean
    public PreferenceClient preferenceClient() {
        return new PreferenceClient();
    }
}
