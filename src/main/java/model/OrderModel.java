package model;

import dto.OrderDto;
import org.hibernate.criterion.Order;

public interface OrderModel {
    boolean saveOrder(OrderDto dto);
}

//--No need to delete or update an order
