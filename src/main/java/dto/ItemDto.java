package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private String code;
    private String desc;
    private double unitPrice;
    private int qty;
}
