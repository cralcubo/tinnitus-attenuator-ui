package bo.roman.tinnitus.ui.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bo.tinnitus.SystemSoundParameters;
import com.bo.tinnitus.TinnitusAtenuator;
import com.bo.tinnitus.TinnitusFrequencies;

import bo.roman.tinnitus.ui.business.AttenuatorManager;
import bo.roman.tinnitus.ui.exception.IllegalFrequencyException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SettingsController {
	private final static Logger log = LoggerFactory.getLogger(SettingsController.class);
	private static final String NOT_SET = "Unknown";
	private AttenuatorManager attenuatorManager;
	
	@FXML
	TextField frequencyField;
	@FXML
	ComboBox<String> audioSource;
	@FXML
	ComboBox<String> audioTarget;

	@FXML
	private void initialize() {
		attenuatorManager = AttenuatorManager.getInstance();
		
		// Set the information preset
		frequencyField.setText(getPresetFreq());
		audioSource.setValue(getPresetAudioSource());
		audioTarget.setValue(getPresetAudioTarget());
		
		// Load the info in the combo boxes
		List<String> audioDevices = getAllAudioDevices();
		audioSource.setItems(FXCollections.observableArrayList(audioDevices));
		audioTarget.setItems(FXCollections.observableArrayList(audioDevices));
	}
	
	public void saveInformation() {
		saveFrequency();
		saveAudioProperties();
	}

	private void saveAudioProperties() {
		String source = audioSource.getValue();
		String target = audioTarget.getValue();
		attenuatorManager.setSystemSoundParameters(new SystemSoundParameters(source, target));
	}

	private void saveFrequency() {
		try {
			int lf = validateFrequency(frequencyField.getText());
			int rf = lf;
			attenuatorManager.setTinnitusFrequencies(new TinnitusFrequencies(Integer.valueOf(lf), Integer.valueOf(rf)));
		} catch (IllegalFrequencyException e) {
			log.error("There was an error setting the tinnitus frequencies", e);
		}
	}

	private String getPresetAudioTarget() {
		return Optional.ofNullable(attenuatorManager)
						.flatMap(am -> Optional.ofNullable(am.getAttenuator()))
						.map(TinnitusAtenuator::getSystemSoundParameters)
						.map(SystemSoundParameters::getTargetSoundDevice)
						.orElseGet(() -> NOT_SET);
	}

	private String getPresetAudioSource() {
		return Optional.ofNullable(attenuatorManager)
						.flatMap(am -> Optional.ofNullable(am.getAttenuator()))
						.map(TinnitusAtenuator::getSystemSoundParameters)
						.map(SystemSoundParameters::getSourceDeviceName)
						.orElseGet(() -> NOT_SET);
	}

	private String getPresetFreq() {
		return Optional.ofNullable(attenuatorManager)
						.flatMap(am -> Optional.ofNullable(am.getAttenuator()))
						.map(TinnitusAtenuator::getTinnitusFrequencies)
						.map(TinnitusFrequencies::getLeftFrequency)
						.map(n -> String.valueOf(n.intValue()))
						.orElseGet(() -> NOT_SET);
	}
	
	private int validateFrequency(String text) throws IllegalFrequencyException {
		try {
			int freq = Integer.parseInt(text);
			if (freq > 0) {
				return freq;
			}
			throw new IllegalFrequencyException();
		} catch (NumberFormatException e) {
			throw new IllegalFrequencyException(e);
		}
	}
	
	private List<String> getAllAudioDevices() {
		return Arrays.stream(AudioSystem.getMixerInfo())
					  .map(Mixer.Info::getName)
					  .filter(n -> !n.startsWith("Port"))
					  .collect(Collectors.toList());
	}

	public boolean canFilterRun() {
		return attenuatorManager.getAttenuator() != null;
	}

}
