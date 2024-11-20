package com.shatakshee.yummyrest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
public record ProductRequest(
        @NotBlank(message = "Name is required and cannot be blank.")
        @Size(max = 100, message = "Name cannot exceed 100 characters.")
        String name,

        @NotNull(message = "Price is required.")
        @Positive(message = "Price must be a positive number.")
        Double price) {


}
