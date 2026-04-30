package com.example.back.dto;

public record AssignRequestDto(
    Integer consultationId,
    Integer employeeId,
    String managerName
) {}