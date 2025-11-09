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

public class CochesOController implements Initializable {

    @FXML
    private TableView<CocheO> tvCoches;
	
    @FXML
    private TableColumn<CocheO, String> tcMatricula;
    
    @FXML
    private TableColumn<CocheO, String> tcMarca;
    
    @FXML
    private TableColumn<CocheO, String> tcModelo;

    @FXML
    private TableColumn<CocheO, Integer> tcKM;

    @FXML
    private TextField tfMatricula;
    
    @FXML
    private TextField tfMarca;
    
    @FXML
    private TextField tfModelo;
    
    @FXML
    private TextField tfKM;
    
    @FXML
    private Label label_Info;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CochesODAO cDAO = new CochesODAO();
		ObservableList<CocheO> coches = cDAO.seleccionar();

		tcMatricula.setCellValueFactory(new PropertyValueFactory<CocheO, String>("matricula"));
		tcMarca.setCellValueFactory(new PropertyValueFactory<CocheO, String>("marca"));
		tcModelo.setCellValueFactory(new PropertyValueFactory<CocheO, String>("modelo"));
		tcKM.setCellValueFactory(new PropertyValueFactory<CocheO, Integer>("km"));
		
		tvCoches.setItems(coches);
		
	}
	

    @FXML
    void oACreateDDL(ActionEvent event) {
		CochesODAO cDAO = new CochesODAO();
		String res = cDAO.crearDDL();
		label_Info.setText(res);
    }
    

    @FXML
    void oAUpdate(ActionEvent event) {

    	if (!tfMatricula.getText().isEmpty()) {
    		CocheO c = new CocheO ("T_COCHE",tfMatricula.getText(), tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfKM.getText()));
    		CochesODAO cDAO = new CochesODAO();
    		
    		String res = cDAO.updateCoche(c);
    		label_Info.setText(res);
    		this.initialize(null, null);
    	}
    	
    }
    
    @FXML
    void oAAdd(ActionEvent event) {

    	if (!tfMatricula.getText().isEmpty()) {
    		CocheO c = new CocheO ("T_COCHE",tfMatricula.getText(), tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfKM.getText()));
    		CochesODAO cDAO = new CochesODAO();
    		
    		String res = cDAO.addCoche(c);
    		label_Info.setText(res);
    		this.initialize(null, null);
    	}
    	
    }
    
    @FXML
    void oADel(ActionEvent event) {
    	if (!tfMatricula.getText().isEmpty()) {
    		CocheO c = new CocheO ("T_COCHE",tfMatricula.getText(), tfMarca.getText(), tfModelo.getText(), Integer.parseInt(tfKM.getText()));
    		CochesODAO cDAO = new CochesODAO();
    		
    		String res = cDAO.delCoche(c);
    		label_Info.setText(res);
    		this.initialize(null, null);
    	}
    }
    
    @FXML
    void oABack(ActionEvent event) {
    	try {
			App.setRoot("Coches");
		} catch (IOException e) {
			label_Info.setText("Error: " + e.getMessage());
		}
    }

    @FXML
    void omcPulsarFila() {

    	CocheO c = tvCoches.getSelectionModel().getSelectedItem();
    	
    	if (c != null) {
    		tfMatricula.setText(c.getMatricula());
    		tfMarca.setText(c.getMarca());
    		tfModelo.setText(c.getModelo());
    		tfKM.setText(String.valueOf(c.getKm()));
    	}
    	
    }

}
