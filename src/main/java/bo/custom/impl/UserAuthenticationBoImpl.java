package bo.custom.impl;

import bo.custom.UserAuthenticationBo;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.custom.UserAuthenticationDao;
import dao.util.DaoType;
import dto.StaffDto;
import dto.UserCredentialsDto;
import entity.StaffEntity;

public class UserAuthenticationBoImpl implements UserAuthenticationBo {
    UserAuthenticationDao userAuthenticationDao = DaoFactory.getInstance().getDao(DaoType.USER_AUTHENTICATION);
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public StaffDto verifyUser(UserCredentialsDto userCredentialsDto) {
        //--Store the data until encryption is implemented
        UserCredentialsDto userCredentials = new UserCredentialsDto(
                userCredentialsDto.getUserEmail(),
                userCredentialsDto.getPassword()
        );

        //--Get the staff entity from the db
        StaffEntity staffEntity = userAuthenticationDao.verifyUser(userCredentials);

        //--check if entity from the db and front end
        if (userCredentialsDto.getUserEmail().equals(staffEntity.getEmail()) &&
        userCredentialsDto.getPassword().equals(staffEntity.getPassword())){
            StaffDto staffDto = mapper.convertValue(staffEntity, StaffDto.class);
            //--Remove the password after authentication
            staffDto.setPassword("");
            return staffDto;
        }
        return null;
    }

    @Override
    public boolean verifyUser(String email) {
        return false;
    }
}
