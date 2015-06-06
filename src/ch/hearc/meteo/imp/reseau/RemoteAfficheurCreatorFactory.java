package ch.hearc.meteo.imp.reseau;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;

public class RemoteAfficheurCreatorFactory {

	/*------------------------------------------------------------------*\
	 |*							Methodes Public							*|
	 \*------------------------------------------------------------------*/

	/*------------------------------*\
	 |*			  Static			*|
	 \*------------------------------*/

	public static RemoteAfficheurCreator_I create() throws RemoteException, UnknownHostException {
		return RemoteAfficheurCreator.getInstance();
	}


}
