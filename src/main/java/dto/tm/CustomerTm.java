package dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTm {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String emailAddress;
    private String contactNumber;
}
