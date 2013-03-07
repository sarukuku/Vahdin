package vahdin.data;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import vahdin.VahdinUI;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.RowItem;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.ui.UI;

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

    public Mark(String name, Date time, String description, String userId) {
        row = new PropertysetItem();
        row.addItemProperty("NAME", new ObjectProperty<String>(name));
        row.addItemProperty("DESCRIPTION", new ObjectProperty<String>(
                description));
        row.addItemProperty("CREATIONTIME", new ObjectProperty<Date>(time));
        row.addItemProperty("USERID", new ObjectProperty<String>(userId));
        User user = ((VahdinUI) UI.getCurrent()).getCurrentUser();
        user.addExperience(10);
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

    public double getVoteCount() {
        int id = (Integer) getItemProperty("ID").getValue();
        double count = 0.0;
        List<Vote> votes = Vote.getVotesByTargetItemId(id, "Mark");
        for (int i = 0; i < votes.size(); i++) {
            count += votes.get(i).getPower();
        }
        return count;
    }

    public int getId() {
        Integer id = (Integer) ((RowItem) row).getId().getId()[0];
        return id == null ? 0 : id;
    }

    public String getUserID() {
        String id = (String) row.getItemProperty("USERID").getValue();
        return id;
    }

    public List<Bust> getBusts() {
        return Bust.getBustByMarkId(getId());
    }

    public static List<Mark> getMarksByUserId(String id) {
        List<Mark> marks = loadAll();
        List<Mark> usermarks = new ArrayList<Mark>();
        for (Mark mk : marks) {
            if (mk.getUserID() == ((VahdinUI) UI.getCurrent()).getCurrentUser()
                    .getUserId()) {
                usermarks.add(mk);
            }
        }
        return usermarks;
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

    /**
     * Get a specific mark by id
     * 
     */
    public static Mark getMarkById(int id) {
        List<Mark> marks = loadAll();
        for (int i = 0; i < marks.size(); i++) {
            if (marks.get(i).getId() == id) {
                return marks.get(i);
            }
        }
        return null;
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

    /**
     * Commits all the changes to the user table.
     * 
     * @throws SQLException
     */
    public static void commit() throws SQLException {
        container.commit();
    }

    @SuppressWarnings("unchecked")
    public void save() throws SQLException {
        Item item = row;
        row = container.getItem(container.addItem());
        row.getItemProperty("NAME").setValue(
                item.getItemProperty("NAME").getValue());
        row.getItemProperty("DESCRIPTION").setValue(
                item.getItemProperty("DESCRIPTION").getValue());
        row.getItemProperty("CREATIONTIME").setValue(
                item.getItemProperty("CREATIONTIME").getValue());
        row.getItemProperty("USERID").setValue(
                item.getItemProperty("USERID").getValue());
    }
    
    public static void addIdChangeListener(QueryDelegate.RowIdChangeListener listener) {
    	container.addRowIdChangeListener(listener);
    }
    
    public static void removeIdChangeListener(QueryDelegate.RowIdChangeListener listener) {
    	container.removeRowIdChangeListener(listener);
    }
    
}
