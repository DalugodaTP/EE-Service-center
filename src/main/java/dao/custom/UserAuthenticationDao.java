package dao.custom;

import bo.SuperBo;
import dao.CrudDao;
import dto.UserCredentialsDto;
import entity.StaffEntity;

public interface UserAuthenticationDao extends CrudDao<StaffEntity> {
    StaffEntity verifyUser (UserCredentialsDto userCredentialsDto);
    StaffEntity verifyUser(String email);
}
