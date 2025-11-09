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
			
			ps.close(); ps = null;
			rs.close(); rs = null;
			con.close(); con = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return coches;
		
	}
	
	
	
	public String crearDDL() {
		String res = "";

	    String sqlCoches = "CREATE TABLE COCHES ("
	            + "    matricula VARCHAR2(20),"
	            + "    marca     VARCHAR2(50),"
	            + "    modelo    VARCHAR2(50),"
	            + "    km        NUMBER(10),"
	            + "    CONSTRAINT pk_coches PRIMARY KEY (matricula)"
	            + ")";


	    String sqlCompras = "CREATE TABLE COMPRAS ("
	            + "    matricula VARCHAR2(20),"
	            + "    valor     NUMBER(10, 2) NOT NULL,"
	            + "    CONSTRAINT pk_compras PRIMARY KEY (matricula),"
	            + "    CONSTRAINT fk_compras_coches "
	            + "        FOREIGN KEY (matricula) "
	            + "        REFERENCES COCHES(matricula)"
	            + "        ON DELETE CASCADE"
	            + ")";
		
		Connection con = Conexion.conectar();
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlCoches);
			ps.executeUpdate();
			
			ps = con.prepareStatement(sqlCompras);
			ps.executeUpdate();
			
			res = "Se han ejecutado correctamente los DDL";
			
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (SQLException e) {

			if (e.getErrorCode() == 955) {
				res = "Error: Las tablas ya existen";
			}
			else {
				res = "Error: " + e.getMessage();
			}
		}
		
		return res;
	}

	public String updateCoche(Coche c) {
		String res = "";	
		Connection con = Conexion.conectar();
		
		try {
			String sql = "update coches set marca = ?, modelo = ?, km = ? where matricula = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, c.getMarca());
			ps.setString(2, c.getModelo());
			ps.setInt(3, c.getKm());
			ps.setString(4, c.getMatricula());
			
			ps.executeUpdate();
			
			res = "Se ha actualizado el coche";
			
			con.close(); con = null;
			ps.close(); ps = null;
			
		} catch (Exception e) {
			res = "Error: " + e.getMessage();
		}
		
		return res;
	}
	
	public String addCoche(Coche c) {
		String res = "";	
		Connection con = Conexion.conectar();
		
		try {
			String sql = "insert into coches values (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setInt(4, c.getKm());

			ps.executeUpdate();
			
			res = "Se ha insertado el coche";
			
			con.close(); con = null;
			ps.close(); ps = null;
			
		} catch (Exception e) {
			res = "Error: " + e.getMessage();
		}
		
		return res;
	}
	
}
