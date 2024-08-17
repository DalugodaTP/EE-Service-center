package dao.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.custom.CatalogDao;
import dao.util.HibernateUtil;
import dto.CatalogDto;
import entity.CatalogEntity;
import entity.StaffEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDaoImpl implements CatalogDao {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public boolean save(CatalogDto dto) throws SQLException, ClassNotFoundException {
        //--Get session
        Session session = HibernateUtil.getSession();
        try{
            Transaction transaction = session.beginTransaction();
            CatalogEntity catalogEntity = new CatalogEntity(
                    null,
                    dto.getProductName(),
                    dto.getCategory(),
                    dto.getImageLink()
            );

            catalogEntity.setStaff(session.find(StaffEntity.class,dto.getStaffId()));
            session.save(catalogEntity);
            transaction.commit();
            session.close();
            return true;
        }
        catch (Exception e){
            System.out.println("Catalog save failed");
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean update(CatalogDto entity) throws SQLException, ClassNotFoundException {
        //--Get session
        Session session = HibernateUtil.getSession();
        try{
            Transaction transaction = session.beginTransaction();

            //--find the customer using id and store as a local variable (Persistent state)
            CatalogEntity catalogEntity = session.find(CatalogEntity.class, entity.getCatalogId());

            //--Set values to this entity
            catalogEntity.setProductName(entity.getProductName());
            catalogEntity.setCategory(entity.getCategory());
            catalogEntity.setImageLink(entity.getImageLink());

            session.save(catalogEntity);

            transaction.commit();
            return true;
        }
        catch (Exception e){
            System.out.println("Catalog update failed");
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        //--Get session
        Session session = HibernateUtil.getSession();
        try{
            Transaction transaction = session.beginTransaction();

            //--delete the customer with pk that match the value
            session.delete(session.find(CatalogEntity.class,value));

            transaction.commit();
            return true;
        }
        catch (Exception e){
            System.out.println("Catalog delete attempt failed");
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<CatalogDto> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("From CatalogEntity ", CatalogEntity.class);
        List<CatalogEntity> list = query.list();
        List<CatalogDto> returnList = new ArrayList<>();
        for (CatalogEntity entity:list) {
            returnList.add(new CatalogDto(
                    entity.getCatalogId(),
                    entity.getProductName(),
                    entity.getCategory(),
                    entity.getImageLink(),
                    entity.getStaff().getStaffId()
            ));
        }
        session.close();
        return returnList;
    }

    @Override
    public CatalogDto getCatalogItem(String id) {
        Session session = HibernateUtil.getSession();
        CatalogEntity catalogEntity = session.find(CatalogEntity.class, id);
        return mapper.convertValue(catalogEntity, CatalogDto.class);
    }
}
