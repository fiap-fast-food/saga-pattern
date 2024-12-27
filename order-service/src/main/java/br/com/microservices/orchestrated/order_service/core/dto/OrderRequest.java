package br.com.microservices.orchestrated.order_service.core.dto;

import br.com.microservices.orchestrated.order_service.core.document.OrderProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String cpfCustomer;
    private List<OrderProducts> products;
}
