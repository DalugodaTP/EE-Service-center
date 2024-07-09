package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private String id;
    private String name;
    private String address;
    private double salary;
}
