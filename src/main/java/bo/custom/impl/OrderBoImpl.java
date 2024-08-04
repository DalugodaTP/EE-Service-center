package bo.custom.impl;

import bo.custom.OrderBo;
import dao.DaoFactory;
import dao.custom.OrderDao;
import dao.util.DaoType;
import dto.OrderDto;
import entity.Orders;

import java.sql.SQLException;

public class OrderBoImpl implements OrderBo {
    private OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        OrderDto orderDto = orderDao.lastOrder();
        if (orderDto != null){
            return orderDto;
        }
        return null;
    }

    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDao.save(new OrderDto(
                dto.getOrderId(),
                dto.getDate(),
                dto.getCustId(),
                dto.getDto()
        ));
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try{
            OrderDto dto = orderDao.lastOrder();
            if (dto!=null){
                String id = orderDao.lastOrder().getOrderId();
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                return String.format("D%03d", num);
            }else{
                return "D001";
            }
        }catch (Exception e){
            System.out.println("Issue with the Id Generator");
        }
        return null;
    }

}
