package DB_Conn;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class db_conn {
    
    //4ofo keda lw 7aga na2sa aw 3ayen te3adelo 3lehom .. ana bs bagareb
    
    private static Connection con;
    private static String driver = "jdbc:mysql://localhost:3306/";
    private static String db_name = "comp";
    private static String db_username = "root";
    private static String db_password = "pass";

    public db_conn() {
        try {
            conn();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(db_conn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(db_conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void conn() throws ClassNotFoundException, SQLException {
        con = DriverManager.getConnection(driver + db_name, db_username, db_password);
    }

    public ResultSet resSet(String mysql_query) throws SQLException {
        PreparedStatement statement = null;
        ResultSet out = null;
        if (mysql_query != null) {
            statement = con.prepareStatement(mysql_query);
            out = statement.executeQuery();
        }
        return out;
    }
    
    public int exUpdate(String mysql_query) throws SQLException {
        PreparedStatement statement = null;
        int out = 0;
        if (mysql_query != null) {
            statement = con.prepareStatement(mysql_query);
            out = statement.executeUpdate();
        }
        return out;
    }
    
    public static int numCols(ResultSet res) throws SQLException{
        ResultSetMetaData resMD = res.getMetaData();
        return resMD.getColumnCount();
    }
    
    public static int numRows(ResultSet res) throws SQLException{
        res.last();
        int rows_num = res.getRow();
        res.beforeFirst();
        return rows_num;
    }
    
    public static String[][] resSetToArray(ResultSet res) throws SQLException {
        int rows_num = numRows(res);
        int cols_num = numCols(res);
        String[][] row = new String[rows_num][cols_num];
        int t = 0;
        while (res.next()) {
            for (int i = 1; i <= cols_num; i++) {
                row[t][i - 1] = res.getString(i);
            }
            t++;
        }
        return row;
    }
    
    public static Vector<String> resSetToVector(String col, ResultSet res) throws SQLException {
        Vector<String> out = new Vector<String>();
        int t = 0;
        while (res.next()) {
                out.add(res.getString(col));
        }
        return out;
    }
    
        
    
}
