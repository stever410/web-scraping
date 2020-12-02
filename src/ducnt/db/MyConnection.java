package ducnt.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection implements Serializable {
    public  static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/THPTQG2020";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, "root", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
