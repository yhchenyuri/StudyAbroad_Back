package com.example.back.dto;

public record RecordRequestDto(
    Integer consultationId,
    String contactMethod,
    String content,
    String result,
    String nextStep
) {}