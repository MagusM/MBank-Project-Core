package enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the account Enums.
 * @author Simon Mor
 *
 */
@XmlType
@XmlEnum(String.class)
public enum AccountType {
	@XmlEnumValue("regular")
	REGULAR("regular"),
	@XmlEnumValue("gold")
	GOLD("gold"),
	@XmlEnumValue("platinum")
	PLATINUM("platinum");
	
	private String nameAsString;
	private AccountType(String nameAsString) {
		 this.nameAsString=nameAsString;
	}
	
	@Override
	public String toString() {
		return this.nameAsString;
	}
	
	public static AccountType getTypeFromString(String type) {
		switch (type) {
		case "gold":
			return AccountType.GOLD;
		case "platinum":
			return AccountType.PLATINUM;
		default:
			return AccountType.REGULAR;
		}
	}

}
