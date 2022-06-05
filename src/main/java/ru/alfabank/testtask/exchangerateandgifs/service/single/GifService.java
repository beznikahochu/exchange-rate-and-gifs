package ru.alfabank.testtask.exchangerateandgifs.service.single;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfabank.testtask.exchangerateandgifs.dto.GifDto;

@FeignClient(name = "gif-service", url = "${gif_api_url}")
public interface GifService {
    @GetMapping("/random")
    ResponseEntity<GifDto> getRandomGif(@RequestParam("api_key") String api_key,
                                        @RequestParam("tag") String tag);
}
