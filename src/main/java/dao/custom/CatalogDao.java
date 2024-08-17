package dao.custom;

import dao.CrudDao;
import dto.CatalogDto;

public interface CatalogDao extends CrudDao<CatalogDto> {
    public CatalogDto getCatalogItem(String id);
}
