package vahdin.data;

import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class Vote implements Item {
    private static final SQLContainer container;
    private static final Logger logger = Logger.getGlobal();

    static {
        logger.info("Initializing users");
        TableQuery table = new TableQuery("Vote", DB.pool);
        table.setVersionColumn("version");
        try {
            container = new SQLContainer(table);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    private Item row;

    @Override
    public Property getItemProperty(Object id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<?> getItemPropertyIds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addItemProperty(Object id, Property property)
            throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeItemProperty(Object id)
            throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        return false;
    }

}
