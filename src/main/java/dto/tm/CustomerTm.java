package dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private double salary;
    private String action;
}
