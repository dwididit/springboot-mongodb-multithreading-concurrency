package dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String username;
    private String email;
    private String phoneNumber;
}
