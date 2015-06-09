package ch.hearc.meteo.imp.reseau;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreatorFactory_I;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;

public class RemoteAfficheurCreatorFactory implements RemoteAfficheurCreatorFactory_I{

	/*------------------------------------------------------------------*\
	 |*							Methodes Public							*|
	 \*------------------------------------------------------------------*/

	/*------------------------------*\
	 |*			  Static			*|
	 \*------------------------------*/

	public RemoteAfficheurCreator_I create() throws RemoteException {
		return RemoteAfficheurCreator.getInstance();
		
	}


}
