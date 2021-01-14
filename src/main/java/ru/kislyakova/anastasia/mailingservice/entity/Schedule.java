package ru.kislyakova.anastasia.mailingservice.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Embeddable
public class Schedule {
    @Column(name = "from_time", nullable = true)
    private LocalDateTime from;

    @Column(name = "to_time", nullable = true)
    private LocalDateTime to;

    private Duration duration;

    public Schedule(LocalDateTime from, LocalDateTime to, Duration duration) {
        this.from = from;
        this.to = to;
        this.duration = duration;
    }

    public Optional getFrom() {
        return Optional.ofNullable(from);
    }

    public Optional getTo() {
        return Optional.ofNullable(to);
    }
}
