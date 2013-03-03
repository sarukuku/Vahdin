package vahdin.data;

import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class Bust implements Item {

    private static final SQLContainer container;
    private static final Logger logger = Logger.getGlobal();

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

    private String title;
    private int id;
    private String description;
    private int imageId;
    private String time;
    private double locationLat;
    private double locationLon;

    public Bust(String title, int id, String desc, int imgId, String time,
            double lat, double lon) {
        this.title = title;
        this.id = id;
        this.description = desc;
        this.imageId = imgId;
        this.time = time;
        this.locationLat = lat;
        this.locationLon = lon;
    }

    public String getTitle() {
        return this.title;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public int getImageId() {
        return this.imageId;
    }

    public String getTime() {
        return this.time;
    }

    public double getLocationLat() {
        return this.locationLat;
    }

    public double getLocationLon() {
        return this.locationLon;
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

}
