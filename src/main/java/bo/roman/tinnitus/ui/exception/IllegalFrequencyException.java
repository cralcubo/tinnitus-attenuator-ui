package bo.roman.tinnitus.ui.exception;

public class IllegalFrequencyException extends Exception {
	private static final long serialVersionUID = 1L;
	private final static String DEFAULT_MESSAGE = "A valid frequency should be bigger than 0 and should be a natural number.";
	
	public IllegalFrequencyException() {
		super(DEFAULT_MESSAGE);
	}
	
	public IllegalFrequencyException(Exception e) {
		super(DEFAULT_MESSAGE, e);
	}
	

}
