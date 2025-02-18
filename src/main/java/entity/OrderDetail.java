package entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class OrderDetail {
    @EmbeddedId
    private OrderDetailsKey id;

    @ManyToOne
    @MapsId("itemCode")
    @JoinColumn(name = "item_code")
    Item item;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "course_id")
    Orders orders;

    private int qty;
    private double unitPrice;



}
