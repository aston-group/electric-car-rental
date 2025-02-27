package ru.astongroup.carchargermanagement.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.astongroup.carchargermanagement.dto.CarResponseDto;

import java.util.Optional;

@Service
@Slf4j
public class CarClient {

    private final RestClient restClient;

    @Autowired
    public CarClient(@Value("${car-management.url}") String baseUrl) {
        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Optional<CarResponseDto> getCarById(Long carId) {
        ResponseEntity<CarResponseDto> response = restClient.get()
                .uri("/get/{id}", carId)
                .retrieve()
                .toEntity(CarResponseDto.class);

        return Optional.ofNullable(response.getBody());
    }
}
