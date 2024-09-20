package com.kcb.interview.customer;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class CustomerController  {

    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("register")
    public ResponseEntity<?> register( @RequestBody @Valid CustomerRegistrationRequest request) {
        customerService.registerCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        Optional<Customer> customer = Optional.ofNullable(customerService.findCustomerById(id));
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomer();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
