/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	String essentials = "Essential";
    	
    	this.txtResult.appendText(this.model.creaGrafo(essentials));

    	this.cmbGeni.getItems().clear();
    	this.cmbGeni.getItems().addAll(this.model.getVertici());
    	
    	this.btnGeniAdiacenti.setDisable(false);
        this.btnSimula.setDisable(false);
    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {

    	this.txtResult.clear();
    	Genes gene = this.cmbGeni.getValue();
    	
    	if(gene==null) {
    		this.txtResult.appendText("Selezionare un gene!");
    		return;
    	}
    	
    	this.txtResult.appendText("Geni adiacenti a"+ gene+"\n");
    	for(Adiacenza a : this.model.getAdiacenti(gene)) {
    		this.txtResult.appendText(a.getG2()+" - "+a.getPeso()+"\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	Genes gene = this.cmbGeni.getValue();
    	
    	if(gene==null) {
    		this.txtResult.appendText("Selezionare un gene!");
    		return;
    		
    	}
    	
    	try {
    		Integer numIng = Integer.parseInt(this.txtIng.getText());
  
    		if(numIng == null) {
    			this.txtResult.setText("Inserisci un numero di ingegneri!");
    			return;
    		}
    		
    		this.txtResult.appendText("Geni in corso di studio e relativo n. di ingegneri associati:\n");
        	Map<Genes, Integer> gen = this.model.simula(numIng, gene);
        	for (Genes ge: gen.keySet())
        		this.txtResult.appendText("Gene: "+ge+" ("+gen.get(ge)+" ingegneri)\n");
        
    	} catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserisci un formato valido!");
    		return;
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        this.btnGeniAdiacenti.setDisable(true);
        this.btnSimula.setDisable(true);
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
