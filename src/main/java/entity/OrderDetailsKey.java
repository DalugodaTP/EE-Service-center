package entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class OrderDetailsKey implements Serializable {
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "item_code", nullable = false)
    private String itemCode;
}
