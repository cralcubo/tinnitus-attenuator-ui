package bo.roman.tinnitus.ui.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bo.tinnitus.SystemSoundParameters;
import com.bo.tinnitus.TinnitusFrequencies;

public class AttenuatorPropertiesUtil {
	private final static Logger log = LoggerFactory.getLogger(AttenuatorPropertiesUtil.class);

	private static final String ATTENUATOR_SETTINGS = "resources/attenuator.properties";

	private static final String LEFTFREQUENCY_KEY = "tinnitus.left";
	private static final String RIGHTFREQUENCY_KEY = "tinnitus.right";
	private static final String SOURCEDEVICE_KEY = "audio.bridge";
	private static final String TARGETDEVICE_KEY = "audio.output";
	
	@Deprecated
	private static SystemSoundParameters soundParameters = new SystemSoundParameters();
	@Deprecated
	private static TinnitusFrequencies tinnitusFrequencies = new TinnitusFrequencies();

	private static Properties properties;
	static {
		// Read tinnitus frequencies and System sound devices from settings.
		properties = new Properties();
		try (InputStream is = ResourcesLocatorUtil.findFileUrl(ATTENUATOR_SETTINGS).openStream()) {
			properties.load(is);
		} catch (IOException e) {
			throw new RuntimeException("There was an error reading attenuator properties.", e);
		}
	}
	@Deprecated
	public static Optional<TinnitusFrequencies> readFrequencies() {
		int lFreq = Integer.parseInt(properties.getProperty(LEFTFREQUENCY_KEY));
		int rFreq = Integer.parseInt(properties.getProperty(RIGHTFREQUENCY_KEY));
		if (lFreq > 0 && rFreq > 0) {
			tinnitusFrequencies.setLeftFrequency(lFreq);
			tinnitusFrequencies.setRightFrequency(rFreq);
			return Optional.of(tinnitusFrequencies);
		}
		return Optional.empty();
	}
	@Deprecated
	public static Optional<SystemSoundParameters> readSystemSoundParameters() {
		String source = properties.getProperty(SOURCEDEVICE_KEY);
		String target = properties.getProperty(TARGETDEVICE_KEY);
		if (source != null && target != null) {
			soundParameters.setSource(source);
			soundParameters.setTarget(target);
			return Optional.of(soundParameters);
		}
		return Optional.empty();
	}
	
	public static String getAudioSource() {
		return properties.getProperty(SOURCEDEVICE_KEY);
	}

	public static void setAudioSource(String source) {
		soundParameters.setSource(source);
		properties.setProperty(SOURCEDEVICE_KEY, source);
	}
	
	public static String getAudioTarget() {
		return properties.getProperty(TARGETDEVICE_KEY);
	}

	public static void setAudioTarget(String target) {
		soundParameters.setTarget(target);
		properties.setProperty(TARGETDEVICE_KEY, target);
	}
	
	public static Number getLeftFrequency() {
		return Integer.parseInt(properties.getProperty(LEFTFREQUENCY_KEY));
	}
	
	public static void setLeftFrequency(Number lf) {
		properties.setProperty(LEFTFREQUENCY_KEY, String.valueOf(lf.intValue()));
	}
	
	@Deprecated
	public static void setLeftFrequency(int lf) {
		tinnitusFrequencies.setLeftFrequency(lf);
		properties.setProperty(LEFTFREQUENCY_KEY, String.valueOf(lf));
	}
	
	public static Number getRightFrequency() {
		return Integer.parseInt(properties.getProperty(RIGHTFREQUENCY_KEY));
	}
	
	public static void setRightFrequency(Number rf) {
		properties.setProperty(RIGHTFREQUENCY_KEY, String.valueOf(rf.intValue()));
	}
	
	@Deprecated
	public static void setRightFrequency(int rf) {
		tinnitusFrequencies.setRightFrequency(rf);
		properties.setProperty(RIGHTFREQUENCY_KEY, String.valueOf(rf));
	}

	public static void writeProperties() {
		try (FileOutputStream fos = new FileOutputStream(
				ResourcesLocatorUtil.findFileUrl(ATTENUATOR_SETTINGS).getFile())) {
			properties.store(fos, null);
		} catch (IOException e) {
			log.error("There was an error saving info in the properties file.");
		}
	}

}
