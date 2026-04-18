module R202_Binet_Maxence {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires java.desktop;
	
	exports controller;
	opens application to javafx.graphics, javafx.fxml;
}
