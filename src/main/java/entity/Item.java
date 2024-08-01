package entity;

import lombok.*;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Item {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
