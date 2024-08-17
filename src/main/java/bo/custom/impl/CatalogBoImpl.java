package bo.custom.impl;

import bo.custom.CatalogBo;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.custom.CatalogDao;
import dao.util.DaoType;
import dto.CatalogDto;
import entity.CatalogEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogBoImpl implements CatalogBo {

    CatalogDao catalogDao = DaoFactory.getInstance().getDao(DaoType.CATALOG_ITEM);
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean saveCatalog(CatalogDto catalogDto) throws SQLException, ClassNotFoundException {
        return catalogDao.save(catalogDto);

    }

    @Override
    public boolean updateCatalog(CatalogDto catalogDto) throws SQLException, ClassNotFoundException {
        return catalogDao.update(mapper.convertValue(catalogDto, CatalogDto.class));
    }

    @Override
    public boolean deleteCatalog(CatalogDto catalogDto) throws SQLException, ClassNotFoundException {
        return catalogDao.Delete(catalogDto.getCatalogId());
    }

    @Override
    public List<CatalogDto> getAllCatalog() throws SQLException, ClassNotFoundException {
        List<CatalogDto> entityList = catalogDao.getAll();
        List<CatalogDto> dtoList = new ArrayList<>();
        for (CatalogDto entity:entityList) {
            dtoList.add(mapper.convertValue(entity, CatalogDto.class));
        }
        return dtoList;
    }

    @Override
    public CatalogDto getCatalogItem(String id) {
        return catalogDao.getCatalogItem(id);
    }
}
