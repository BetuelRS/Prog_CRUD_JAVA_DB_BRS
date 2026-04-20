

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LigaDB {
    private static final String URL = "jdbc:mysql://localhost:3306/java_db_betuelsouza?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    //private static final String URL = "jdbc:mysql://localhost:3306/java_db_betuelsouza?useSSL=false&serverTimezone=UTC";
    //private static final String URL = "jdbc:mysql://localhost:3306/java_db_betuelsouza";
    private static final String USER = "BetuelSouza";
    private static final String PASS = "Betuel.300108";
    
    public Connection getConnect() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASS);
    }
   
    
}
