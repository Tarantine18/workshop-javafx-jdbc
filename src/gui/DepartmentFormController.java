package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Ultils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentServices;

public class DepartmentFormController implements Initializable {

	private Department entity;
	
	private DepartmentServices service;
	
	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErroName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department entity) {
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
			Ultils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.showAlert("erro saving obj", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private Department getFormData() {
	
		Department obj = new Department();
		obj.setId(Ultils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		return obj;
		
		
	}

	public void onBtCancelAction(ActionEvent event) {
		Ultils.currentStage(event).close();
	}
	
	public void setDepartmentServices(DepartmentServices service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormDate() {
		if(entity == null) {
			throw new IllegalStateException("entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
