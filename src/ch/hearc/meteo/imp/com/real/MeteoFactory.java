
package ch.hearc.meteo.imp.com.real;

import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;
import ch.hearc.meteo.imp.com.real.com.ComConnexion;
import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceFactory_I;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

/**
 * <pre>
 * Problem :
 * 		MeteoService est un MeteoServiceCallback_I
 * 		ComConnexions_I utilise MeteoServiceCallback_I
 * 		MeteoService utilise ComConnexions_I
 *
 * On est dans la situation
 * 		A(B)
 * 		B(A)
 *
 * Solution
 * 		 B
 * 		 A(B)
 *  	 B.setA(A)
 *
 *  Autrement dit:
 *
 *		ComConnexions_I comConnexion=new ComConnexion( portName,  comOption);
 *      MeteoService_I meteoService=new MeteoService(comConnexion);
 *      comConnexion.setMeteoServiceCallback(meteoService);
 *
 *      Ce travail doit se faire dans la factory
 *
 *  Warning : call next
 *  	setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
 *
 *  Faire tout ca dans MeteoFactory
 *
 *  </pre>
 */
public class MeteoFactory implements MeteoServiceFactory_I
	{

	public MeteoFactory(ComOption comOption) //TODO : ? créer ici comOption ?
		{
		this.comOption = comOption;
		}

	@Override
	public MeteoService_I create(String portName)
		{
		comConnexion = new ComConnexion(portName, comOption);
		meteoService = new MeteoService(comConnexion);
		comConnexion.setMeteoServiceCallback((MeteoServiceCallback_I)meteoService);

		return meteoService;
		}

	//input
	private ComOption comOption;

	//Tools
	private ComConnexion comConnexion;
	private MeteoService_I meteoService;

	}
