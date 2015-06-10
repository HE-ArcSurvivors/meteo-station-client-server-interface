
package ch.hearc.meteo.imp.com.real.port;

import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionServiceFactory_I;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;

public class MeteoPortDetectionServiceFactory implements MeteoPortDetectionServiceFactory_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public MeteoPortDetectionServiceFactory()
		{
		//rien
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/
	@Override
	public MeteoPortDetectionService_I create()
		{
		return new MeteoPortDetectionService(new ComOption());
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
