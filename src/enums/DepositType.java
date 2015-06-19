package enums;

/**
 * This class defines the deposit Enums.
 * @author Simon Mor
 *
 */
public enum DepositType {
	SHORT("short"), LONG("long");
	
	private String nameAsString;
	private DepositType(String nameAsString) {
		 this.nameAsString = nameAsString;
	}
	
	public static DepositType convertStringToDepositType(String type) {
		if ("short".equals(type)) {
			return DepositType.SHORT;
		}
		return DepositType.LONG;
	}
	
	@Override
	public String toString() {
		return this.nameAsString;
	}
	
}
