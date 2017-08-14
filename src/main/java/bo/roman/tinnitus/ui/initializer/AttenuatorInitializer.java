package bo.roman.tinnitus.ui.initializer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bo.tinnitus.utils.LoggerUtils;

import bo.roman.tinnitus.ui.App;
import bo.roman.tinnitus.ui.controller.AttenuatorController;
import bo.roman.tinnitus.ui.util.ResourcesLocatorUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AttenuatorInitializer {
	private final static Logger log = LoggerFactory.getLogger(AttenuatorInitializer.class);
	private static final String FXML_PATH = "resources/fxml/AttenuatorLayout.fxml";
	private static final String PROGRAM_TITLE = "Tinnitus Attenuator";

	private final Stage primaryStage;
	private final App app;
	
	private AttenuatorController controller;

	public AttenuatorInitializer(Stage primaryStage, App app) {
		this.primaryStage = primaryStage;
		this.app = app;
	}

	public void initialize() {
		LoggerUtils.logDebug(log, () -> "Initializing AttenuatorInitializer");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ResourcesLocatorUtil.findFileUrl(FXML_PATH));
			
			HBox attenuatorUI = loader.load();
			Scene scene = new Scene(attenuatorUI);
			scene.setFill(Color.BLACK);
			controller = loader.getController();
			controller.setApp(app);
			primaryStage.setScene(scene);
			primaryStage.setTitle(PROGRAM_TITLE);
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(eh -> controller.close());
		} catch (IOException e) {
			throw new RuntimeException("The FXML file could not be loaded.", e);
		}
	}
	
	public void show() {
		primaryStage.show();
	}

	public void showSettings() {
		app.showSettings();
	}

	public void enableRunButton() {
		controller.enableRunButton();
	}

}
