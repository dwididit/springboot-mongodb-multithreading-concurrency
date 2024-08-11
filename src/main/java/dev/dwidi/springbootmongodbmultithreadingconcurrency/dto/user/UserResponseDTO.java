package dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user;

import dev.dwidi.springbootmongodbmultithreadingconcurrency.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }
}
