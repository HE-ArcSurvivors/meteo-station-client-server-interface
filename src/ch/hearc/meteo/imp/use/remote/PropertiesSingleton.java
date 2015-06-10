package ch.hearc.meteo.imp.use.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesSingleton {

	private PropertiesSingleton() throws IOException {
		processFile();
	}

	public static PropertiesSingleton getInstance() throws IOException {
		if (propertiesSingleton == null) {
			propertiesSingleton = new PropertiesSingleton();
		}
		return propertiesSingleton;
	}

	private void processFile() throws IOException {
		File file = new File(FILE_NAME);

		if (!file.exists()) {
			file.createNewFile();
			loadFile(file);
			saveFile(file);
		} else {
			loadFile(file);
		}
	}

	private void loadFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);

		Properties property = new Properties();
		property.load(bis);

		ipServer = property.getProperty(SERVER_IP_ADDRESS, "127.0.0.1");
		ipLocal = property.getProperty(LOCAL_IP_ADDRESS, "127.0.0.1");
		nameStation = property.getProperty(STATION_NAME, "STATION METEO");

//		rmiSocketTimeOut = Integer.valueOf(property.getProperty(
//				NAME_RMI_SOCKET_TIMEOUT, "10000"));
//		rmiConnectionTimeOut = Integer.valueOf(property.getProperty(
//				NAME_RMI_CONNECTION_TIMEOUT, "10000"));
//		rmiConnectionTry = Integer.valueOf(property.getProperty(
//				NAME_NAME_RMI_CONNECTION_TRY, "10000"));

		bis.close();
		fis.close();
	}

	private void saveFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		Properties property = new Properties();

//		property.setProperty(NAME_RMI_SOCKET_TIMEOUT,
//				String.valueOf(rmiSocketTimeOut));
//		property.setProperty(NAME_RMI_CONNECTION_TIMEOUT,
//				String.valueOf(rmiConnectionTimeOut));
//		property.setProperty(NAME_NAME_RMI_CONNECTION_TRY,
//				String.valueOf(rmiConnectionTry));
		
		
		property.setProperty(LOCAL_IP_ADDRESS, ipLocal);
		property.setProperty(STATION_NAME, nameStation);
		property.setProperty(SERVER_IP_ADDRESS, ipServer);

		property.store(bos, "# Config file for Meteo Station");

		bos.close();
		fos.close();
	}
	
	
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getIpServer()
		{
		return this.ipServer;
		}

	public String getIpLocal()
		{
		return this.ipLocal;
		}

	public String getStationName()
		{
		return this.nameStation;
		}

	public int getRmiSocketTimeOut()
		{
		return this.rmiSocketTimeOut;
		}

	public int getRmiConnectionTimeOut()
		{
		return this.rmiConnectionTimeOut;
		}

	public int getRmiConnectionTry()
		{
		return this.rmiConnectionTry;
		}
	
	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setIpServer(String ipServer)
		{
		this.ipServer = ipServer;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private String ipServer;
	private String ipLocal;
	private String nameStation;

	private int rmiSocketTimeOut;
	private int rmiConnectionTimeOut;
	private int rmiConnectionTry;

	private final String FILE_NAME = "./settings.properties";

	private static String SERVER_IP_ADDRESS = "SERVER_IP_ADDRESS";
	private static String LOCAL_IP_ADDRESS = "LOCAL_IP_ADDRESS";
	private static String STATION_NAME = "STATION_NAME";

//	private static String NAME_RMI_SOCKET_TIMEOUT = "NAME_RMI_SOCKET_TIMEOUT";
//	private static String NAME_RMI_CONNECTION_TIMEOUT = "NAME_RMI_CONNECTION_TIMEOUT";
//	private static String NAME_NAME_RMI_CONNECTION_TRY = "NAME_NAME_RMI_CONNECTION_TRY";

	private static PropertiesSingleton propertiesSingleton;

}
