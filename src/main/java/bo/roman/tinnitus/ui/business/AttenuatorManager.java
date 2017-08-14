package bo.roman.tinnitus.ui.business;

import com.bo.tinnitus.SystemSoundParameters;
import com.bo.tinnitus.TinnitusAtenuator;
import com.bo.tinnitus.TinnitusFrequencies;

import bo.roman.tinnitus.ui.dao.FrequenciesDao;
import bo.roman.tinnitus.ui.dao.SystemSoundDao;

public class AttenuatorManager {
	private static AttenuatorManager instance;
	private final static FrequenciesDao fDao = new FrequenciesDao();
	private final static SystemSoundDao ssDao = new SystemSoundDao();

	private TinnitusAtenuator attenuator;

	private AttenuatorManager(TinnitusAtenuator attenuator) {
		this.attenuator = attenuator;
	}

	public static AttenuatorManager getInstance() {
		if (instance == null) {
			instance = new AttenuatorManager(createAttenuator());
		}
		return instance;
	}
	
	public TinnitusAtenuator getAttenuator() {
		if (attenuator == null) {
			attenuator = createAttenuator();
		}
		return attenuator;
	}
	
	private static TinnitusAtenuator createAttenuator() {
		return fDao.read()
				 .flatMap(f -> ssDao.read()
							        .map(ss -> new TinnitusAtenuator(f, ss)))
				 .orElse(null);
	}
	
	
	public void setTinnitusFrequencies(TinnitusFrequencies tf) {
		fDao.write(tf);
		if (attenuator != null) {
			attenuator.setTinnitusFrequencies(tf);
		}
	}

	public void setSystemSoundParameters(SystemSoundParameters ssp) {
		ssDao.write(ssp);
		if (attenuator != null) {
			attenuator.setSystemSoundParameters(ssp);
		}
	}
}
