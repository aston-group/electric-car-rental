package ru.astongroup.usermanagement.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

import lombok.NoArgsConstructor;
import ru.astongroup.usermanagement.models.UserModel;
import ru.astongroup.usermanagement.models.enums.UserStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private String phone;
    private  byte[] image;
    private Date created;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userStatus=" + userStatus +
                ", phone='" + phone + '\'' +
                ", image=" + image +
                ", created=" + created +
                '}';
    }
}