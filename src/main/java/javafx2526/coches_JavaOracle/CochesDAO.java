package javafx2526.coches_JavaOracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Types;

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
			if (!(e.getErrorCode()==942))
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
	            + "    id        NUMBER(10) PRIMARY KEY,"
	            + "    matricula VARCHAR2(20),"
	            + "    valor     NUMBER(10, 2) NOT NULL,"
	            + "    CONSTRAINT fk_compras_coches "
	            + "        FOREIGN KEY (matricula) "
	            + "        REFERENCES COCHES(matricula) "
	            + "        ON DELETE CASCADE"
	            + ")";

		
	    String sqlSecuencia = "CREATE SEQUENCE SEC_COM START WITH 1 INCREMENT BY 1";


	    
		Connection con = Conexion.conectar();
		
		try {
			PreparedStatement ps = con.prepareStatement(sqlCoches);
			ps.executeUpdate();
			ps.close();
			
			
			ps = con.prepareStatement(sqlCompras);
			ps.executeUpdate();
			ps.close();
			
			ps = con.prepareStatement(sqlSecuencia);
			ps.executeUpdate();
			ps.close();
			
			res = "Se han ejecutado correctamente los DDL";
			

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
			
			ps.close(); ps = null;
			
		} catch (Exception e) {
			res = "Error: " + e.getMessage();
		}
		
		return res;
	}
	
	public String delCoche(Coche c) {
		String res = "";	
		Connection con = Conexion.conectar();
		
		try {
			String sql = "delete from coches where matricula = ?";
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

	public String llamarProc(Coche c){
		
		String res = "";
		try {
		
		Connection con = Conexion.conectar();
		con.setAutoCommit(false);
		String sql = "call ADDCOCHE(?,?, ?, ?)";
		
		CallableStatement proc = con.prepareCall(sql);
		
		proc.setString(1, c.getMatricula());
		proc.setString(2, c.getMarca());
		proc.setString(3, c.getModelo());
		proc.setInt(4, c.getKm());
		
		proc.execute();
		con.commit();
		proc.close();
		con.close();
		con = null;
		res = "Se ha ejecutado el procedimiento";
		} catch (SQLException e) {
			res = "Error: " + e.getMessage();
		}
		return res;
		
	}
	
	public String llamarFunc(Integer km) {
		int contador = 0;
		String res = "";
		try {
			
			Connection con = Conexion.conectar();
			String sql = "{? = call COCHESMASKM(?)}";
			
			CallableStatement cs = con.prepareCall(sql);
			
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.setInt(2, km);
			cs.execute();
			
			String contadorS = cs.getString(1);
			
			if (contadorS != null) {
				contador = Integer.parseInt(contadorS);
			}
			
			cs.close();
			con.close();
			con = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		res = "Coches con más de " + km + "KM: " + contador;
		return res;
	}
	
	
	public String transaccion (Coche c, Integer valor, Integer disponible) {
		String resultado= "";
		
		Connection con = Conexion.conectar();
		PreparedStatement psCoche = null;
        PreparedStatement psCompra = null;
		try {

			con.setAutoCommit(false);
			addCoche(c);
			
			Savepoint gCompra = con.setSavepoint();
			
			String sql = "insert into compras values (SEC_COM.NEXTVAL, ?, ?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,c.getMatricula());
			ps.setInt(2, valor);
			ps.execute();
			
			if (valor > disponible) {
				con.rollback(gCompra);
				resultado = "Coche guardado, pero la compra no (sin saldo)";
			}
			else {
				resultado = "Coche y compra guardados.";
			}
			
			
			con.commit();
			
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		
			try {
				con.close(); con = null; 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} con = null;
			

		return resultado;
	}
	
	public String transaccionEdad (Coche c, Integer edad, Integer valor, Integer disponible) {
		String resultado= "";
		
		Connection con = Conexion.conectar();
		PreparedStatement psCoche = null;
        PreparedStatement psCompra = null;
		try {

			con.setAutoCommit(false);
			addCoche(c);
			
			Savepoint gCompra = con.setSavepoint();
			
			String sql = "insert into compras values (SEC_COM.NEXTVAL, ?, ?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,c.getMatricula());
			ps.setInt(2, valor);
			ps.execute();
			
			if (valor > disponible) {
				con.rollback(gCompra);
				resultado = "Coche guardado, pero la compra no (no tienes saldo)";
			}
			else if (edad < 18) {
				con.rollback(gCompra);
				resultado = "Coche guardado, pero la compra no (no tienes edad)";

			}
			else {
				resultado = "Coche y compra guardados.";
			}
			
			
			con.commit();
			
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		
			try {
				con.close(); con = null; 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} con = null;
			

		return resultado;
	}
	
}	

// PROCEDIMIENTO Y FUNCIÓN 
	
//	
//	CREATE OR REPLACE PROCEDURE ADDCOCHE 
//	(
//	  MATRICULA IN VARCHAR2 
//	, MARCA IN VARCHAR2 
//	, MODELO IN VARCHAR2 
//	, KM IN NUMBER 
//	) AS 
//	BEGIN
//	  insert into coches values (MATRICULA, MARCA, MODELO, KM);
//	END ADDCOCHE;
//	
	
//	
//	CREATE OR REPLACE FUNCTION COCHESMASKM 
//	(
//	  KM IN NUMBER 
//	) RETURN VARCHAR2 AS 
//	  v_count NUMBER; 
//	BEGIN
//	  
//	  SELECT COUNT(*)
//	  INTO v_count
//	  FROM COCHES
//	  WHERE km > KM;
//	  
//	  RETURN TO_CHAR(v_count);
//	  
//	END COCHESMASKM;

