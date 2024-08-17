package dev.dwidi.springbootmongodbmultithreadingconcurrency.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO {
    private String username;
    private String email;
    private String phoneNumber;
}
