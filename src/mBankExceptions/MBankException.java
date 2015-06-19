package mBankExceptions;

public class MBankException extends Exception{

	/**
	 * This class defines MBank Exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * MBank Exception constructor.
	 * generates the exception and print massage and error message to the console.
	 * @param String text
	 * @param Exception e
	 */
	public MBankException(String text, Exception e){
		super(text);
		System.err.println(text);
//		e.printStackTrace();
	}

}
