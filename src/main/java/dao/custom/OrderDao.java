package dao.custom;

import dto.OrderDto;

import java.sql.SQLException;

public interface OrderDao {
    boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException;
    OrderDto lastOrder() throws SQLException, ClassNotFoundException;
}

//--No need to delete or update an order
