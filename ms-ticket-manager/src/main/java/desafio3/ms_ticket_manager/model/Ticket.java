package desafio3.ms_ticket_manager.model;

import desafio3.ms_event_manager.model.Event;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
public class Ticket {
    @Id
    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String eventName;
    private Event event;
    private String brlamount;
    private String usdamount;
    private String status;

    public Ticket() {
    }

    public Ticket(String ticketId, String customerName, String cpf, String customerMail, String eventId, String eventName, Event event, String brlamount, String usdamount, String status) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.eventId = eventId;
        this.eventName = eventName;
        this.event = event;
        this.brlamount = brlamount;
        this.usdamount = usdamount;
        this.status = status;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getBrlamount() {
        return brlamount;
    }

    public void setBrlamount(String brlamount) {
        this.brlamount = brlamount;
    }

    public String getUsdamount() {
        return usdamount;
    }

    public void setUsdamount(String usdamount) {
        this.usdamount = usdamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
