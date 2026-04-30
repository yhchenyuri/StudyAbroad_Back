package com.example.back.dto;

/**
 * 負責裝載「業務顧問」與「當前負載案件數」的 DTO
 */
public record ConsultantWorkloadDto(
    Integer id,
    String name,
    Long currentCaseCount // JPQL 的 COUNT() 回傳值必須用 Long 接
) {}