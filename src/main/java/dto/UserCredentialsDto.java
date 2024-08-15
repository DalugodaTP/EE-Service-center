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

//--This object is only used to flow data from presentation layer to Dao layer to perform validation
