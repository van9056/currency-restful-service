package com.example.currencyrestfulservice.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = Gif.GifDeserializer.class)
public class Gif {
    private URL url;

    static class GifDeserializer extends JsonDeserializer<Gif> {

        @Override
        public Gif deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(p);
            URL url = new URL("https://i.giphy.com/media/" + node.get("data").get("id").textValue() + "/giphy.gif");
            return new Gif(url);
        }
    }
}