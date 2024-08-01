package entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Entity
public class Orders {
    @Id
    private String orderId;
    private String date;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    public Orders(String orderId, String date) {
        this.orderId = orderId;
        this.date = date;
    }
}
