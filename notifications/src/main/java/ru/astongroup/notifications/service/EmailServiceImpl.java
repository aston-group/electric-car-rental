package ru.astongroup.notifications.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.astongroup.notifications.entity.EmailDetails;
import ru.astongroup.notifications.entity.NotificationStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public NotificationStatus sendSimpleEmail(EmailDetails details) {
        try {
            log.info("Начинают отправку email на адрес {}", details.getRecipient());
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMessage());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            log.info("Сообщение на адрес {} отправлено", details.getRecipient());
        } catch (Exception e) {
            //throw new SendEmailException(String.format("Ошибка при отправке на адрес %s", details.getRecipient()));
            log.error("Ошибка при отправке на адрес {}}", details.getRecipient());
            return NotificationStatus.ERROR;
        }

        return NotificationStatus.SUCCESS;
    }
}
