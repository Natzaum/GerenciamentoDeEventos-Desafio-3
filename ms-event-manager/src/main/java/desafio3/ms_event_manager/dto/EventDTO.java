package desafio3.ms_event_manager.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventDTO {
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String cep;
    private String address;

    public EventDTO() {}

    public EventDTO(String name, String description, LocalDateTime dateTime, String cep, String address) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.cep = cep;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
