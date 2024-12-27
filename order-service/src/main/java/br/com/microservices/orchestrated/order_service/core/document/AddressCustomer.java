package br.com.microservices.orchestrated.order_service.core.document;

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
