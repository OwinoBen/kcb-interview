package com.kcb.interview.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setIdentificationNumber("12345678");
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));
        customer.setPhoneNumber("+123456789");
        customer.setEmail("john@example.com");


        // Act & Assert
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.identificationNumber").value("12345678"));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // Arrange
        String stringUUID = "123e4567-e89b-12d3-a456-426614174000"; // Example string
        UUID id = UUID.fromString(stringUUID);
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Jane Doe");

        when(customerService.findCustomerById(id)).thenReturn(customer);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        String stringUUID = "123e4567-e89b-12d3-a456-426614174000"; // Example string
        UUID id = UUID.fromString(stringUUID);
        // Arrange
        Customer customer = new Customer();
        customer.setName("John Smith");

        when(customerService.updateCustomer(eq(id), any(Customer.class))).thenReturn(customer);

        // Act & Assert
        mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Smith"));
    }

    @Test
    public void testSoftDeleteCustomer() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isOk());
    }

    // Helper method to convert objects to JSON strings
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

