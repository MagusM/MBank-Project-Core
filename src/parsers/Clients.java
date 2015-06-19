package parsers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import wrapClasses.ClientBeanWrapper;
import beans.ClientBean;

@XmlRootElement(name="clients")
@XmlAccessorType(XmlAccessType.FIELD)
public class Clients {
	
	@XmlElement(name="clients", type=ClientBeanWrapper.class)
	private List<ClientBeanWrapper> clients;

	public List<ClientBeanWrapper> getClients() {
		return clients;
	}

	public void setClients(List<ClientBean> beanClients) {
		List<ClientBeanWrapper> wrappedClients = new ArrayList<ClientBeanWrapper>();
		for (ClientBean clientBean : beanClients) {
			wrappedClients.add(new ClientBeanWrapper(clientBean));
		}
		this.clients = wrappedClients ;
	}
	
	
}
