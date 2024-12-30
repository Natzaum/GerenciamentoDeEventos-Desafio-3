package desafio3.ms_event_manager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "events")
public class Event {
    @Id
    private String id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String cep;
    private String address;

    public Event() {}

    public Event(String name, String description, LocalDateTime dateTime, String cep, String address) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.cep = cep;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCep(){
        return cep;
    }

    public void setCep(String cep){
        this.cep = cep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
