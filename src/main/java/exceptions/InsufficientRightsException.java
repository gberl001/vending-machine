package exceptions;

@SuppressWarnings("serial")
public class InsufficientRightsException extends Exception {

	public InsufficientRightsException(String message) {
		super(message);
	}

    public InsufficientRightsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
