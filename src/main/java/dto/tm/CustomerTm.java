package dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private double salary;
    private JFXButton btn;
}
