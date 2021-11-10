package entities;


import java.sql.Date;

public class Waybill {

    private int id;
    private Date time;
    private int sender;

    public Waybill(int id, Date time, int sender) {
        this.id = id;
        this.time = time;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
}
