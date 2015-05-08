package Meteo_Implementation.src.ch.hearc.meteo.imp.com.test;


import java.util.Enumeration;

import gnu.io.CommPortIdentifier;



public class ComList
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
		listPorts();
		}

	static void listPorts()
		{
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		while(portEnum.hasMoreElements())
			{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			System.out.println(portIdentifier.getName() + " - " + getPortTypeName(portIdentifier.getPortType()));
			}
		}

	private static String getPortTypeName(int portType)
		{

		switch(portType)
			{
			case CommPortIdentifier.PORT_I2C:
				return "I2C";
			case CommPortIdentifier.PORT_PARALLEL:
				return "Parallel";
			case CommPortIdentifier.PORT_RAW:
				return "Raw";
			case CommPortIdentifier.PORT_RS485:
				return "RS485";
			case CommPortIdentifier.PORT_SERIAL:
				return "Serial";

			default:
				return "unknown type";
			}
		}


	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}

