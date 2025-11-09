package javafx2526.coches_JavaOracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private static Connection con;
	
	private static String driver = "jdbc:oracle:thin:";
	private static String server = "@localhost:1521";
	private static String user = "PRUEBA";
	private static String password = "prueba";
	
	private static String clase = "oracle.jdbc.driver.OracleDriver";
	
	public static Connection conectar() {
		try {
			if (con == null || con.isClosed()) {
					try {
						Class.forName(clase);
						con = DriverManager.getConnection(driver+server, user, password);
						
						System.out.println("Se ha establecido la conexión con la base de datos...");
						
					} catch (ClassNotFoundException e) {

						e.printStackTrace();
					}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
}
