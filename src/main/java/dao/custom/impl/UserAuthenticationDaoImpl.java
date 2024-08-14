package dao.custom.impl;

import dao.custom.UserAuthenticationDao;
import dao.util.HibernateUtil;
import dto.UserCredentialsDto;
import entity.StaffEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserAuthenticationDaoImpl implements UserAuthenticationDao {
    @Override
    public StaffEntity verifyUser(UserCredentialsDto userCredentialsDto) {
        //--Get a sesssion from Hibernate
        Session session = HibernateUtil.getSession();

        //--Check if the entity is available in the staff (:email is a placeholder provided later)
        String hql = "from StaffEntity where email like :email";

        Query<StaffEntity> query = session.createQuery(hql);

        //--attach the value to variable
        query.setParameter("email", userCredentialsDto.getUserEmail());
        StaffEntity staff = query.getSingleResult();
        session.close();
        return staff;
    }

    @Override
    public StaffEntity verifyUser(String email) {
        return null;
    }
    @Override
    public boolean save(StaffEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(StaffEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<StaffEntity> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
