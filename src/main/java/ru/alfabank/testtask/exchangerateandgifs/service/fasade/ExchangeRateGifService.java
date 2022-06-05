package ru.alfabank.testtask.exchangerateandgifs.service.fasade;

import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface ExchangeRateGifService {
    ResponseEntity<byte[]> getGifByCurrency(String currency);
}
