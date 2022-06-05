package ru.alfabank.testtask.exchangerateandgifs.dto;

import lombok.Data;

@Data
public class ErrorDto {
    private String message;
    private int errorCode;
}
