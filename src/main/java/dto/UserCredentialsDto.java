package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCredentialsDto {
    private String userEmail;
    private String password;
}
