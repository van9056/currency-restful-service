package com.example.currencyrestfulservice.service.impl;

import com.example.currencyrestfulservice.api.GiphyClient;
import com.example.currencyrestfulservice.api.OpenExchangeRatesClient;
import com.example.currencyrestfulservice.model.Gif;
import com.example.currencyrestfulservice.service.GifCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class GifCurrencyServiceImpl implements GifCurrencyService {

    @Value("${api.openexchangerates.key}") private String openExchangeRatesKey;
    @Value("${api.giphy.key}") private String giphyKey;
    @Value("${base}") private String base;

    @Autowired private OpenExchangeRatesClient openExchangeRatesClient;
    @Autowired private GiphyClient giphyClient;

    public Gif getRelevantGif(String symbol) {
        if (symbol == null || symbol.isEmpty() || !symbol.matches("^[A-Z]{3}$"))
            return null;

        var latestInfo = openExchangeRatesClient.getLatest(openExchangeRatesKey, base, symbol);
        var yesterdayInfo = openExchangeRatesClient.getHistorical(
                new SimpleDateFormat("yyyy-MM-dd").format(yesterday(latestInfo.getTimestamp())),
                openExchangeRatesKey,
                base,
                symbol
        );

        float latestRate = latestInfo.getRates().get(symbol);
        float yesterdayRate = yesterdayInfo.getRates().get(symbol);
        String searchQuery = "broke";
        if (yesterdayRate < latestRate) {
            searchQuery = "rich";
        }
        return giphyClient.getRandomGif(giphyKey, searchQuery);
    }

    private Date yesterday(long timestamp) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(timestamp * 1000));
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
