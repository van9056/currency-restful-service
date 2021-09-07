package com.example.currencyrestfulservice.service;

import com.example.currencyrestfulservice.api.GiphyClient;
import com.example.currencyrestfulservice.api.OpenExchangeRatesClient;
import com.example.currencyrestfulservice.model.CurrencyInfo;
import com.example.currencyrestfulservice.model.Gif;
import com.example.currencyrestfulservice.service.GifCurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GifCurrencyServiceTest {

    @Autowired private GifCurrencyService gifCurrencyService;

    @MockBean private OpenExchangeRatesClient openExchangeRatesClient;
    @MockBean private GiphyClient giphyClient;

    private final String testSymbol = "RUB";

    public void prepareReturnsForOpenExchangeRatesClient(float latestRate, float yesterdayRate) {
        var latestInfo = new CurrencyInfo(new HashMap<>() {{ put(testSymbol, latestRate); }}, 1609459200);
        var yesterdayInfo = new CurrencyInfo(new HashMap<>() {{ put(testSymbol, yesterdayRate); }}, 1609372800);

        when(openExchangeRatesClient.getLatest(any(), any(), eq(testSymbol))).thenReturn(latestInfo);
        when(openExchangeRatesClient.getHistorical(any(), any(), any(), eq(testSymbol))).thenReturn(yesterdayInfo);
    }

    @Test
    public void whenSymbolIsNotValid_returnNull() {
        String invalidSymbol = "Aa1";
        assertThat(gifCurrencyService.getRelevantGif(invalidSymbol)).isNull();
        verifyNoInteractions(openExchangeRatesClient, giphyClient);
    }

    @Test
    public void whenSymbolIsValid_returnCorrectResult() {
        prepareReturnsForOpenExchangeRatesClient(70, 70);
        when(giphyClient.getRandomGif(any(), any())).thenReturn(new Gif(null));
        assertThat(gifCurrencyService.getRelevantGif(testSymbol)).isNotNull();
    }

    @Test
    public void whenYesterdayRateIsHigherThanLatestRate_returnBrokeGif() {
        prepareReturnsForOpenExchangeRatesClient(70, 75);
        gifCurrencyService.getRelevantGif(testSymbol);
        verify(giphyClient).getRandomGif(any(), eq("broke"));
    }

    @Test
    public void whenYesterdayRateIsLessThanLatestRate_returnBrokeGif() {
        prepareReturnsForOpenExchangeRatesClient(75, 70);
        gifCurrencyService.getRelevantGif(testSymbol);
        verify(giphyClient).getRandomGif(any(), eq("rich"));
    }
}
