package ru.astongroup.carchargermanagement.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.astongroup.carchargermanagement.dto.UserResponseDto;

import java.rmi.RemoteException;
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

    public Optional<UserResponseDto> getUserById(Long userId) {
        log.info("Запрос на получение пользователя с id = {}", userId);
        UserResponseDto userResponseDto = restClient.get()
                .uri("/" + userId)
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    log.error("Ошибка при отправке запроса получения пользователя по id = {}", userId);
                    throw new RuntimeException(String.format("Ошибка клиента: %s%s", response.getStatusCode(),
                            response.getHeaders()));
                }))
                .onStatus(HttpStatusCode::is5xxServerError, (((request, response) -> {
                    log.error("Ошибка сервера при получении пользователя по id = {}", userId);
                    throw new RemoteException(String.format("Ошибка клиента: %s%s", response.getStatusCode(),
                            response.getHeaders()));
                })))
                .body(UserResponseDto.class);
        log.info("пользователь с id = {} получен", userResponseDto.getUserId());
        return Optional.ofNullable(userResponseDto);
    }
}
