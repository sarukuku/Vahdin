package vahdin.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.sqlcontainer.RowId;
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

    public Vote(String userId, int targetItemId, String type, double power) {
        row = new PropertysetItem();
        row.addItemProperty("USERID", new ObjectProperty<String>(userId));
        row.addItemProperty("TARGETITEMID", new ObjectProperty<Integer>(
                targetItemId));
        row.addItemProperty("TYPE", new ObjectProperty<String>(type));
        row.addItemProperty("POWER", new ObjectProperty<Double>(power));
        addPrestigeToVoteOwner(targetItemId, type, power);
    }

    private Vote(Item item) {
        row = item;
    }

    public String getUserId() {
        return (String) getItemProperty("USERID").getValue();
    }

    public String getType() {
        return (String) getItemProperty("TYPE").getValue();
    }

    public int getTargetItemId() {
        return (Integer) getItemProperty("TARGETITEMID").getValue();
    }

    public double getPower() {
        return (Double) getItemProperty("POWER").getValue();
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

    private void addPrestigeToVoteOwner(int targetItemId, String type,
            double power) {
        if (type.equals("Mark")) {
            Mark m = Mark.getMarkById(targetItemId);
            User u = User.getUserById(m.getUserID());
            u.addPrestige(power);
        } else {
            Bust b = Bust.getBustById(targetItemId);
            User u = User.getUserById(b.getUserID());
            u.addPrestige(power);
        }
    }

    /**
     * Get a list of all votes related to a Mark or a Bust
     * 
     * @param id
     *            id of Mark or Bust
     * @param type
     *            type of object: Mark or Bust
     * @return List of all Votes*
     */
    public static List<Vote> getVotesByTargetItemId(int id, String type) {
        List<Vote> all = loadAll();
        List<Vote> votes = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getTargetItemId() == id
                    && all.get(i).getType().equals(type)) {
                votes.add(all.get(i));
            }
        }
        return votes;
    }

    public static boolean hasVoted(int itemId, String itemType, String userId) {
        List<Vote> votes = getVotesByTargetItemId(itemId, itemType);
        for (int i = 0; i < votes.size(); i++) {
            if (votes.get(i).getUserId() == userId) {
                return true;
            }
        }
        return false;
    }

    public static List<Vote> loadAll() {
        ArrayList<Vote> votes = new ArrayList<>(container.size());
        for (@SuppressWarnings("rawtypes")
        Iterator i = container.getItemIds().iterator(); i.hasNext();) {
            RowId id = (RowId) i.next();
            Item item = container.getItem(id);
            votes.add(new Vote(item));
        }
        return votes;
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
        row.getItemProperty("POWER").setValue(
                item.getItemProperty("POWER").getValue());
    }

}
