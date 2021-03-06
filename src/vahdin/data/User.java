package vahdin.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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

public class User implements Item {

    private static final SQLContainer container;
    private static final Logger logger = Logger.getGlobal();

    static {
        logger.info("Initializing users");
        TableQuery table = new TableQuery("User", DB.pool);
        table.setVersionColumn("VERSION");
        try {
            container = new SQLContainer(table);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    public static User load(String id) {
        Item item = container.getItem(new RowId(new Object[] { id }));
        if (item == null) {
            return null;
        }
        return new User(item);
    }

    public User(String id) {
        row = new PropertysetItem();
        row.addItemProperty("ID", new ObjectProperty<String>(id));
        row.addItemProperty("NAME", new ObjectProperty<String>(""));
        row.addItemProperty("ADMIN", new ObjectProperty<Boolean>(false));
        row.addItemProperty("EXPERIENCE", new ObjectProperty<Integer>(0));
        row.addItemProperty("PRESTIGE", new ObjectProperty<Integer>(0));
    }

    public static User guest() {
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("ID", new ObjectProperty<String>(""));
        item.addItemProperty("NAME", new ObjectProperty<String>("guest"));
        item.addItemProperty("EXPERIENCE", new ObjectProperty<Integer>(0));
        item.addItemProperty("PRESTIGE", new ObjectProperty<Integer>(-1000));
        return new User(item);
    }

    private Item row;
    private boolean loggedIn = false;

    private User(Item item) {
        row = item;
    }

    /**
     * get raw prestige value from DB
     * 
     * @return prestige value
     */
    private int getPrestigeValue() {
        int value = (Integer) this.getItemProperty("PRESTIGE").getValue();
        return value;
    }

    /**
     * Get the id of the user
     * 
     * @return id userid
     */
    public String getUserId() {
        String id = (String) this.getItemProperty("ID").getValue();
        return id;
    }

    /** @return true if the user is a guest. */
    public boolean isGuest() {
        return "".equals(getItemProperty("ID").getValue());
    }

    /**
     * Returns true if the user is currently marked as logged in.
     * 
     * @return The current login status of the user.
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /** Marks the user as logged in. */
    public void markLoggedIn() {
        loggedIn = true;
    }

    /**
     * Checks if user is admin
     * 
     * @return true if admin
     */
    public boolean isAdmin() {
        boolean admin = isLoggedIn()
                && (boolean) getItemProperty("ADMIN").getValue();
        return admin;
    }

    /** Marks the user as logged out. */
    public void markLoggedOut() {
        loggedIn = false;
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

    public double getAllVotes() {
        List<Vote> votes = new ArrayList<Vote>();
        List<Mark> marks = Mark.getMarksByUserId(getUserId());
        List<Bust> busts = Bust.getBustsByUserId(getUserId());
        for (Mark mk : marks) {
            votes.addAll(Vote.getVotesByTargetItemId(mk.getId(), "Mark"));
        }
        for (Bust bs : busts) {
            votes.addAll(Vote.getVotesByTargetItemId(bs.getId(), "Bust"));
        }
        double votesum = 0;
        for (Vote v : votes) {
            votesum += v.getPower();
        }
        return votesum;

    }

    public String getName() {
        String name = this.getItemProperty("NAME").getValue().toString();
        return name;
    }

    public int getExperience() {
        int value = (Integer) this.getItemProperty("EXPERIENCE").getValue();
        return value;
    }

    public Vote getVote(Votable target) {
        final VahdinUI ui = (VahdinUI) UI.getCurrent();
        List<Vote> all = Vote.loadAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getTargetItemId() == target.getId()
                    && all.get(i).getType().equals(target.getVotableName())
                    && all.get(i).getUserId() == ui.getCurrentUser()
                            .getUserId()) {
                return all.get(i);
            }
        }
        return null;
    }

    /*
     * Get the verbal Prestige rank of the user
     * 
     * @return Prestige rank or "title"
     */
    public String getPrestigeRank() {
        int value = getPrestigeValue();
        String rank = null;

        if (value < 0) {
            rank = "Trolol";
        } else if (value < 1000) {
            rank = "Nobody";
        } else if (value < 2000) {
            rank = "Newbie";
        } else if (value < 4000) {
            rank = "Stalker";
        } else if (value < 8000) {
            rank = "Pro Stalker";
        } else if (value < 16000) {
            rank = "Master Stalker";
        } else {
            rank = "Gaylord";
        }
        return rank;
    }

    /*
     * Get the user's Vote Power based on their prestige value
     * 
     * @return 2 point decimal float power
     */
    public float getPrestigePower() {
        int power = (int) (java.lang.Math.sqrt(getPrestigeValue()));
        return power / 200 + 1;
    }

    @SuppressWarnings("unchecked")
    public void setName(String name) {
        this.getItemProperty("NAME").setValue(name);
    }

    @SuppressWarnings("unchecked")
    public void addExperience(int experience) {
        int exp = experience + getExperience();
        this.getItemProperty("EXPERIENCE").setValue(exp);
    }

    /*
     * Add prestige to user
     * 
     * @param prestige Prestige POWER of user giving vote lol
     */
    @SuppressWarnings("unchecked")
    public void addPrestige(double prestige) {
        double pre = prestige * 100 + getPrestigeValue();
        this.getItemProperty("PRESTIGE").setValue((int) pre);
    }

    public void reload() {
        container.refresh();
        row = container.getItem(new RowId(new Object[] { getUserId() }));
    }

    public static User getUserById(String id) {
        List<User> users = loadAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    public static List<User> loadAll() {
        ArrayList<User> users = new ArrayList<>(container.size());
        for (@SuppressWarnings("rawtypes")
        Iterator i = container.getItemIds().iterator(); i.hasNext();) {
            RowId id = (RowId) i.next();
            Item item = container.getItem(id);
            users.add(new User(item));
        }
        return users;
    }

    /**
     * Commits all the changes to the user table.
     * 
     * @throws SQLException
     */
    public static void commit() throws SQLException {
        container.commit();
    }

    /**
     * Saves a newly created user to the database.
     * 
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public void save() throws SQLException {
        Item item = row;
        row = container.getItem(container.addItem());
        row.getItemProperty("ID").setValue(
                item.getItemProperty("ID").getValue());
        row.getItemProperty("NAME").setValue(
                item.getItemProperty("NAME").getValue());
        row.getItemProperty("ADMIN").setValue(
                item.getItemProperty("ADMIN").getValue());
        row.getItemProperty("EXPERIENCE").setValue(
                item.getItemProperty("EXPERIENCE").getValue());
        row.getItemProperty("PRESTIGE").setValue(
                item.getItemProperty("PRESTIGE").getValue());
    }
}
