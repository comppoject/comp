package beans;

import DB_Conn.db_conn;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class sell {
    private static DateFormat df;
    private static Date curr_time;
    private static db_conn con = new db_conn();

    public sell() throws ClassNotFoundException, SQLException {
        con.conn();
        df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }
    
    
    public static void insert(String total) throws SQLException {
        curr_time = Calendar.getInstance().getTime();
        int done = con.exUpdate("insert into sell (date, total) values ('" + df.format(curr_time) + "', '" +total+ "')");
        System.out.println(done + " rows affected.");
    }
    
    public static void select() throws SQLException {
        String query = "select * from sell ";
        String[][] arr = con.resSetToArray(con.resSet(query));
        
        
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        sell x = new sell();
        //x.insert("120");
        x.select();
    }
}
