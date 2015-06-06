package ch.hearc.meteo.imp.use.remote.pccentral;

import java.net.UnknownHostException;

public class UsePCCentral {

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args) {
		try {
			main();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
	}

	public static void main() throws UnknownHostException {

		new PCCentral().run();

	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

}
