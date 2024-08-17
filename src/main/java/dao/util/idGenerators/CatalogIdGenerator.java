package dao.util.idGenerators;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CatalogIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        Connection connection = session.connection();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT MAX(CAST(SUBSTRING(catalog_id, 3) AS SIGNED)) FROM catalog");
            //--Move the pointer to the next entry
            rs.next();
            int nextId = rs.getInt(1);

            // Format the ID as CT00001
            return "CT" + String.format("%05d", ++nextId);
        } catch (SQLException e) {
            throw new HibernateException("Error generating custom ID", e);
        }
    }
}
