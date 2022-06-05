package ru.alfabank.testtask.exchangerateandgifs.service.fasade.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alfabank.testtask.exchangerateandgifs.dto.ExchangeRateDto;
import ru.alfabank.testtask.exchangerateandgifs.dto.GifDto;
import ru.alfabank.testtask.exchangerateandgifs.exception.CurrencyNotFoundException;
import ru.alfabank.testtask.exchangerateandgifs.service.fasade.ExchangeRateGifService;
import ru.alfabank.testtask.exchangerateandgifs.service.single.DownloadService;
import ru.alfabank.testtask.exchangerateandgifs.service.single.ExchangeRateService;
import ru.alfabank.testtask.exchangerateandgifs.service.single.GifService;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateGifServiceImpl implements ExchangeRateGifService {

    private final ExchangeRateService exchangeRateService;
    private final GifService gifService;
    private final DownloadService downloadService;

    @Value("${gif_api_key}")
    private String gifApiKey;
    @Value("${exchange_rate_app_id}")
    private String exchangeRateAppId;
    @Value("${base_currency}")
    private String baseCurrency;

    @Override
    public ResponseEntity<byte[]> getGifByCurrency(String currency) {
        String todayDate = formatDateFromNow(0);
        String yesterdayDate = formatDateFromNow(1);
        Double todayRate = getRateByDateAndCurrency(todayDate, currency);
        Double yesterdayRate = getRateByDateAndCurrency(yesterdayDate, currency);
        String tag = (todayRate < yesterdayRate) ? "rich" : "broke";
        URI gifUri = URI.create(getGifUrlByTag(tag));
        return downloadService.downloadResourceByUrl(gifUri);
    }


    private String formatDateFromNow(int days) {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(days);
        String dateFromNow = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(dateTime);
        return dateFromNow;
    }

    private Double getRateByDateAndCurrency(String date, String currency) {
        ExchangeRateDto exchangeRateDto = exchangeRateService
                .getExchangeRate(date, exchangeRateAppId, baseCurrency).getBody();
        Double rate = Optional.ofNullable(exchangeRateDto.getRates().get(currency.toUpperCase()))
                .orElseThrow(() -> new CurrencyNotFoundException("Валюта '" + currency + "' не найдена"));
        return rate;
    }

    private String getGifUrlByTag(String tag) {
        GifDto gifDto = gifService.getRandomGif(gifApiKey,tag).getBody();
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> images = oMapper.convertValue(gifDto.getData().get("images"), HashMap.class);
        Map<String, String> image = oMapper.convertValue(images.get("original"), HashMap.class);
        String url = image.get("url");
        return url;
    }
}
