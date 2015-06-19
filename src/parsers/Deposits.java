package parsers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import wrapClasses.DepositBeanWrapper;
import beans.DepositBean;

@XmlRootElement(name="deposits")
@XmlAccessorType(XmlAccessType.FIELD)
public class Deposits {
	
	@XmlElement(name="deposit", type=DepositBeanWrapper.class)
	private List<DepositBeanWrapper> deposits;

	public List<DepositBeanWrapper> getDeposits() {
		return deposits;
	}

	public void setDeposits(List<DepositBean> beanDeposits) {
		List<DepositBeanWrapper> wrappedDeposits = new ArrayList<DepositBeanWrapper>();
		for(DepositBean depositBean : beanDeposits) {
			wrappedDeposits.add(new DepositBeanWrapper(depositBean));
		}
		this.deposits = wrappedDeposits;
	}
	

}
