package com.tus.springcourse.firstrex.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class ErrorResponse {

    private int code;
    private String massage;
    private LocalDate errTime;

}
