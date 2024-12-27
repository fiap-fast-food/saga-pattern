package br.com.microservices.orchestrated.payment_service.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCustomer {

    private String street;
    private String city;
    private String state;
}
