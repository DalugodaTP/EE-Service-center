package dao.custom.impl;

import dao.DaoFactory;
import dao.util.DaoType;
import db.DBConnection;
import dto.OrderDto;
import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import entity.Orders;

import javax.persistence.criteria.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return new OrderDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                null
            );
        }
        return null;
    }

    @Override
    public boolean save(OrderDto entity) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try{
            //--Transaction to save orderdetails before the order
            connection =DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, entity.getOrderId());
            pstm.setString(2, entity.getDate());
            pstm.setString(3, entity.getCustId());

            //--if the data was saved, now we need to save order details
            if (pstm.executeUpdate()>0){
                boolean isDetailSaved = orderDetailsDao.saveOrderDetails(entity.getDto());
                if (isDetailSaved){
                    connection.commit();
                    return true;
                }
            }
        }catch (SQLException | ClassNotFoundException ex){
            //--if order details failed then rollback the changes
            connection.rollback();
        }finally {
            //--reset autocommit to auto save
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
