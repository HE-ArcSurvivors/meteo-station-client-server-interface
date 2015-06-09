
package ch.hearc.meteo.imp.use.local;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import gnu.io.CommPortIdentifier;

import ch.hearc.meteo.imp.com.real.MeteoFactory;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class UseSimple
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		try
			{
			main();
			}
		catch (MeteoServiceException e)
			{
			e.printStackTrace();
			}
		}

	public static void main() throws MeteoServiceException
		{

		//listPorts();
		MeteoService_I meteoService = (new MeteoFactory()).create("/dev/tty.SLAB_USBtoUART");
		use(meteoService);
		}

	static void listPorts()
		{
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		System.out.println("all serials");

		List<String> listSerial = new LinkedList<String>();
		while(portEnum.hasMoreElements())
			{
			CommPortIdentifier portIdentifier = portEnum.nextElement();

			if(portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL)
				{
				System.out.println(portIdentifier.getName());
				listSerial.add(portIdentifier.getName());
				}
			}
		}

	public static void use(MeteoService_I meteoService) throws MeteoServiceException
		{
		meteoService.connect();

		meteoService.addMeteoListener(new MeteoListener_I()
			{
				@Override public void temperaturePerformed(MeteoEvent event)
					{
					System.out.println("Temperature : \t "+ event.getValue() + " \t at time : " + event.getTime());
					}

				@Override public void pressionPerformed(MeteoEvent event)
					{
					System.out.println("Pression : \t "+ event.getValue() + " \t at time : " + event.getTime());
					}

				@Override public void altitudePerformed(MeteoEvent event)
					{
					System.out.println("Altitude : \t "+ event.getValue() + " \t at time : " + event.getTime());
					}
			});

		scenario(meteoService); // exemple!
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Exemple pour mettre � l'�preuve la logique de fonctionnement
	 * Checker dans la console que la logique verbeuse afficher est correcte
	 * </pre>
	 */
	private static void scenario(MeteoService_I meteoService) throws MeteoServiceException
		{
		MeteoServiceOptions meteoServiceOptions1 = new MeteoServiceOptions(100, 100, 100);

		//MeteoServiceOptions meteoServiceOptions2 = new MeteoServiceOptions(100, 100, 100);

		meteoService.start(meteoServiceOptions1);
		sleep(3000);
		meteoService.stop();
		meteoService.disconnect();

//		sleep(3000);
//		meteoService.stop();
//		for(int i = 1; i <= 10; i++)
//			{
//			meteoService.start(meteoServiceOptions1);
//			sleep(3000);
//			meteoService.stop();
//			sleep(3000);
//			}

		//meteoService.disconnect();
		}

	private static void sleep(long delayMS)
		{
		System.out.println("sleep main: " + delayMS);
		try
			{
			Thread.sleep(delayMS);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	}
