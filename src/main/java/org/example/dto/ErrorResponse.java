package org.example.dto;

public record ErrorResponse(
    int status,
    String message,
    String details
){

}