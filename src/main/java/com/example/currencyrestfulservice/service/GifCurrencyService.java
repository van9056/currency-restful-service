package com.example.currencyrestfulservice.service;

import com.example.currencyrestfulservice.model.Gif;

public interface GifCurrencyService {
    Gif getRelevantGif(String symbol);
}
