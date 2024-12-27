package br.com.microservices.orchestrated.order_service.core.repository;

import br.com.microservices.orchestrated.order_service.core.document.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findByCpfAndIsActiveTrue(String cpf);
    Boolean existsByCpf(String cpf);

}
