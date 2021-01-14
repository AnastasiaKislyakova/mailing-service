package ru.kislyakova.anastasia.mailingservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.webclient.WebReactiveFeign;
import ru.kislyakova.anastasia.channelclient.ChannelServiceClient;
import ru.kislyakova.anastasia.emailclient.EmailServiceClient;

@Configuration
public class FeignClientsConfiguration {
    @Bean
    public ChannelServiceClient channelServiceClient() {
//        return Feign.builder()
//                .encoder(new GsonEncoder())
//                .decode404()
//                .decoder(new GsonDecoder())
//                .target(ChannelServiceClient.class, "http://localhost:8094/api/channels");
        return WebReactiveFeign
                .<ChannelServiceClient>builder()
                .target(ChannelServiceClient.class, "http://localhost:8094/api/channels");
    }

    @Bean
    public EmailServiceClient emailServiceClient() {
        return WebReactiveFeign
                .<EmailServiceClient>builder()
                .target(EmailServiceClient.class, "http://localhost:8095/api/emails");
    }
}
