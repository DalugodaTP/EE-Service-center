package dao.custom.impl;

import dao.custom.UserAuthenticationDao;
import dao.util.HibernateUtil;
import dto.UserCredentialsDto;
import entity.StaffEntity;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
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
        try {
            StaffEntity staff = query.getSingleResult();  // Attempt to retrieve the single result
            //--compare the password here, then return the entity
            if (staff.getPassword().equals(userCredentialsDto.getPassword())) {
                return staff; // Password matches, return the staff entity
            } else {
                System.out.println("Invalid password for email: " + userCredentialsDto.getUserEmail());
            }
        } catch (NoResultException e) {
            System.out.println("No staff member found with email: " + userCredentialsDto.getUserEmail());
        }catch (NonUniqueResultException e) {
            System.out.println("Multiple staff members found with the same email: " + userCredentialsDto.getUserEmail());
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public StaffEntity verifyUser(String email) {
        //--Get a sesssion from Hibernate
        Session session = HibernateUtil.getSession();

        //--Check if the entity is available in the staff (:email is a placeholder provided later)
        String hql = "from StaffEntity where email like :email";

        Query<StaffEntity> query = session.createQuery(hql);

        //--attach the value to variable
        query.setParameter("email", email);
        try {
            StaffEntity staff = query.getSingleResult();  // Attempt to retrieve the single result
            return staff;
        } catch (NoResultException e) {
            System.out.println("No staff member found with email: " + email);
        }catch (NonUniqueResultException e) {
            System.out.println("Multiple staff members found with the same email: " + email);
        } finally {
            session.close();
        }
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
