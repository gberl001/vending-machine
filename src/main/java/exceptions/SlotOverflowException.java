package exceptions;

@SuppressWarnings("serial")
public class SlotOverflowException extends Exception {
	public SlotOverflowException(String message) {
		super(message);
	}

    public SlotOverflowException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
