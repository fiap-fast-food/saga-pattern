package br.com.microservices.orchestrated.order_service.core.dto;

import br.com.microservices.orchestrated.order_service.core.document.AddressCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private String name;
    private String cpf;
    private AddressCustomer address;
    private String phone;
}
