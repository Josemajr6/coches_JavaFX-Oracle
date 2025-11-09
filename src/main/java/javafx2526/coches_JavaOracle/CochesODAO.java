package javafx2526.coches_JavaOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CochesODAO {

	public ObservableList<CocheO> seleccionar() {
		ObservableList<CocheO> coches = FXCollections.observableArrayList();
		Connection con = Conexion.conectar();
		
		String sql = "select matricula, marca, modelo, km from COCHESO";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				coches.add(new CocheO("T_COCHE",rs.getString(1), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4))));
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

		String SQL = "create or replace TYPE T_COCHE AS Object\r\n"
				+ "(matricula varchar2(25),\r\n"
				+ "marca varchar2(25),\r\n"
				+ "modelo varchar2(25),\r\n"
				+ "km number);\r\n"
				+ "";
		
		String SQL2 =  "CREATE TABLE COCHESO OF T_COCHE  (matricula PRIMARY KEY)";
		
		Connection con = Conexion.conectar();
		
		try {
			PreparedStatement ps = con.prepareStatement(SQL);
			ps.executeUpdate();
			
			ps = con.prepareStatement(SQL2);
			ps.executeUpdate();
			
			res = "Se han ejecutado correctamente los DDL";
			
			ps.close();
			ps = null;
			con.close();
			con = null;
		} catch (SQLException e) {

			if (e.getErrorCode() == 955) {
				res = "Error: Ya se han ejecutado los DDL";
			}
			else {
				res = "Error: " + e.getMessage();
			}
		}
		
		return res;
	}

	public String updateCoche(CocheO c) {
		String res = "";	
		Connection con = Conexion.conectar();
		
		try {
			String sql = "update cochesO set marca = ?, modelo = ?, km = ? where matricula = ?";
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
	
	public String addCoche(CocheO c) {
		String res = "";	
		Connection con = Conexion.conectar();
		
		try {
			String sql = "insert into cocheso values (?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, c);

			ps.executeUpdate();
			
			res = "Se ha insertado el coche";
			
			con.close(); con = null;
			ps.close(); ps = null;
			
		} catch (Exception e) {
			res = "Error: " + e.getMessage();
		}
		
		return res;
	}
	
	public String delCoche(CocheO c) {
		String res = "";	
		Connection con = Conexion.conectar();
		
		try {
			String sql = "delete from cocheso where matricula = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, c.getMatricula());

			ps.executeUpdate();
			
			res = "Se ha borrado el coche";
			
			con.close(); con = null;
			ps.close(); ps = null;
			
		} catch (Exception e) {
			res = "Error: " + e.getMessage();
		}
		
		return res;
	}
	
}
