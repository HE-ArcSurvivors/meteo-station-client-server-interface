
package ch.hearc.meteo.imp.use.remote.pclocal;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.imp.use.remote.PropertiesSingleton;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

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
		} catch (IOException e) {
			e.printStackTrace();
		}
		}

	public static void main() throws IOException
		{

		try {
			int dataToPrint = 3;

			//Making simulator data
			String portcom = "SIMULATEUR";
			MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
			String ipServer = PropertiesSingleton.getInstance().getIpServer();
			InetAddress inetIpAddress = InetAddress.getByName(ipServer);
			RmiURL rmiUrl = new RmiURL(RemoteAfficheurCreator.RMI_ID, inetIpAddress,
					RemoteAfficheurCreator.RMI_PORT);
			PCLocal pc = new PCLocal(meteoServiceOptions,portcom,null,rmiUrl);
			
//			MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create("COM1");
			
			

//
//			String moduleTitle = moduleName + ": " + ipServer + " [" + meteoService.getPort() + "]";
//
//			AffichageOptions affichageOptions = new AffichageOptions(dataToPrint, moduleTitle);

//			PCLocal pc = new PCLocal(meteoServiceOptions,portcom,affichageOptions,rmiUrl);
			
//			PCLocal pc = new PCLocal(null,portcom,null,null);
			pc.run();

		} catch (Exception e) {
			e.printStackTrace();
		}

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
