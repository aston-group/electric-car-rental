package ru.astongroup.carbooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingReqUpdateDto {

    @NotNull
    private Long carId;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;

}
