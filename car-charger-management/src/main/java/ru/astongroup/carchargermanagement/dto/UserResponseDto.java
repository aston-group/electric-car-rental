package ru.astongroup.carchargermanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {
    @NotNull
    private Long userId;
    @NotNull
    private String userName;
}
