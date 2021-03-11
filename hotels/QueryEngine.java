package hotels;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Authors:
 * Einar Jónsson
 * Eydís Sylvía Einarsdóttir
 * Jaan Jaerving
 * Snorri Steinn Stefánsson Thors
 */
class QueryEngine {
    private static final String DB_PATH = "hotels/hotels.db"; // Package path to the generated database

    /**
     * Helper function that returns 
     * a valid open Connection object
     * 
     * @return Connection
     * @throws ClassNotFoundException
     */
    private static Connection connect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return connection;
    }

    /**
     * Helper function that closes
     * open database communication
     * 
     * @param c currently open Connection object
     * @param s currently open Statement object
     * @param r currently open ResultSet object
     */
    private static void close(Connection c, Statement s, ResultSet r) {
        try {
            r.close();
        } catch (Exception e) {
            /* Ignored */ }
        try {
            s.close();
        } catch (Exception e) {
            /* Ignored */ }
        try {
            c.close();
        } catch (Exception e) {
            /* Ignored */ }
    }

    /**
     * Queries the database according to the SQL query parameters
     * returns a set of cached row results that can be worked with
     * without needing to have an open database connection.
     * 
     * @param sql SQL query string to be executed
     * @return CachedRowSet cached rows from the result of the performed query
     * @throws ClassNotFoundException
     */
    static CachedRowSet query(String sql) throws ClassNotFoundException {
        Connection connection = null;
        Statement statement = null;
        CachedRowSet res = null;
        ResultSet rs = null;

        try {
            connection = connect();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            RowSetFactory factory = RowSetProvider.newFactory();
            res = factory.createCachedRowSet();
            res.populate(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(connection, statement, rs);
        }
        return res;
    }

    // TODO: Similar generic for insert and update statements
    // static void update(String sql) throws ClassNotFoundException {  }
}
