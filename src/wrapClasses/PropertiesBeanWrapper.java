package wrapClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import beans.PropertiesBean;

/**
 * This is a wrapper class for properties bean to be used by JAXB
 * @author Simon Mor
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder={"propKey", "propValue"})
public class PropertiesBeanWrapper {
	private String propKey; 
	private String propValue;

	public PropertiesBeanWrapper() {super();}

	public PropertiesBeanWrapper(PropertiesBean propBean) {
		super();
		this.propKey = propBean.getPropKey();
		this.propValue = propBean.getPropValue();
	}

	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	
}
