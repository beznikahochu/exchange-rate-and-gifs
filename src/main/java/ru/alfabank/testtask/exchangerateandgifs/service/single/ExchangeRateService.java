package ru.alfabank.testtask.exchangerateandgifs.service.single;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfabank.testtask.exchangerateandgifs.dto.ExchangeRateDto;

@FeignClient(name = "exchange-rate-service", url = "${exchange_rate_api_url}")
public interface ExchangeRateService {
    @GetMapping("/{date}.json")
    ResponseEntity<ExchangeRateDto> getExchangeRate(@PathVariable("date") String date,
                                                    @RequestParam("app_id") String appId,
                                                    @RequestParam("base") String base);
}
