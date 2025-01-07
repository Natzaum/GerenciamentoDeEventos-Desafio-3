package desafio3.ms_ticket_manager.dto;

public class TicketDTO {
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String eventName;
    private EventResponseDTO event;
    private String brlamount;
    private String usdamount;

    public TicketDTO() {
    }

    public TicketDTO(String customerName, String cpf, String customerMail, String eventId, String eventName, EventResponseDTO event, String brlamount, String usdamount) {
        this.customerName = customerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.eventId = eventId;
        this.eventName = eventName;
        this.event = event;
        this.brlamount = brlamount;
        this.usdamount = usdamount;
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

    public EventResponseDTO getEvent() {
        return event;
    }

    public void setEvent(EventResponseDTO event) {
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
}
