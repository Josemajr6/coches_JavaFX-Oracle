package javafx2526.coches_JavaOracle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CochesController implements Initializable {

    @FXML
    private TableView<Coche> tvCoches;
	
    @FXML
    private TableColumn<Coche, String> tcMatricula;
    
    @FXML
    private TableColumn<Coche, String> tcMarca;
    
    @FXML
    private TableColumn<Coche, String> tcModelo;

    @FXML
    private TableColumn<Coche, Integer> tcKM;

    @FXML
    private TextField tfMatricula;
    
    @FXML
    private TextField tfMarca;
    
    @FXML
    private TextField tfModelo;
    
    @FXML
    private TextField tfKM;
    
    @FXML
    private TextField tfValor;
    
    @FXML
    private TextField tfDisponible;
    
    @FXML
    private Label label_Info;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CochesDAO cDAO = new CochesDAO();
		ObservableList<Coche> coches = cDAO.seleccionar();

		tcMatricula.setCellValueFactory(new PropertyValueFactory<Coche, String>("matricula"));
		tcMarca.setCellValueFactory(new PropertyValueFactory<Coche, String>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<Coche, String>("modelo"));
		tcKM.setCellValueFactory(new PropertyValueFactory<Coche, Integer>("km"));
		
		tvCoches.setItems(coches);
		
	}
	

    @FXML
    void oACreateDDL(ActionEvent event) {
		CochesDAO cDAO = new CochesDAO();
		String res = cDAO.crearDDL();
		label_Info.setText(res);
    }
    

    @FXML
    void oAUpdate(ActionEvent event) {

    	if (!tfMatricula.getText().isEmpty()) {
    		Coche c = new Coche (tfMatricula.getText(), tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfKM.getText()));
    		CochesDAO cDAO = new CochesDAO();
    		
    		String res = cDAO.updateCoche(c);
    		label_Info.setText(res);
    		this.initialize(null, null);
    	}
    	
    }
    
    @FXML
    void oAAdd(ActionEvent event) {

    	if (!tfMatricula.getText().isEmpty()) {
    		Coche c = new Coche (tfMatricula.getText(), tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfKM.getText()));
    		CochesDAO cDAO = new CochesDAO();
    		
    		String res = cDAO.addCoche(c);
    		label_Info.setText(res);
    		this.initialize(null, null);
    	}
    	
    }
    
    @FXML
    void oADel(ActionEvent event) {
    	if (!tfMatricula.getText().isEmpty()) {
    		Coche c = new Coche (tfMatricula.getText(), tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfKM.getText()));
    		CochesDAO cDAO = new CochesDAO();
    		
    		String res = cDAO.delCoche(c);
    		label_Info.setText(res);
    		this.initialize(null, null);
    	}
    }
    
    @FXML
    void oAToObject(ActionEvent event) {
    	try {
			App.setRoot("CochesO");
		} catch (IOException e) {
			label_Info.setText("Error: " + e.getMessage());
		}
    }
    
    @FXML
    void oAProc(ActionEvent event) {
    	
    	if (!tcMatricula.getText().isEmpty()) {
    		
    		Coche c = new Coche (tfMatricula.getText(), tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfKM.getText()));
		 
			CochesDAO cDAO = new CochesDAO();
			cDAO.llamarProc(c);
			this.initialize(null,null);
		}
    	
    }
    
    @FXML
    void oAFunc(ActionEvent event) {
    	
    	if (!tcKM.getText().isEmpty()) {
			CochesDAO cDAO = new CochesDAO();
			label_Info.setText(cDAO.llamarFunc(Integer.parseInt(tfKM.getText())));
			this.initialize(null, null);
		}
    	else {
    		label_Info.setText("Error: Introduce KM");
    	}
    	
    }

    @FXML
    void omcPulsarFila() {

    	Coche c = tvCoches.getSelectionModel().getSelectedItem();
    	
    	if (c != null) {
    		tfMatricula.setText(c.getMatricula());
    		tfMarca.setText(c.getMarca());
    		tfModelo.setText(c.getModelo());
    		tfKM.setText(String.valueOf(c.getKm()));
    	}
    	
    }
    
    @FXML
    void oACompra(ActionEvent event) {
    	
    	
    	if (!tcMatricula.getText().isEmpty() && !tfValor.getText().isEmpty() && !tfDisponible.getText().isEmpty() && !tfKM.getText().isEmpty())
		{
		 
		 CochesDAO cDAO = new CochesDAO();
    	
		 label_Info.setText(cDAO.transaccion( new Coche(tfMatricula.getText(),tfMarca.getText(),tfModelo.getText(), 
				 Integer.parseInt(tfKM.getText())), Integer.valueOf(tfValor.getText()),Integer.valueOf(tfDisponible.getText())));
		 
		 this.initialize(null,null);
		}
    } 

}
