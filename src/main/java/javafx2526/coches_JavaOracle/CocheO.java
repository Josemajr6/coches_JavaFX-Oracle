package javafx2526.coches_JavaOracle;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class CocheO implements SQLData {

	private String typeName;
	private String matricula;
	private String marca;
	private String modelo;
	private Integer km;
	
	
	public CocheO(String typeName, String matricula, String marca, String modelo, Integer km) {
		super();
		this.typeName = typeName;
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.km = km;
	}


	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getKm() {
		return km;
	}

	public void setKm(Integer km) {
		this.km = km;
	}
	
	@Override
	public String getSQLTypeName() throws SQLException {
		// TODO Auto-generated method stub
		return this.typeName;
	}

	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		// TODO Auto-generated method stub
		
		this.typeName = typeName;
		this.matricula = stream.readString();
		this.marca = stream.readString();
		this.modelo = stream.readString();
		this.km = stream.readInt();
		
		
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		// TODO Auto-generated method stub
		
		stream.writeString(this.matricula);
		stream.writeString(this.marca);
		stream.writeString(this.modelo);
		stream.writeInt(this.km);

	}

	
	
}
