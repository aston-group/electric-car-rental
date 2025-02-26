package ru.astongroup.carbooking.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.astongroup.carbooking.dto.CarResponseDTO;

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

    // получаем машину

    public Optional<CarResponseDTO> getCarById(Long carId) {
        ResponseEntity<CarResponseDTO> response = restClient.get()
                .uri("/get/{id}", carId)
                .retrieve()
                .toEntity(CarResponseDTO.class);

        return Optional.ofNullable(response.getBody());
    }


//    public Optional<CarResponseDTO> getCarById(Long carId) {
//        log.info("Отправляем запрос на получения машины с id = {}", carId);
//        CarResponseDTO car = restClient.get()
//                .uri("/get/{id}", carId)
//                .header("Content-Type", "application/json")
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
//                    log.error("Ошибка при отправке запроса получения машины по id");
//                    throw new RuntimeException(String.format("Ошибка клиента: %s %s", response.getStatusCode(),
//                            response.getHeaders()));
//                })
//                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
//                    log.error("Ошибка сервера при получении машины по id");
//                    throw new RuntimeException(String.format("Ошибка сервера: %s %s", response.getStatusCode(),
//                            response.getHeaders()));
//                }))
//                .body(CarResponseDTO.class);
//
//        log.info("Машина с id = {} получена", carId);
//        return Optional.ofNullable(car);
//    }
}

