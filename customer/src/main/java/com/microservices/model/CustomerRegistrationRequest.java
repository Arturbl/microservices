package com.microservices.model;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
