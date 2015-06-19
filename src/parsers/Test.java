package parsers;

import java.util.List;

import mBankExceptions.MBankException;
import beans.ClientBean;

public class Test {

	public static void main(String[] args) throws MBankException {
		DataExchanger de = new DataExchanger();
		List<ClientBean> test = null; 
		System.out.println(de.marshallClients(test));
		
	}

}
