package ru.alfabank.testtask.exchangerateandgifs.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateDto {
    private String disclaimer;
    private String license;
    private long timestamp;
    private String base;
    private Map<String , Double> rates;
}
