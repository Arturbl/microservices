package com.microservices;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
