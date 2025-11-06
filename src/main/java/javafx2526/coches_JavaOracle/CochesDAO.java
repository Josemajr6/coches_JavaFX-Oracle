package javafx2526.coches_JavaOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CochesDAO {

	public ObservableList<Coche> seleccionar() {
		ObservableList<Coche> coches = FXCollections.observableArrayList();
		Connection con = Conexion.conectar();
		
		String sql = "select matricula, marca, modelo, km from COCHES";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				coches.add(new Coche(rs.getString(1), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4))));
			}
			
			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return coches;
		
	}
	
	
	
	
}
