package vahdin.data;

import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class Vote implements Item {

    private static final SQLContainer container;
    private static final Logger logger = Logger.getGlobal();

    static {
        logger.info("Initializing votes");
        TableQuery table = new TableQuery("Vote", DB.pool);
        table.setVersionColumn("VERSION");
        try {
            container = new SQLContainer(table);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    private Item row;

    public Vote(String userId, int targetItemId, String type) {
        row = new PropertysetItem();
        row.addItemProperty("USERID", new ObjectProperty<String>(userId));
        row.addItemProperty("TARGETITEMID", new ObjectProperty<Integer>(
                targetItemId));
        row.addItemProperty("TYPE", new ObjectProperty<String>(type));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Property getItemProperty(Object id) {
        return row.getItemProperty(id);
    }

    @Override
    public Collection<?> getItemPropertyIds() {
        return row.getItemPropertyIds();
    }

    @Override
    public boolean addItemProperty(Object id, @SuppressWarnings("rawtypes")
    Property property) throws UnsupportedOperationException {
        return row.addItemProperty(id, property);
    }

    @Override
    public boolean removeItemProperty(Object id)
            throws UnsupportedOperationException {
        return row.removeItemProperty(id);
    }

    public static void commit() throws SQLException {
        container.commit();
    }

    @SuppressWarnings("unchecked")
    public void save() throws SQLException {
        Item item = row;
        row = container.getItem(container.addItem());
        row.getItemProperty("USERID").setValue(
                item.getItemProperty("USERID").getValue());
        row.getItemProperty("TARGETITEMID").setValue(
                item.getItemProperty("TARGETITEMID").getValue());
        row.getItemProperty("TYPE").setValue(
                item.getItemProperty("TYPE").getValue());
    }

}
