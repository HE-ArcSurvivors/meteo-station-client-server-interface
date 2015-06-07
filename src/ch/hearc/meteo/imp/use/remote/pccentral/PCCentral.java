package ch.hearc.meteo.imp.use.remote.pccentral;

import java.rmi.RemoteException;
import java.net.UnknownHostException;

import ch.hearc.meteo.imp.afficheur.real.AfficherFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreatorFactory;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory_I;

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
		geometry();
		control();
		apparence();
		
		try {
			server();
		} catch (UnknownHostException e) {
			System.err.println("error: server()");
			e.printStackTrace();
		}
		
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void server() throws UnknownHostException {

		try {
			String name = "PC Central";
			AffichageOptions affichageOptions = new AffichageOptions(0, name);
			remoteAfficheurCreator = RemoteAfficheurCreatorFactory.create();
			(new AfficherFactory()).createOnCentralPC(affichageOptions, null);
			
//			String name = "PC Central Simulateur";
//			AffichageOptions affichageOptions = new AffichageOptions(0, name);
//			remoteAfficheurCreator = RemoteAfficheurCreatorFactory.create();
//			(new AfficheurSimulateurFactory()).createOnCentralPC(affichageOptions, null);
			
//			remoteAfficheurCreator = RemoteAfficheurCreator.getInstance();
		} catch (RemoteException e) {
			System.err.println("error: server()");
			e.printStackTrace();
		}
		
		
	}

	private void apparence() {

	}

	private void control() {
		
	}

	private void geometry() {

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
