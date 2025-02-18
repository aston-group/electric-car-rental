package ru.astongroup.notifications.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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

    // Нужен метод для получения данных пользователя, либо самого пользователя с данными для рассылки(почта, телеграм)
}
