package bo.roman.tinnitus.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bo.tinnitus.TinnitusAtenuator;
import com.bo.tinnitus.utils.LoggerUtils;

import bo.roman.tinnitus.ui.App;
import bo.roman.tinnitus.ui.business.AttenuatorManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class AttenuatorController {
	private final static Logger log = LoggerFactory.getLogger(AttenuatorController.class);

	private enum AttenuatorState {
		FILTERING, STOP, BYPASS
	}

	@FXML
	private Button settings;

	@FXML
	private ToggleButton run;

	private TinnitusAtenuator attenuator;
	private AttenuatorState state;

	private App app;
	
	@FXML
	private void initialize() {
		state = AttenuatorState.STOP;
		AttenuatorManager am = AttenuatorManager.getInstance();
		attenuator = am.getAttenuator();
		if (attenuator == null) {
			// Disable attenuator run button
			run.setDisable(true);
		}
	}

	@FXML
	public void settingsAction() {
		// Launch settings
		if(app != null) {
			app.showSettings();
		}
	}

	@FXML
	public void runAction() {
		if (run.isSelected()) {
			filter();
		} else {
			bypass();
		}
	}

	private void filter() {
		LoggerUtils.logDebug(log, () -> "Filtering sounds");
		if(state == AttenuatorState.BYPASS) {
			attenuator.stop();
		}
		attenuator.runFilter();
		state = AttenuatorState.FILTERING;
	}
	
	private void bypass() {
		LoggerUtils.logDebug(log, () -> "Bypassing sounds");
		attenuator.stop();
		attenuator.bypassAudio();
		state = AttenuatorState.BYPASS;
	}

	public void close() {
		log.debug("Clossing tinnitus attenuator");
		System.exit(0);
	}

	public void setApp(App app)
	{
		this.app = app;
	}

	public void enableRunButton() {
		run.setDisable(false);
	}
}
