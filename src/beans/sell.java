package beans;

import DB_Conn.db_conn;
import java.sql.SQLException;

public class sell {
    
    static db_conn con = new db_conn();

    public sell() throws ClassNotFoundException, SQLException {
        con.conn();
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
        x.select();
    }
}
