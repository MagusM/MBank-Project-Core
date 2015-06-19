package parsers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import wrapClasses.AccountsBeanWrapper;
import wrapClasses.ClientBeanWrapper;
import beans.AccountsBean;

@XmlRootElement(name="accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class Accounts {
	
	@XmlElement(name="account", type=AccountsBeanWrapper.class)
	private List<AccountsBeanWrapper> accounts;

	public List<AccountsBeanWrapper> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountsBean> accounts) {
		List<AccountsBeanWrapper> wrappedAccounts = new ArrayList<AccountsBeanWrapper>();
		for (AccountsBean accountsBean : accounts) {
			wrappedAccounts.add(new AccountsBeanWrapper(accountsBean));
		}
		this.accounts = wrappedAccounts;
	}
	

}
