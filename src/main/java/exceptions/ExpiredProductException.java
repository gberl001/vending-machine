package exceptions;

@SuppressWarnings("serial")
public class ExpiredProductException extends Exception{
	public ExpiredProductException(String message) {
		super(message);
	}

    public ExpiredProductException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
