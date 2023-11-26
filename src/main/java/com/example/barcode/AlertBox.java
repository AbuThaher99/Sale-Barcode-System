package com.example.barcode;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox {
	public static int flage = 0;

	public static void display(String tital,String user,String password) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(tital);
		window.setMinWidth(300);
		window.initStyle(StageStyle.UNDECORATED);

		Pane Pane = new Pane();

		Label label = new Label("الاسم :");
		label.setLayoutX(342);
		label.setLayoutY(42);

		Label label1 = new Label("كلمة السر :");
		label1.setLayoutX(342);
		label1.setLayoutY(110);

		TextField text = new TextField();
		text.setLayoutX(130);
		text.setLayoutY(38);

		PasswordField pass = new PasswordField();
		pass.setLayoutX(130);
		pass.setLayoutY(106);

		Button but = new Button("تسجيل");
		but.setLayoutX(246);
		but.setLayoutY(166);
		but.setPrefSize(66, 34);
		but.setOnAction(e -> {

			if (text.getText().equals(user) && pass.getText().equals(password)) {
				window.close();

			}else{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("خطأ");
				alert.setHeaderText("خطأ في اسم المستخدم او كلمة السر");
				alert.setContentText("الرجاء اعادة المحاولة");
				alert.showAndWait();
				return;

			}

		});

		Button but1 = new Button("الغاء");
		but1.setLayoutX(97);
		but1.setLayoutY(166);
		but1.setPrefSize(66, 34);

		but1.setOnAction(e -> {
			flage = 1;
			window.close();

		});



		pass.setPromptText("passwords");
		text.setPromptText("User Name");

		Pane.getChildren().addAll(label, label1, text, pass, but, but1);
		Scene se = new Scene(Pane , 408, 227);
		se.getStylesheets().add(AlertBox.class.getResource("application.css").toExternalForm());
		window.setScene(se);
		window.showAndWait();

	}

}
