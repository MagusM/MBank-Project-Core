package mbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import mBankExceptions.MBankException;

/**
 * This class defines the connection pool to the database with max of 10 connections<p>
 * uses the Embedded Driver
 * @author Simon Mor
 *
 */
public class ConnectionPool {

	private final int NUM_OF_CONS = 10;
	private static ConnectionPool pool;
	private List<Connection> connectionsList;

	public static ConnectionPool getConnectionPool() throws MBankException{
		if(pool == null){
			pool = new ConnectionPool();
		}
		return pool;
	}

	private ConnectionPool() throws MBankException{
		connectionsList = new ArrayList<>();
		try{
			//Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			for(int i=0;i<NUM_OF_CONS;i++){
				//connectionsList.add(DriverManager.getConnection("jdbc:derby:Users/MaguS/Documents/MyDB/MBank;create=true"));
				connectionsList.add(DriverManager.getConnection ("jdbc:derby://localhost:1527/MBank;create=true"));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new MBankException("DB is not available", e);
		}
	}
	
	public synchronized Connection getConnection(){
		while(connectionsList.isEmpty()){
			try{
				wait();
			}
			catch(InterruptedException e) {

			}
		}
		return connectionsList.remove(connectionsList.size()-1);
	}

	public synchronized boolean returnConnection(Connection conn) {
		boolean isReturned = connectionsList.add(conn);
		notify();
		return isReturned;
	}

	public void shutdown() {
		for(int i=0;i<NUM_OF_CONS;i++) {
			try{
				connectionsList.get(i).close();
			}
			catch(Exception e) {

			}
		}
	}


}
