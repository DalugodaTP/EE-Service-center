package dao.custom;

import dao.CrudDao;
import dto.OrderDto;
import entity.Orders;

import javax.persistence.criteria.Order;
import java.sql.SQLException;

public interface OrderDao extends CrudDao<Orders> {
    OrderDto lastOrder() throws SQLException, ClassNotFoundException;
    boolean save(OrderDto entity) throws SQLException, ClassNotFoundException;

}

//--No need to delete or update an order
