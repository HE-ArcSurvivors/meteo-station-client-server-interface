
package ch.hearc.meteo.imp.use.remote.pclocal;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class UsePCLocal
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		try {
			main();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		}

	public static void main() throws UnknownHostException
		{

		try {
			int dataToPrint = 3;

			//Making simulator data
			String portcom = "COM1";
			MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create(portcom);
			MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);

			//Making real data
//			MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(dataToPrint, dataToPrint, dataToPrint);

			//Get Properties from config file
			FileInputStream fis = new FileInputStream(FILE_NAME);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Properties property = new Properties();
			property.load(bis);
			String ipAddress = property.getProperty(IP_ADDRESS);
			InetAddress inetIpAddress = InetAddress.getByName(ipAddress);
			String moduleName = property.getProperty(MODULE_NAME);
			bis.close();
			fis.close();

			RmiURL rmiUrl = new RmiURL(RemoteAfficheurCreator.RMI_ID, inetIpAddress,
					RemoteAfficheurCreator.RMI_PORT);

			String moduleTitle = moduleName + ": " + RmiTools.getLocalHost() + " " + meteoService.getPort();

			AffichageOptions affichageOptions = new AffichageOptions(dataToPrint, moduleTitle);

			PCLocal pc = new PCLocal(meteoServiceOptions,portcom,affichageOptions,rmiUrl);
			pc.run();

		} catch (Exception e) {
			e.printStackTrace();
		}

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/


	// Static tools
	private static final String IP_ADDRESS = "IP_ADDRESS";
	private static final String MODULE_NAME = "MODULE_NAME";
	private static final String FILE_NAME = "./settings.properties";

	}
