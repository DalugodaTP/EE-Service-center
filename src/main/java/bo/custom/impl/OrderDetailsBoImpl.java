package bo.custom.impl;

import bo.custom.OrderDetailsBo;
import dao.DaoFactory;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dao.util.DaoType;

public class OrderDetailsBoImpl implements OrderDetailsBo {
    private OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
}
