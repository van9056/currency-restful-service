package com.example.currencyrestfulservice.controller;

import com.example.currencyrestfulservice.model.Gif;
import com.example.currencyrestfulservice.service.GifCurrencyService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GifController {

    @Autowired private GifCurrencyService gifCurrencyService;

    @GetMapping(value = "/gif", produces = MediaType.IMAGE_GIF_VALUE)
    public ResponseEntity<?> getGif(@RequestParam String symbol) {
        try {
            Gif gif = gifCurrencyService.getRelevantGif(symbol);
            if (gif == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            byte[] gifByteArray = IOUtils.toByteArray(gif.getUrl().openStream());
            return new ResponseEntity<>(gifByteArray, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
