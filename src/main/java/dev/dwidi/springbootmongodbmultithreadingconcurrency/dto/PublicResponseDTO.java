package dev.dwidi.springbootmongodbmultithreadingconcurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicResponseDTO<T> {
    private Integer code;
    private String message;
    private T data;
}
