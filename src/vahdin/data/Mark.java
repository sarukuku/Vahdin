package vahdin.data;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class Mark implements Item {

    private static final SQLContainer container;
    private static final Logger logger = Logger.getGlobal();

    static {
        logger.info("Initializing marks");
        TableQuery table = new TableQuery("Mark", DB.pool);
        table.setVersionColumn("VERSION");
        try {
            container = new SQLContainer(table);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    private Item row;

    // the emphasis of one vote determines on the PrestigePower of the user,
    // which is a 2 decimal float;
    // Changed voteCount type from int to float.
    private float voteCount = 0;
    private int id;
    private ArrayList<Bust> busts = new ArrayList<Bust>();

    @SuppressWarnings("unchecked")
    public Mark(String name, Date time, String description, int photoId, int id) {
        row = container.getItem(container.addItem());
        row.getItemProperty("NAME").setValue(name);
        row.getItemProperty("CREATIONTIME").setValue(time);
        row.getItemProperty("DESCRIPTION").setValue(description);
    }

    public String getTitle() {
        return (String) getItemProperty("NAME").getValue();
    }

    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder date = new StringBuilder(
                dateFormat.format(getItemProperty("CREATIONTIME").getValue()));
        return date.toString();
    }

    public String getDescription() {
        return (String) getItemProperty("DESCRIPTION").getValue();
    }

    public int getPhotoId() {
        return getId();
    }

    public float getVoteCount() {
        return this.voteCount; // TODO:
    }

    public int getId() {
        /*
         * Integer id = (Integer) row.getItemProperty("Id").getValue(); return
         * id == null ? 0 : id;
         */
        Integer id = (Integer) row.getItemProperty("ID").getValue();
        return id == null ? 0 : id;
    }

    public ArrayList<Bust> getBusts() {
        return this.busts; // TODO:
    }

    public void addBust(Bust bust) {
        this.busts.add(bust); // TODO:
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

    private Mark(Item item) {
        row = item;
    }

    public static List<Mark> loadAll() {
        ArrayList<Mark> marks = new ArrayList<>(container.size());
        for (@SuppressWarnings("rawtypes")
        Iterator i = container.getItemIds().iterator(); i.hasNext();) {
            RowId id = (RowId) i.next();
            Item item = container.getItem(id);
            marks.add(new Mark(item));
        }
        return marks;
    }

    @SuppressWarnings("unchecked")
    public void save() throws SQLException {
        Item item = row;
        row = container.getItem(container.addItem());
        row.getItemProperty("NAME").setValue(
                item.getItemProperty("NAME").getValue());
    }

}
