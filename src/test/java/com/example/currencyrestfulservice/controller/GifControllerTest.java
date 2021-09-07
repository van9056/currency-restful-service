package com.example.currencyrestfulservice.controller;

import com.example.currencyrestfulservice.controller.GifController;
import com.example.currencyrestfulservice.model.Gif;
import com.example.currencyrestfulservice.service.GifCurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GifController.class)
public class GifControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private GifCurrencyService gifCurrencyService;

    @Test
    public void whenGetGifRequestParamIsValid_returnOk() throws Exception {
        String validParam = "RUB";
        Gif testGif = new Gif(new URL("http://example.com"));
        when(gifCurrencyService.getRelevantGif(eq(validParam))).thenReturn(testGif);

        mockMvc.perform(get("/gif")
                .queryParam("symbol", validParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_GIF));
    }

    @Test
    public void whenGetGifRequestParamIsNotValid_returnBadRequest() throws Exception {
        String invalidParam = "Aa1";
        when(gifCurrencyService.getRelevantGif(eq(invalidParam))).thenReturn(null);

        mockMvc.perform(get("/gif")
                .queryParam("symbol", invalidParam))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetGifRequestTriesToOpenTheWrongURL_returnInternalServerError() throws Exception {
        when(gifCurrencyService.getRelevantGif(any())).thenReturn(new Gif(new URL("http://")));

        mockMvc.perform(get("/gif")
                .queryParam("symbol", "RUB"))
                .andExpect(status().isInternalServerError());
    }
}
