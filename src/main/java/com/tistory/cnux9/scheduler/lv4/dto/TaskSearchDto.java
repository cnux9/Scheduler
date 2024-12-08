package com.tistory.cnux9.scheduler.lv4.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TaskSearchDto {
    String email;
    LocalDate date;
}
