
package ch.hearc.meteo.imp.use.local;

import ch.hearc.meteo.imp.afficheur.JFrameSelectionPortCom;

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
//		MeteoPortDetectionService testConnect = new MeteoPortDetectionService(new ComOption());
//		//boolean b = testConnect.isStationMeteoAvailable("/dev/tty.SLAB_USBtoUART", 3000);
//
//		List<String> listPortsMeteo = testConnect.findListPortMeteo();
//
//		System.out.println(listPortsMeteo);

		new JFrameSelectionPortCom();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
