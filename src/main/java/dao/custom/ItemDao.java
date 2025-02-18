package dao.custom;

import dao.CrudDao;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao extends CrudDao<Item> {
    ItemDto getItem(String code) throws SQLException, ClassNotFoundException;
    ItemDto searchItem(String id) throws SQLException, ClassNotFoundException;
}
