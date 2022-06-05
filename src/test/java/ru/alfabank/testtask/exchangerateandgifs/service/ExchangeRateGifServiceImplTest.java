package ru.alfabank.testtask.exchangerateandgifs.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import ru.alfabank.testtask.exchangerateandgifs.dto.ExchangeRateDto;
import ru.alfabank.testtask.exchangerateandgifs.dto.GifDto;
import ru.alfabank.testtask.exchangerateandgifs.exception.CurrencyNotFoundException;
import ru.alfabank.testtask.exchangerateandgifs.service.fasade.ExchangeRateGifService;
import ru.alfabank.testtask.exchangerateandgifs.service.single.DownloadService;
import ru.alfabank.testtask.exchangerateandgifs.service.single.ExchangeRateService;
import ru.alfabank.testtask.exchangerateandgifs.service.single.GifService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class ExchangeRateGifServiceImplTest {

    @MockBean
    private DownloadService downloadService;
    @MockBean
    private ExchangeRateService exchangeRateService;
    @MockBean
    private GifService gifService;

    @Autowired
    private ExchangeRateGifService exchangeRateGifService;

    @Value("${gif_api_key}")
    private String gifApiKey;
    @Value("${exchange_rate_app_id}")
    private String exchangeRateAppId;
    @Value("${base_currency}")
    private String baseCurrency;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    void whenGetGifByCurrencyReturnRichGif() {
        ExchangeRateDto exchangeRateToday = new ExchangeRateDto();
        Map<String , Double> ratesToday = Map.of("RUB", 60.0);
        exchangeRateToday.setRates(ratesToday);

        ExchangeRateDto exchangeRateYesterday = new ExchangeRateDto();
        Map<String , Double> ratesYesterday = Map.of("RUB", 65.0);
        exchangeRateYesterday.setRates(ratesYesterday);

        Mockito.when(exchangeRateService.getExchangeRate(anyString(), eq(exchangeRateAppId), eq(baseCurrency)))
                .thenReturn(ResponseEntity.ok(exchangeRateToday), ResponseEntity.ok(exchangeRateYesterday));

        Map<String, String> original = new HashMap<>();
        original.put("url", "https://alfabank.ru/res/gifka.gif");
        Map<String, Object> images = new HashMap<>();
        images.put("original", original);
        Map<String , Object> data = new HashMap<>();
        data.put("images", images);
        GifDto gifDto = new GifDto();
        gifDto.setData(data);

        Mockito.when(gifService.getRandomGif(eq(gifApiKey), anyString()))
                .thenReturn(ResponseEntity.ok(gifDto));

        exchangeRateGifService.getGifByCurrency("RUB");

        Mockito.verify(gifService).getRandomGif(Mockito.anyString(),stringCaptor.capture());
        String gifTag = stringCaptor.getValue();

        assertEquals(gifTag,"rich", "GifTag должен быть \"rich\"");
    }

    @Test
    void whenGetGifByCurrencyReturnBrokeGif() {
        ExchangeRateDto exchangeRateToday = new ExchangeRateDto();
        Map<String , Double> ratesToday = Map.of("RUB", 65.0);
        exchangeRateToday.setRates(ratesToday);

        ExchangeRateDto exchangeRateYesterday = new ExchangeRateDto();
        Map<String , Double> ratesYesterday = Map.of("RUB", 60.0);
        exchangeRateYesterday.setRates(ratesYesterday);

        Mockito.when(exchangeRateService.getExchangeRate(anyString(), eq(exchangeRateAppId), eq(baseCurrency)))
                .thenReturn(ResponseEntity.ok(exchangeRateToday), ResponseEntity.ok(exchangeRateYesterday));

        Map<String, String> original = new HashMap<>();
        original.put("url", "https://alfabank.ru/res/gifka.gif");
        Map<String, Object> images = new HashMap<>();
        images.put("original", original);
        Map<String , Object> data = new HashMap<>();
        data.put("images", images);
        GifDto gifDto = new GifDto();
        gifDto.setData(data);

        Mockito.when(gifService.getRandomGif(eq(gifApiKey), anyString()))
                .thenReturn(ResponseEntity.ok(gifDto));

        exchangeRateGifService.getGifByCurrency("RUB");

        Mockito.verify(gifService).getRandomGif(Mockito.anyString(),stringCaptor.capture());
        String gifTag = stringCaptor.getValue();

        assertEquals(gifTag,"broke", "GifTag должен быть \"broke\"");
    }

    @Test
    void whenGetGifByCurrencyThrowsCurrencyNotFoundException() {
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        Map<String , Double> rates = new HashMap<>();
        rates.put("BYN", 2.5);
        rates.put("RUB", 60.0);
        exchangeRateDto.setRates(rates);

        Mockito.when(exchangeRateService.getExchangeRate(anyString(), eq(exchangeRateAppId), eq(baseCurrency)))
                .thenReturn(ResponseEntity.ok(exchangeRateDto));

        assertThrows(CurrencyNotFoundException.class, () -> exchangeRateGifService.getGifByCurrency("QWE"));
    }
}
