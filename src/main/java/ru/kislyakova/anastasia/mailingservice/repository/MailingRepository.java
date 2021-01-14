package ru.kislyakova.anastasia.mailingservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kislyakova.anastasia.mailingservice.entity.Mailing;

import java.util.List;

public interface MailingRepository extends CrudRepository<Mailing, Integer> {
    @Override
    List<Mailing> findAll();
}
