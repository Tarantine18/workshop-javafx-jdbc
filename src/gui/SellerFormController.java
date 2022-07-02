package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Ultils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exeptions.ValidationsExeptions;
import model.services.SellerServices;

public class SellerFormController implements Initializable {

	private Seller entity;
	
	private SellerServices service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	
	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	@FXML
	private Label labelErroName;
	
	@FXML
	private Label labelErroEmail;
	
	@FXML
	private Label labelErroBirthDate;
	
	@FXML
	private Label labelErroBaseSalary;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notitfyDataChangedListeterns();
			Ultils.currentStage(event).close();
		}
		catch(ValidationsExeptions e) {
			setErroorMessages(e.getErros());
		}
		catch(DbException e) {
			Alerts.showAlert("erro saving obj", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void notitfyDataChangedListeterns() {
		for(DataChangeListener listeners : dataChangeListeners) {
			listeners.onDataChanged();
		}
		
	}

	private Seller getFormData() {
	
		Seller obj = new Seller();
		
		ValidationsExeptions exeption = new ValidationsExeptions("validations erro");
		obj.setId(Ultils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exeption.addError("name", "field cannot be empty");
		}
		obj.setName(txtName.getText());
		
		if(exeption.getErros().size() > 0) {
			throw exeption;
		}
		
		return obj;
		
		
	}

	public void onBtCancelAction(ActionEvent event) {
		Ultils.currentStage(event).close();
	}
	
	public void setSellerServices(SellerServices service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListeners(DataChangeListener listeners) {
		dataChangeListeners.add(listeners);	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Ultils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		
		
		
	}
	
	public void updateFormDate() {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if(entity.getBirthDate() != null) {
		dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(),ZoneId.systemDefault()));
		}
	}
	
	public void setErroorMessages(Map<String,String> erros) {
		Set<String> fields = erros.keySet();
		
		if(fields.contains("name")) {
			labelErroName.setText(erros.get("name"));
		}
		
	}

}
