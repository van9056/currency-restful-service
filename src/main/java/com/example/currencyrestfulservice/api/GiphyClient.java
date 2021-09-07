package com.example.currencyrestfulservice.api;

import com.example.currencyrestfulservice.model.Gif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Giphy-service", url = "${api.giphy.url}")
public interface GiphyClient {

    @GetMapping("/v1/gifs/random")
    Gif getRandomGif(@RequestParam String api_key, @RequestParam String tag);
}
