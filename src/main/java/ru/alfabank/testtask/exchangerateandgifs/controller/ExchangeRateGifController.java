package ru.alfabank.testtask.exchangerateandgifs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alfabank.testtask.exchangerateandgifs.service.fasade.ExchangeRateGifService;

@Slf4j
@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ExchangeRateGifController {

    private final ExchangeRateGifService exchangeRateGifService;

    @GetMapping("/gif")
    public ResponseEntity<byte[]> getGifByCurrency(@RequestParam String currency) {
        log.info("Попытка получить gif");

        ResponseEntity<byte[]> result = exchangeRateGifService.getGifByCurrency(currency);

        log.info("Попытка получить gif завершилась успешно");
        return result;
    }
}
