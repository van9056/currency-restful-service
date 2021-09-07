package com.example.currencyrestfulservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class CurrencyInfo {
    private Map<String, Float> rates;
    private long timestamp;
}
