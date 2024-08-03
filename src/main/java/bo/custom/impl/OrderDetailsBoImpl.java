package bo.custom.impl;

import bo.custom.OrderDetailsBo;
import dao.DaoFactory;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dao.util.DaoType;
import dto.OrderDetailsDto;
import entity.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBoImpl implements OrderDetailsBo {
    private OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    public List<OrderDetailsDto> getOrderDetails(String orderId){
//        List<OrderDetailsDto> list = new ArrayList<>();
//        List<OrderDetail> orderDetails = orderDetailsDao.getOrderDetails(orderId);
//        for (OrderDetail x:orderDetails) {
//            list.add(new OrderDetailsDto(
//                    x.getOrderId(),
//                    x.getitem_code(),
//                    x.getQty(),
//                    x.getUnitPrice()
//            ));
//        }
        return null;
    }
}
