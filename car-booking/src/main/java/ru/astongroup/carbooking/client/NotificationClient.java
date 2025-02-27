package ru.astongroup.carbooking.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.astongroup.carbooking.dto.NotificationCreateDto;
import ru.astongroup.carbooking.dto.NotificationResponseDto;
import ru.astongroup.carbooking.exception.ClientRequestException;
import ru.astongroup.carbooking.exception.ServerRequestException;

@Service
@Slf4j
public class NotificationClient {

    private final RestClient restClient;

    public NotificationClient(@Value("${notifications.url}") String baseUrl) {
        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public NotificationResponseDto sendNotification(NotificationCreateDto dto) {
        log.info("Пытаюсь отправить новое уведомление {}", dto);
        NotificationResponseDto responseDto = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    log.error("Ошибка при отправке запроса получения пользователя по id");
                    throw new ClientRequestException(String.format("Ошибка клиента: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    log.error("Ошибка сервера при получении пользователя по id");
                    throw new ServerRequestException(String.format("Ошибка сервера: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                }))
                .body(NotificationResponseDto.class);
        log.info("Уведомление отправлено.");

        return responseDto;
    }
}
