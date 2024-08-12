package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact_no;
}
