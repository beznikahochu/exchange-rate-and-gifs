package ru.alfabank.testtask.exchangerateandgifs.handler;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.alfabank.testtask.exchangerateandgifs.dto.ErrorDto;
import ru.alfabank.testtask.exchangerateandgifs.exception.CurrencyNotFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CurrencyNotFoundException.class)
    protected ErrorDto handleNotFoundException(CurrencyNotFoundException exception) {
        log.info("Поймано исключение CurrencyNotFoundException: {}", exception.toString());

        ErrorDto notFoundError = new ErrorDto();
        notFoundError.setMessage(exception.getMessage());
        notFoundError.setErrorCode(HttpStatus.NOT_FOUND.value());

        log.info("CurrencyNotFoundException: сообщение: {}, код ошибки: {}",
                notFoundError.getMessage(),
                notFoundError.getErrorCode());

        return notFoundError;
    }
}
