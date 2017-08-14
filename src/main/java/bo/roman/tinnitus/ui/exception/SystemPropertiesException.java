package bo.roman.tinnitus.ui.exception;

public class SystemPropertiesException extends Exception {
	private static final long serialVersionUID = 5945773531147810290L;
	private static final String MESSAGE = "There are not enough properties to instantiate a TinnitusAttenuator";

	public SystemPropertiesException() {
		super(MESSAGE);
	}

}
