package bo.roman.tinnitus.ui.initializer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bo.tinnitus.utils.LoggerUtils;

import bo.roman.tinnitus.ui.App;
import bo.roman.tinnitus.ui.controller.SettingsController;
import bo.roman.tinnitus.ui.util.ResourcesLocatorUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SettingsInitializer {
	private final static Logger log = LoggerFactory.getLogger(SettingsInitializer.class);
	private static final String FXML_PATH = "resources/fxml/SettingsLayout.fxml";
	private static final String SETTINGS_TITLE = "Attenuator Settings";
	
	private Stage settingsStage;
	private SettingsController controller;
	private final Stage primaryStage;
	private final App app;
	
	
	public SettingsInitializer(Stage primaryStage, App app) {
		this.primaryStage = primaryStage;
		this.app = app;
	}
	
	public void initialize() {
		LoggerUtils.logDebug(log, () -> "Initializing SettingsInitializer");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ResourcesLocatorUtil.findFileUrl(FXML_PATH));
			
			HBox settingsUI = loader.load();
			Scene scene = new Scene(settingsUI);
			settingsStage = new Stage();
			settingsStage.setScene(scene);
			settingsStage.setTitle(SETTINGS_TITLE);
			settingsStage.setResizable(false);
			settingsStage.initOwner(primaryStage);
			controller = loader.getController();
			settingsStage.setOnCloseRequest(eh -> closeSettingsAction());
		} catch (IOException e) {
			throw new RuntimeException("The FXML file could not be loaded.", e);
		}
	}
	
	private void closeSettingsAction() {
		controller.saveInformation();
		if (controller.canFilterRun()) {
			app.enableFilterButton();
		}
	}

	public void showAndWait() {
		settingsStage.showAndWait();
	}

}
