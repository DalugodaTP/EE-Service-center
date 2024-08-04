package bo.custom;

import bo.SuperBo;
import dto.OrderDto;

import java.sql.SQLException;

public interface OrderBo extends SuperBo {
    OrderDto lastOrder() throws SQLException, ClassNotFoundException;
    boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
}
