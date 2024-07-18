package model.impl;

import dto.ItemDto;
import model.ItemModel;

import java.sql.SQLException;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDto searchItem(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
