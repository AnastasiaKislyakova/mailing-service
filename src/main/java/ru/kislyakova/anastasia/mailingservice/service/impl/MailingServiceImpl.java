package ru.kislyakova.anastasia.mailingservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kislyakova.anastasia.channelclient.ChannelServiceClient;
import ru.kislyakova.anastasia.channelmodel.entity.Channel;
import ru.kislyakova.anastasia.emailclient.EmailServiceClient;
import ru.kislyakova.anastasia.emailmodel.dto.EmailCreationDto;
import ru.kislyakova.anastasia.emailmodel.entity.Email;
import ru.kislyakova.anastasia.mailingservice.dto.MailingCreationDto;
import ru.kislyakova.anastasia.mailingservice.entity.Mailing;
import ru.kislyakova.anastasia.mailingservice.exception.ChannelNotFoundException;
import ru.kislyakova.anastasia.mailingservice.repository.MailingRepository;
import ru.kislyakova.anastasia.mailingservice.service.MailingService;

import java.util.List;

@Service
public class MailingServiceImpl implements MailingService {
    private static final Logger logger = LoggerFactory.getLogger(MailingServiceImpl.class);

    private MailingRepository mailingRepository;

    private ChannelServiceClient channelServiceClient;
    private EmailServiceClient emailServiceClient;

    @Autowired
    public MailingServiceImpl(MailingRepository mailingRepository, ChannelServiceClient channelServiceClient,
                              EmailServiceClient emailServiceClient) {
        this.mailingRepository = mailingRepository;
        this.channelServiceClient = channelServiceClient;
        this.emailServiceClient = emailServiceClient;
    }

    @Override
    public Mailing createMailing(MailingCreationDto mailingDto) {
        Mailing mailing = new Mailing(mailingDto);

        int channelId = mailingDto.getChannelId();
        Channel channelById = channelServiceClient.getChannelById(channelId).block();
        if (channelById == null) {
            throw new ChannelNotFoundException(channelId);
        }

        mailing = mailingRepository.save(mailing);
        return mailing;
    }

    @Override
    public Mailing sendMailing(int mailingId) {
        Mailing mailing = mailingRepository.findById(mailingId).orElse(null);
        if (mailing == null) {
            return null;
        }

        mailing.setAttempt(mailing.getAttempt() + 1);
        sendEmails(mailing);
        mailing = mailingRepository.save(mailing);
        return mailing;
    }

    //   @Scheduled(cron = "0 17 ? * MON-FRI")
    @Scheduled(cron = "0 0/1 * * * *")
    private void sendScheduledEmails() {
        boolean sent = false;
        do {
            Mailing mailing = mailingRepository.findById(1).orElse(null);
            if (mailing == null) return;
            try {
                mailing.setAttempt(mailing.getAttempt() + 1);
                sendEmails(mailing);
                sent = true;
                mailingRepository.save(mailing);
            } catch (MailException ex) {
                logger.error("Error send emails", ex);
            }

        } while (!sent);
    }

    private void sendEmails(Mailing mailing) {
        int channelId = mailing.getChannelId();
        Channel channel = channelServiceClient.getChannelById(channelId).block();
        if (channel == null) {
            throw new ChannelNotFoundException(channelId);
        }

        List<String> emails = channel.getRecipients();
        String subject = mailing.getSubject();
        String text = mailing.getText();

        for (String email : emails) {
            EmailCreationDto emailDto = new EmailCreationDto(mailing.getId(), mailing.getAttempt(), email, subject, text);
            Email sentEmail = emailServiceClient.sendEmail(emailDto).block();
            logger.info("Email to {} status: {}", email, sentEmail.getStatus());
        }
    }

    @Override
    public List<Mailing> getMailings() {
        return mailingRepository.findAll();
    }

    @Override
    public Mailing getMailingById(int mailingId) {
        return mailingRepository.findById(mailingId).orElse(null);
    }


}
