
package ch.hearc.meteo.imp.use.local;

import java.util.List;

import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.imp.com.real.port.MeteoPortDetectionService;

public class UseMeteoPortDetection
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
		MeteoPortDetectionService testConnect = new MeteoPortDetectionService(new ComOption());
		//boolean b = testConnect.isStationMeteoAvailable("/dev/tty.SLAB_USBtoUART", 3000);

		List<String> listPortsMeteo = testConnect.findListPortMeteo();

		System.out.println(listPortsMeteo);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
