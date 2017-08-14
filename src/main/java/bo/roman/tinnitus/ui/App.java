package bo.roman.tinnitus.ui;

import bo.roman.tinnitus.ui.initializer.AttenuatorInitializer;
import bo.roman.tinnitus.ui.initializer.SettingsInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	private AttenuatorInitializer li;
	private SettingsInitializer si;
	 
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		li = new AttenuatorInitializer(primaryStage, this);
		li.initialize();
		si = new SettingsInitializer(primaryStage, this);
		si.initialize();
		
		li.show();
	}

	public void showSettings() {
		si.showAndWait();
	}

	public void enableFilterButton() {
		li.enableRunButton();
	}
	
}
