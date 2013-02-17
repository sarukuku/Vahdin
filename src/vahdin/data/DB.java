package vahdin.data;

import java.sql.SQLException;
import java.util.logging.Logger;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;

public class DB {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String CONNECTION_URI = "jdbc:mysql://naantalinsiniset.fi:3306/naantal1_vahdin";
    private static final String USER = "naantal1_vahdin";
    private static final String PASSWORD = "FS0S~M?a#cZ=";
    private static final Logger logger = Logger.getGlobal();

    public static final JDBCConnectionPool pool;

    static {
        try {
            logger.info("Initializing database connection");
            pool = new SimpleJDBCConnectionPool(DRIVER, CONNECTION_URI, USER,
                    PASSWORD);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
}
