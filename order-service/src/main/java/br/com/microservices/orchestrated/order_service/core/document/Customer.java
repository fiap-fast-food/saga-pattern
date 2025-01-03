package br.com.microservices.orchestrated.order_service.core.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
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
