package exceptions;

@SuppressWarnings("serial")
public class OverwriteException extends Exception {

	public OverwriteException(String message) {
		super(message);
	}

    public OverwriteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
