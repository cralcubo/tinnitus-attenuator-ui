package bo.roman.tinnitus.ui.dao;

import java.util.Optional;

import com.bo.tinnitus.TinnitusFrequencies;

import bo.roman.tinnitus.ui.util.AttenuatorPropertiesUtil;

public class FrequenciesDao implements PropertiesDao<TinnitusFrequencies>{
	
	private TinnitusFrequencies instance = new TinnitusFrequencies();

	@Override
	public void write(TinnitusFrequencies tf) {
		AttenuatorPropertiesUtil.setLeftFrequency(tf.getLeftFrequency());
		AttenuatorPropertiesUtil.setRightFrequency(tf.getRightFrequency());
		AttenuatorPropertiesUtil.writeProperties();
	}

	@Override
	public Optional<TinnitusFrequencies> read() {
		instance.setLeftFrequency(AttenuatorPropertiesUtil.getLeftFrequency());
		instance.setRightFrequency(AttenuatorPropertiesUtil.getRightFrequency());
		return Optional.of(instance);
	}
}
