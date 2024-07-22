package model.impl;

import db.DBConnection;
import dto.CustomerDto;
import dto.ItemDto;
import model.ItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item VALUES (?,?,?,?)";

        PreparedStatement pstm =
                DBConnection.getInstance().getConnection().prepareStatement(sql);

        //--Set values
        pstm.setString(1, dto.getCode());
        pstm.setString(2, dto.getDesc());
        pstm.setDouble(3, dto.getUnitPrice());
        pstm.setInt(4, dto.getQty());

        int result = pstm.executeUpdate();

        if (result>0){
            return true;
        }

        return false;
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";


        PreparedStatement pstm =
                DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, dto.getDesc());
        pstm.setDouble(2, dto.getUnitPrice());
        pstm.setDouble(3, dto.getQty());
        pstm.setString(4, dto.getCode());

        int result = pstm.executeUpdate();

        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE code=?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, dto.getCode());

        int result = pstm.executeUpdate();

        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        List<ItemDto> resultList = new ArrayList<>();
        //--SQL Query
        String sql = "SELECT * FROM item";
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet resultSet = stm.executeQuery(sql);

        while (resultSet.next()){
            resultList.add(new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return resultList;
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
