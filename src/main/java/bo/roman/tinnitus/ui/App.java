package bo.roman.tinnitus.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AttenuatorInitializer li = new AttenuatorInitializer(primaryStage);
		li.initialize();
		li.show();
	}
	
}
