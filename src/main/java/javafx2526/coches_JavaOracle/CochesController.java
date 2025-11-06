package javafx2526.coches_JavaOracle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    }

}
