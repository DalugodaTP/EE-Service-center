package bo.custom;

import bo.SuperBo;
import dto.StaffDto;
import dto.UserCredentialsDto;

public interface UserAuthenticationBo extends SuperBo {
    StaffDto verifyUser(UserCredentialsDto userCredentialsDto);
    boolean verifyUser(String email);
}
