package bo.roman.tinnitus.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AttenuatorInitializer {
	private final static Logger log = LoggerFactory.getLogger(AttenuatorInitializer.class);

	private static final String FXML_PATH = "resources/fxml/AttenuatorLayout.fxml";

	private final Stage primaryStage;

	public AttenuatorInitializer(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void initialize() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(findFileUrl(FXML_PATH));
		try {
			HBox attenuatorUI = loader.load();
			Scene scene = new Scene(attenuatorUI);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
		} catch (IOException e) {
			throw new RuntimeException("The FXML file could not be loaded.", e);
		}
	}
	
	public void show() {
		primaryStage.show();
	}

	private URL findFileUrl(String fxmlPath) {
		Path p = Paths.get(fxmlPath);
		if (p != null) {
			try {
				return p.toUri().toURL();
			} catch (MalformedURLException e) {
				log.error("There was an error converting a URI to URL", e);
			}
		}

		return null;
	}

}
