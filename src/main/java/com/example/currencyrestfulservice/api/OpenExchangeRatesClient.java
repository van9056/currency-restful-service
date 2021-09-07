package com.example.currencyrestfulservice.api;

import com.example.currencyrestfulservice.model.CurrencyInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OpenExchangeRates-service", url = "${api.openexchangerates.url}")
public interface OpenExchangeRatesClient {

    @GetMapping("/api/latest.json")
    CurrencyInfo getLatest(@RequestParam String app_id,
                           @RequestParam String base,
                           @RequestParam String symbols);

    @GetMapping("/api/historical/{date}.json")
    CurrencyInfo getHistorical(@PathVariable String date,
                               @RequestParam String app_id,
                               @RequestParam String base,
                               @RequestParam String symbols);
}
