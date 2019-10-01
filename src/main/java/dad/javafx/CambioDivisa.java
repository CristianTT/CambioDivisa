package dad.javafx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisa extends Application {
	

	private TextField divisaEntradaText, divisaSalidaText;
	private ComboBox<Divisa> divisaEntradaCombo, divisaSalidaCombo;
	private Button convertirButton;
	private Divisa[] divisas = {
			new Divisa("Euro", 1.0),
			new Divisa("Libra esterlina", 0.8873),
			new Divisa("Dolar estadounidense", 1.2007),
			new Divisa("Yen", 133.59)
	};
	

	@Override
	public void start(Stage primaryStage) throws Exception {

		divisaEntradaText = new TextField();
		divisaEntradaText.setPromptText("Entrada");
		divisaEntradaText.setMaxWidth(80);
		
		divisaSalidaText = new TextField();
		divisaSalidaText.setPromptText("Salida");
		divisaSalidaText.setMaxWidth(80);
		divisaSalidaText.setDisable(true);
		
		divisaEntradaCombo = new ComboBox();
		divisaEntradaCombo.getItems().addAll(divisas);
		divisaEntradaCombo.setValue(divisas[0]);
		
		divisaSalidaCombo = new ComboBox();
		divisaSalidaCombo.getItems().addAll(divisas);
		divisaSalidaCombo.setValue(divisas[1]);

		convertirButton = new Button("Convertir");
		convertirButton.setDefaultButton(true);
		convertirButton.setOnAction(e -> onConvertirButtonAction(e));

		HBox entradaBox = new HBox(10, divisaEntradaText, divisaEntradaCombo);
		entradaBox.setAlignment(Pos.CENTER);
		
		HBox salidaBox = new HBox(10, divisaSalidaText, divisaSalidaCombo);
		salidaBox.setAlignment(Pos.CENTER);

		VBox root = new VBox(15, entradaBox, salidaBox, convertirButton);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("CambioDivisa");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void onConvertirButtonAction(ActionEvent e) {
		String entrada = divisaEntradaText.getText();
		Pattern pattern = Pattern.compile("^[0-9]+([.][0-9]+)?$");
		Matcher matcher = pattern.matcher(entrada);
		if (matcher.matches()) {
			double number = Double.parseDouble(entrada);
			double salida = Divisa.fromTo(divisaEntradaCombo.getValue(), divisaSalidaCombo.getValue(), number);
			divisaSalidaText.setText("" + salida);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("El número introducido no es válido.");
			alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
