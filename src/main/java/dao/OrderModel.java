package dao;

import dto.OrderDto;
import org.hibernate.criterion.Order;

import java.sql.SQLException;

public interface OrderModel {
    boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException;
    OrderDto lastOrder() throws SQLException, ClassNotFoundException;
}

//--No need to delete or update an order
