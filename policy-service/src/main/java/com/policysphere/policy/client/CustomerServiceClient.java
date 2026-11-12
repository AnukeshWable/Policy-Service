package com.policysphere.policy.client;

import com.policysphere.policy.exception.CustomerNotFoundException;
import com.policysphere.policy.exception.CustomerServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class CustomerServiceClient {

    /** Only the fields Policy Service needs from Customer Service's response. */
    public record CustomerResponse(Long id, String status) {
        public boolean isActive() {
            return "ACTIVE".equals(status);
        }
    }

    private final RestClient restClient;

    public CustomerServiceClient(RestClient customerRestClient) {
        this.restClient = customerRestClient;
    }

    public CustomerResponse getCustomer(Long customerId) {
        try {
            CustomerResponse customer = restClient.get()
                    .uri("/api/v1/customers/{id}", customerId)
                    .retrieve()
                    .onStatus(status -> status.value() == 404, (request, response) -> {
                        throw new CustomerNotFoundException(customerId);
                    })
                    .body(CustomerResponse.class);

            if (customer == null) {
                throw new CustomerServiceException("Customer Service returned an empty response", null);
            }
            return customer;

        } catch (ResourceAccessException ex) {
            // connection refused, timeout, unknown host
            throw new CustomerServiceException("Customer Service is unavailable", ex);
        } catch (RestClientException ex) {
            // 5xx, other 4xx, unreadable body
            throw new CustomerServiceException("Customer Service call failed", ex);
        }
    }
}
