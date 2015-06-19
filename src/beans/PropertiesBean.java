package beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * This class defines the property bean
 * @author Simon Mor
 *
 */
@XmlRootElement
public class PropertiesBean {
	private String propKey; 
	private String propValue;
	
	public PropertiesBean(){super();}
	
	public PropertiesBean(String propKey, String propValue) {
		super();
		this.propKey = propKey;
		this.propValue = propValue;
	}

	public String getPropKey() {
		return propKey;
	}

	public String getPropValue() {
		return propValue;
	}
	
	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	@Override
	public String toString() {
		return "PropertiesBean [propKey=" + propKey + ", propValue="
				+ propValue + "]";
	}
	

}
