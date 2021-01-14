package ru.kislyakova.anastasia.mailingservice.api;

import feign.Param;
import feign.RequestLine;
import ru.kislyakova.anastasia.mailingservice.dto.MailingCreationDto;
import ru.kislyakova.anastasia.mailingservice.entity.Mailing;

import java.util.List;

public interface MailingServiceClient {
    @RequestLine("POST /")
    Mailing createMailing(MailingCreationDto mailingDto);

    @RequestLine("POST /{mailingId}/send")
    Mailing sendMailing(@Param("mailingId") int mailingId);

    @RequestLine("GET /")
    List<Mailing> getMailings();

    @RequestLine("GET /{mailingId}")
    Mailing getMailingById(@Param("mailingId") int mailingId);
}
