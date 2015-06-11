package ch.hearc.meteo.imp.use.remote.pccentral;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.real.AfficheurFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreatorFactory;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.imp.use.remote.PropertiesSingleton;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;

public class PCCentral implements PC_I {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCCentral() {

	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run() {

		try {
			server();
		} catch (IOException e) {
			System.err.println("error: server()");
			e.printStackTrace();
		}

	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void server() throws IOException {

			String name = "PC Central @ " + PropertiesSingleton.getInstance().getIpLocal() + ":" + RemoteAfficheurCreator.RMI_PORT;
			AffichageOptions affichageOptions = new AffichageOptions(0, name);
			remoteAfficheurCreator = (new RemoteAfficheurCreatorFactory()).create();
			(new AfficheurFactory()).createOnCentralPC(affichageOptions, null);

//			String name = "PC Central Simulateur";
//			AffichageOptions affichageOptions = new AffichageOptions(0, name);
//			remoteAfficheurCreator = RemoteAfficheurCreatorFactory.create();
//			(new AfficheurSimulateurFactory()).createOnCentralPC(affichageOptions, null);

//			remoteAfficheurCreator = RemoteAfficheurCreator.getInstance();

	}

	/*------------------------------------------------------------------*\
	|*							GET Methodes							*|
	\*------------------------------------------------------------------*/


	/*------------------------------------------------------------------*\
	|*							SET Methodes							*|
	\*------------------------------------------------------------------*/


	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private RemoteAfficheurCreator_I remoteAfficheurCreator;

}
