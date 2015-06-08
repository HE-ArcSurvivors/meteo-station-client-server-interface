package ch.hearc.meteo.imp.reseau;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ch.hearc.meteo.imp.afficheur.real.AfficheurFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

/**
 * <pre>
 * One instance only (Singleton) on PC-Central
 * RMI-Shared
 * </pre>
 */
public class RemoteAfficheurCreator implements RemoteAfficheurCreator_I {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private RemoteAfficheurCreator() throws RemoteException, UnknownHostException {
		server();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Remote use
	 */
	@Override
	public RmiURL createRemoteAfficheurService(
			AffichageOptions affichageOptions, RmiURL meteoServiceRmiURL)
			throws RemoteException {

		try {

			// client
			// make remote
			MeteoServiceWrapper_I meteoServiceWrapper = (MeteoServiceWrapper_I) RmiTools
					.connectionRemoteObjectBloquant(meteoServiceRmiURL);

			// server
			// make afficheurService for PCCentral
			AfficheurService_I afficheurService = createAfficheurService(
					affichageOptions, meteoServiceWrapper);
			// share afficheurService from PCCentral
			AfficheurServiceWrapper afficheurServiceWrapper = new AfficheurServiceWrapper(
					afficheurService);
			RmiURL RmiURLAfficheurService = rmiUrl();
			RmiTools.shareObject(afficheurServiceWrapper,
					RmiURLAfficheurService);

			// Return the remote on AfficheurService
			return RmiURLAfficheurService;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized RemoteAfficheurCreator_I getInstance()
			throws RemoteException, UnknownHostException {
		if (INSTANCE == null) {
			INSTANCE = new RemoteAfficheurCreator();
		}

		return INSTANCE;
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurService_I createAfficheurService(
			AffichageOptions affichageOptions,
			MeteoServiceWrapper_I meteoServiceRemote) {
		// create AfficheurService
		AfficheurService_I afficheurService = new AfficheurFactory().createOnLocalPC(affichageOptions, meteoServiceRemote);
		
//		try {
//			RemoteAfficheurCreator_I remoteAfficheurCreator = RemoteAfficheurCreator.getInstance();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		afficheurServiceList.add(afficheurService);
		return afficheurService;

	}

	private void server() throws RemoteException, UnknownHostException {

//		PC_CENTRAL_IP = System.getProperty("PC_CENTRAL_IP", LOCALHOST_IP);
//		PC_CENTRAL_ID = RemoteAfficheurCreator_I.class.getName();
//		INET_CONVERTED_IP_ADDRESS = InetAddress.getByName(PC_CENTRAL_IP);
//		RMI_URL = new RmiURL(PC_CENTRAL_ID,INET_CONVERTED_IP_ADDRESS, RMI_PORT);
//		RmiTools.shareObject(this, RMI_URL);

		RmiTools.shareObject(this, new RmiURL(PREFIXE));
	}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl() {
		String id = IdTools.createID(PREFIXE);

		return new RmiURL(id);
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private ArrayList<AfficheurService_I> afficheurServiceList;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Tools PRIVATE final
	private static final String LOCALHOST_IP = "127.0.0.1";
	private static final String PREFIXE = "AFFICHEUR_SERVICE";

	// Tools PUBLIC final
	public static final String RMI_ID = PREFIXE;

	// Tools
	public static int RMI_PORT = RmiTools.PORT_RMI_DEFAUT;
	public static RmiURL RMI_URL = null;
	private static RemoteAfficheurCreator_I INSTANCE = null;


}
