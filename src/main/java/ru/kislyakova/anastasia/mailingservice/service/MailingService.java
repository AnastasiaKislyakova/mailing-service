package ru.kislyakova.anastasia.mailingservice.service;

import ru.kislyakova.anastasia.mailingservice.dto.MailingCreationDto;
import ru.kislyakova.anastasia.mailingservice.entity.Mailing;

import java.util.List;

public interface MailingService {
    Mailing createMailing(MailingCreationDto mailingDto);
    Mailing sendMailing(int mailingId);
    List<Mailing> getMailings();
    Mailing getMailingById(int mailingId);
}
