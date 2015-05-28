
package ch.hearc.meteo.imp.use.remote.pclocal;

import java.net.InetAddress;
import java.rmi.UnknownHostException;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

public class UsePCLocal
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		String portName = "COM1";
	
		MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create(portName);
		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
		
		String titre = RmiTools.getLocalHost() + " " + meteoService.getPort();
		AffichageOptions affichageOptions = new AffichageOptions(3, titre);
		RmiURL rmiURL = null;
		
		new PCLocal(meteoServiceOptions,portName,affichageOptions,rmiURL).run();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
