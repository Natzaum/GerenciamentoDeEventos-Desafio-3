package desafio3.ms_ticket_manager.dto;

public class TicketDTO {
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String eventName;
    private double BRLamount;
    private double USDamount;

    public TicketDTO() {
    }

    public TicketDTO(String customerName, String cpf, String customerMail, String eventId, String eventName, double BRLamount, double USDamount) {
        this.customerName = customerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.eventId = eventId;
        this.eventName = eventName;
        this.BRLamount = BRLamount;
        this.USDamount = USDamount;
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

    public double getBRLamount() {
        return BRLamount;
    }

    public void setBRLamount(double BRLamount) {
        this.BRLamount = BRLamount;
    }

    public double getUSDamount() {
        return USDamount;
    }

    public void setUSDamount(double USDamount) {
        this.USDamount = USDamount;
    }
}
