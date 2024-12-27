package br.com.microservices.orchestrated.payment_service.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private String id;
    private String name;
    private String cpf;
    private AddressCustomer address;
    private String phone;
    @Builder.Default
    private Boolean isActive = true;

}