package mbank;

public class RegisterDBDriver {

	public RegisterDBDriver() throws ClassNotFoundException {
		super();
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
	}
	
}
