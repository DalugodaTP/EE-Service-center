package bo.custom;

import bo.SuperBo;
import dto.CatalogDto;

import java.sql.SQLException;
import java.util.List;

public interface CatalogBo extends SuperBo {
    public boolean saveCatalog(CatalogDto catalogDto) throws SQLException, ClassNotFoundException;
    public boolean updateCatalog(CatalogDto catalogDto) throws SQLException, ClassNotFoundException;
    public boolean deleteCatalog(CatalogDto catalogDto) throws SQLException, ClassNotFoundException;
    public List<CatalogDto> getAllCatalog() throws SQLException, ClassNotFoundException;
    public CatalogDto getCatalogItem(String id);
}
