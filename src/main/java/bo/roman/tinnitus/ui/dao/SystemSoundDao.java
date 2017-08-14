package bo.roman.tinnitus.ui.dao;

import java.util.Optional;

import com.bo.tinnitus.SystemSoundParameters;

import bo.roman.tinnitus.ui.util.AttenuatorPropertiesUtil;

public class SystemSoundDao implements PropertiesDao<SystemSoundParameters> {
	
	private final SystemSoundParameters instance = new SystemSoundParameters();

	@Override
	public void write(SystemSoundParameters ssp) {
		AttenuatorPropertiesUtil.setAudioSource(ssp.getSourceDeviceName());
		AttenuatorPropertiesUtil.setAudioTarget(ssp.getTargetSoundDevice());
		AttenuatorPropertiesUtil.writeProperties();
	}

	@Override
	public Optional<SystemSoundParameters> read() {
		instance.setSource(AttenuatorPropertiesUtil.getAudioSource());
		instance.setTarget(AttenuatorPropertiesUtil.getAudioTarget());
		return Optional.of(instance);
	}
	
}
