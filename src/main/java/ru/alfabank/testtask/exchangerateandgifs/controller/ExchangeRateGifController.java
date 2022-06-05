package ru.alfabank.testtask.exchangerateandgifs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alfabank.testtask.exchangerateandgifs.service.fasade.ExchangeRateGifService;

@Slf4j
@RestController()
@RequiredArgsConstructor
public class ExchangeRateGifController {

    /*
    TODO
     1 Написать обработчик исключений - done
     2 Написать тесты - done - //можно доделать тесты на контроллер//
     3 Переменные среды Docker / настройка докер / профили spring -
     4 Выложить на github
     */

    private final ExchangeRateGifService exchangeRateGifService;

    @GetMapping
    public ResponseEntity<byte[]> getGifByCurrency(@RequestParam(value = "currency",defaultValue = "RUB") String currency) {
        log.info("Попытка получить gif");

        ResponseEntity<byte[]> result = exchangeRateGifService.getGifByCurrency(currency);

        log.info("Попытка получить gif завершилась успешно");
        return result;
    }
}
