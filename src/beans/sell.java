package beans;

import DB_Conn.db_conn;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class sell {
    public static DateFormat df;
    public static Date curr_time;
    private static db_conn con = new db_conn();

    public sell() throws ClassNotFoundException, SQLException {
        con.conn();
        df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        curr_time = Calendar.getInstance().getTime();
    }
    
    
    public static void insertSell(String cols, String table, String total) throws SQLException {
        int done = con.exUpdate("insert into " + table + " (" + cols + ") values ('" + df.format(curr_time) + "', '" +total+ "')");
    }
    
    public static void insert(String cols, String table, String vals) throws SQLException {
        curr_time = Calendar.getInstance().getTime();
        int done = con.exUpdate("insert into " + table + " (" + cols + ") values (" + vals + ")");
    }
    
    public static String[][] select() throws SQLException {
        String query = "select * from sell ";
        String[][] arr = con.resSetToArray(con.resSet(query));
        return arr;
    }
    
    public static String[][] search(String cols, String table, String cond) throws SQLException {
        String query = "select " + cols + " from " + table + " " + cond;
        String[][] arr = con.resSetToArray(con.resSet(query));
        return arr;
    }
    
    public static Vector<String> get_col(String col, String table, String cond) throws SQLException {
        String query = "select " + col + " from " + table + " " + cond;
        Vector<String> res = con.resSetToVector(col, con.resSet(query));
        return res;
    }
    
}
