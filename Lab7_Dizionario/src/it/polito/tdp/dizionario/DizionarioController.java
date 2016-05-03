package it.polito.tdp.dizionario;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtLen;

	@FXML
	private TextField txtParola;

	@FXML
	private Button btnGeneraGrafo;

	@FXML
	private Button btnTrovaVicini;

	@FXML
	private Button btnTrovaTutti;

	@FXML
	private TextArea txtResult;

	@FXML
	void doGeneraGrafo(ActionEvent event) {

		String lunS = txtLen.getText();

		Integer lun;
		try {
			lun = Integer.parseInt(lunS);
		} catch (NumberFormatException e) {
			txtResult.appendText("Formato errato: " + lunS + "\n");
			return;
		}

		model.caricaParole(lun);

		txtResult.appendText("Parole caricate: " + model.getDict().size() + "\n");

		model.buildGraph();

		btnTrovaVicini.setDisable(false);
		btnTrovaTutti.setDisable(false);

	}

	@FXML
	void doTrovaTutti(ActionEvent event) {

	}

	@FXML
	void doTrovaVicini(ActionEvent event) {

	}

	@FXML
	void initialize() {
		assert txtLen != null : "fx:id=\"txtLen\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaTutti != null : "fx:id=\"btnTrovaTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
