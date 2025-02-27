package ru.astongroup.carbooking.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.astongroup.carbooking.dto.UserRequestDto;

import java.util.Optional;

@Service
@Slf4j
public class UserClient {

    private final RestClient restClient;

    @Autowired
    public UserClient(@Value("${user-management.url}") String baseUrl) {
        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // получаем юзера
    public Optional<UserRequestDto> getUserById(Long userId) {
        log.info("Отправляем запрос на получения пользователя с id = {}", userId);
        UserRequestDto user = restClient.get()
                .uri("/" + userId)
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    log.error("Ошибка при отправке запроса получения пользователя по id");
                    throw new RuntimeException(String.format("Ошибка клиента: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    log.error("Ошибка сервера при получении пользователя по id");
                    throw new RuntimeException(String.format("Ошибка сервера: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                }))
                .body(UserRequestDto.class);

        log.info("Пользователь с id = {} получен", userId);
        return Optional.ofNullable(user);
    }
}

