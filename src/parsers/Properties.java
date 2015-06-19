package parsers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import wrapClasses.PropertiesBeanWrapper;
import beans.PropertiesBean;

@XmlRootElement(name="properties")
@XmlAccessorType(XmlAccessType.FIELD)
public class Properties {
	
	@XmlElement(name="property", type=PropertiesBeanWrapper.class)
	private List<PropertiesBeanWrapper> properties;

	public List<PropertiesBeanWrapper> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertiesBean> properties) {
		List<PropertiesBeanWrapper> wrappedProperties = new ArrayList<PropertiesBeanWrapper>();
		for (PropertiesBean propertiesBean : properties) {
			wrappedProperties.add(new PropertiesBeanWrapper(propertiesBean));
		}
		this.properties = wrappedProperties;
	}

}
