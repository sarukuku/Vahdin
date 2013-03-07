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
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.ui.UI;

public class Bust implements Item {

    private static final SQLContainer container;
    private static final Logger logger = Logger.getGlobal();
    private final VahdinUI ui = (VahdinUI) UI.getCurrent();

    static {
        logger.info("Initializing busts");
        TableQuery table = new TableQuery("Bust", DB.pool);
        table.setVersionColumn("VERSION");
        try {
            container = new SQLContainer(table);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    private Item row;

    public Bust(String title, String desc, Date time, double lat, double lon,
            int markId, String userId) {
        row = new PropertysetItem();
        row.addItemProperty("NAME", new ObjectProperty<String>(title));
        row.addItemProperty("DESCRIPTION", new ObjectProperty<String>(desc));
        row.addItemProperty("TIME", new ObjectProperty<Date>(time));
        row.addItemProperty("USERID", new ObjectProperty<String>(userId));
        row.addItemProperty("MARKID", new ObjectProperty<Integer>(markId));
        row.addItemProperty("COORDINATESLAT", new ObjectProperty<Double>(lat));
        row.addItemProperty("COORDINATESLON", new ObjectProperty<Double>(lon));
        User user = ((VahdinUI) UI.getCurrent()).getCurrentUser();
        user.addExperience(1);

    }

    private Bust(Item item) {
        row = item;
    }

    public String getTitle() {
        return (String) getItemProperty("NAME").getValue();
    }

    public int getId() {
        return (Integer) getItemProperty("ID").getValue();
    }

    public String getUserID() {
        String id = (String) row.getItemProperty("USERID").getValue();
        return id;
    }

    public int getMarkId() {
        return (Integer) getItemProperty("MARKID").getValue();
    }

    public String getDescription() {
        return (String) getItemProperty("DESCRIPTION").getValue();
    }

    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder date = new StringBuilder(
                dateFormat.format(getItemProperty("TIME").getValue()));
        return date.toString();
    }

    public double getLocationLat() {
        return (Double) getItemProperty("COORDINATESLAT").getValue();
    }

    public double getLocationLon() {
        return (Double) getItemProperty("COORDINATESLON").getValue();
    }

    public double getVoteCount() {
        int id = (Integer) getItemProperty("ID").getValue();
        double count = 0.0;
        List<Vote> votes = Vote.getVotesByTargetItemId(id, "Bust");
        for (int i = 0; i < votes.size(); i++) {
            count += votes.get(i).getPower();
        }
        return count;
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

    public static List<Bust> getBustsByUserId(String id) {
        List<Bust> busts = loadAll();
        List<Bust> userbusts = new ArrayList<Bust>();
        for (Bust bs : busts) {
            if (bs.getUserID() == ((VahdinUI) UI.getCurrent()).getCurrentUser()
                    .getUserId()) {
                userbusts.add(bs);
            }
        }
        return userbusts;
    }

    public static Bust getBustById(int id) {
        List<Bust> busts = loadAll();
        for (int i = 0; i < busts.size(); i++) {
            if (busts.get(i).getId() == id) {
                return busts.get(i);
            }
        }
        return null;
    }

    public static List<Bust> getBustByMarkId(int id) {
        List<Bust> bustsAll = loadAll();
        List<Bust> busts = new ArrayList<>();
        for (int i = 0; i < bustsAll.size(); i++) {
            if (bustsAll.get(i).getMarkId() == id) {
                busts.add(bustsAll.get(i));
            }
        }
        return busts;
    }

    public static List<Bust> loadAll() {
        ArrayList<Bust> busts = new ArrayList<>(container.size());
        for (@SuppressWarnings("rawtypes")
        Iterator i = container.getItemIds().iterator(); i.hasNext();) {
            RowId id = (RowId) i.next();
            Item item = container.getItem(id);
            busts.add(new Bust(item));
        }
        return busts;
    }

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
        row.getItemProperty("TIME").setValue(
                item.getItemProperty("TIME").getValue());
        row.getItemProperty("USERID").setValue(
                item.getItemProperty("USERID").getValue());
        row.getItemProperty("MARKID").setValue(
                item.getItemProperty("MARKID").getValue());
        row.getItemProperty("COORDINATESLAT").setValue(
                item.getItemProperty("COORDINATESLAT").getValue());
        row.getItemProperty("COORDINATESLON").setValue(
                item.getItemProperty("COORDINATESLON").getValue());
    }

}
