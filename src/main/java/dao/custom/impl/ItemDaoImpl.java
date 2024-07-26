package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dto.ItemDto;
import dao.custom.ItemDao;
import entity.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item VALUES (?,?,?,?)";
        return CrudUtil.execute(sql,entity.getCode(),entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
        return CrudUtil.execute(sql,entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnHand(),entity.getCode());
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE code=?";
        return CrudUtil.execute(sql, value);
    }

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        List<Item> resultList = new ArrayList<>();
        //--SQL Query
        String sql = "SELECT * FROM item";

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            resultList.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return resultList;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    @Override
    public ItemDto searchItem(String code) throws SQLException, ClassNotFoundException {
        ItemDto resultDto = new ItemDto();
        String sql = "SELECT * FROM item WHERE code = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, code);
        ResultSet result = pstm.executeQuery();

        //--Process the result set
        while (result.next()) {
            //--Add the result set into the list as CustomerDto
            resultDto = new ItemDto(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );
        }
        return resultDto;
    }
}
