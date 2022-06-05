package ru.alfabank.testtask.exchangerateandgifs.exception;

public class CurrencyNotFoundException extends RuntimeException{
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
