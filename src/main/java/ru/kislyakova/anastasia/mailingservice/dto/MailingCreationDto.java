package ru.kislyakova.anastasia.mailingservice.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
public class MailingCreationDto {

    @Min(1)
    private int channelId;

    @NotNull
    private String subject;

    @NotNull
    private String text;

   // private Schedule schedule;
}
