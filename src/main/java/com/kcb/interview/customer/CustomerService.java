package com.kcb.interview.customer;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerRegistrationRequest request){
        var customer = Customer.builder()
                .email(request.getEmailAddress())
                .name(request.getName())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .gender(request.getGender())
                .residentialAddress(request.getResidentialAddress())
                .phoneNumber(request.getPhoneNumber())
                .identificationNumber(generateActivationCode(8))
                .build();
        customerRepository.save(customer);

        sendWelcomeSms(customer.getPhoneNumber(), "Dear " + customer.getName() + " Thank for registering to Presto Technologies");
    }


    public Customer findCustomerById(UUID id){
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Customer> findAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer updateCustomer(UUID customer_id, Customer customerData){
        Customer customer = this.findCustomerById(customer_id);
        customer.setName(customerData.getName());
        customer.setEmail(customerData.getEmail());
        customer.setResidentialAddress(customerData.getResidentialAddress());
        customer.setPhoneNumber(customerData.getPhoneNumber());
        customer.setGender(customerData.getGender());
        customer.setIdentificationNumber(customerData.getIdentificationNumber());

        return customerRepository.save(customerData);
    }

    public void deleteCustomer(UUID customer_id){
        Customer customer = findCustomerById(customer_id);
        customer.setIsDeleted(true);
        customerRepository.save(customer);
    }

    public void sendWelcomeSms(String phoneNumber, String message) {
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

}
