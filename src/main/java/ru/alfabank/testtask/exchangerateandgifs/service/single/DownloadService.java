package ru.alfabank.testtask.exchangerateandgifs.service.single;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "download-service", url = "https://placeholder")
public interface DownloadService {
    @GetMapping
    ResponseEntity<byte[]> downloadResourceByUrl(URI uri);
}
