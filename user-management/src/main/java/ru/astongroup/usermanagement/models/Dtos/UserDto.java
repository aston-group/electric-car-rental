package ru.astongroup.usermanagement.models.Dtos;

import lombok.Data;
import java.util.Date;

import ru.astongroup.usermanagement.models.UserModel;
import ru.astongroup.usermanagement.models.enums.UserStatus;

@Data
public class UserDto {
    private long id;
    private String name;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private String phone;
    private  byte[] image;
    private Date created;

    public UserDto() {}

    public UserDto(UserModel user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.userStatus = user.getUserStatus();
        this.phone = user.getPhone();
        this.image = user.getImage();
        this.created = user.getCreated();
    }
}