package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ParametreCtrl extends FenetreApp implements Initializable{

	@FXML private VBox parametreBox;
    @FXML private Label lblTitle;
    @FXML private RadioButton chkIP;
    @FXML private TextField fieldIP;
    @FXML private RadioButton chkPort;
    @FXML private TextField fieldPort;
    @FXML private Button btnIPAppliquer;
    @FXML private Button btnPortAppliquer;
    
    private ObservateurEcrans obs;
    
	private void initBoutons() {
		btnIPAppliquer.setOnAction((action)-> { 
			obs.setIP(fieldIP.getText()); 
			chkIP.setSelected(false);
		});
		btnPortAppliquer.setOnAction((action)-> { 
			obs.setPort(Integer.valueOf(fieldPort.getText()));
			chkPort.setSelected(false);
		});
	}
	
	private void chkEvent() {
		chkIP.selectedProperty().addListener((o, oldValue, newValue)->{ if(!newValue) obs.currentIP(); });
		chkPort.selectedProperty().addListener((o, old, newVal)-> { if(!newVal) obs.currentPort(); });
	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		fieldIP.disableProperty().bind(chkIP.selectedProperty().isEqualTo(new SimpleBooleanProperty(false)));
		fieldPort.disableProperty().bind(chkPort.selectedProperty().isEqualTo(new SimpleBooleanProperty(false)));
		btnIPAppliquer.disableProperty().bind(fieldIP.disableProperty());
		btnPortAppliquer.disableProperty().bind(fieldPort.disableProperty());
		initBoutons();
		chkEvent();
	}

	public void setFieldIp(String ip) { this.fieldIP.setText(ip); }
	public void setFieldPort(int port) { this.fieldPort.setText(String.valueOf(port)); }
	public void addObserver(ObservateurEcrans obs) { this.obs = obs; }
	
	@Override protected String getFxml() { return "Parametre.fxml"; }
	@Override protected String getTitle() { return "Parametre"; }
	@Override protected Parent getRoot() { return parametreBox; }
	public static void main(String[] args) { launch(args); }
	
	
}
