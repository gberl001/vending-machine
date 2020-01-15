package exceptions;

@SuppressWarnings("serial")
public class RecalledProductException extends Exception{
	public RecalledProductException(String message) {
		super(message);
	}

    public RecalledProductException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
