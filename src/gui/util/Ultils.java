package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Ultils {

	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node)event.getSource()).getScene().getWindow();
	}
	
	public static Integer tryParseToInt(String stg) {
		try {
			return Integer.parseInt(stg);
		}
		catch(NumberFormatException e) {
			return null;
		}
	}
	
}
