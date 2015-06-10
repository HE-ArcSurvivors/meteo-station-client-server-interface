package ch.hearc.meteo.imp.reseau;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.hearc.meteo.imp.afficheur.real.AfficheurFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

//Developpé en collaboration avec Nils Ryter

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

	private RemoteAfficheurCreator() throws RemoteException {
//		afficheurServiceList = new ArrayList<AfficheurService_I>();
		afficheurServiceMap = new HashMap<String, AfficheurService_I>();
		rmiURLMap = new HashMap<String, RmiURL>();
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

			// client
			// make remote
			MeteoServiceWrapper_I meteoServiceWrapper = (MeteoServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(meteoServiceRmiURL, 500, 5);
			
			// build an unique key
			String key = affichageOptions.getTitre();
			
			// Check if PC local already exist
			if (rmiURLMap.containsKey(key))
				{
				System.out.println("I Know you: " + key );
				}
			else
				{
				System.out.println("I don't know you: " + key);
				}
			
			// server
			// make afficheurService for PCCentral
			AfficheurService_I afficheurService = createAfficheurService(
					affichageOptions, meteoServiceWrapper);

			// share afficheurService from PCCentral
			AfficheurServiceWrapper afficheurServiceWrapper = new AfficheurServiceWrapper(
					afficheurService);
			RmiURL rmiURLAfficheurService = rmiUrl();
			RmiTools.shareObject(afficheurServiceWrapper, rmiURLAfficheurService);

			// add to maps
			afficheurServiceMap.put(key, afficheurService);
			rmiURLMap.put(key, rmiURLAfficheurService);

			return rmiURLMap.get(key);
			
			
			//			MeteoServiceWrapper_I meteoServiceWrapper = (MeteoServiceWrapper_I) RmiTools
//					.connectionRemoteObjectBloquant(meteoServiceRmiURL);
//			meteoServiceRemoteList.add(meteoServiceWrapper);

//			// server
//			// make afficheurService for PCCentral
//			AfficheurService_I afficheurService = createAfficheurService(
//					affichageOptions, meteoServiceWrapper);
//			// share afficheurService from PCCentral
//			AfficheurServiceWrapper afficheurServiceWrapper = new AfficheurServiceWrapper(
//					afficheurService);
//			RmiURL rmiURLAfficheurService = rmiUrl();
//			RmiTools.shareObject(afficheurServiceWrapper,
//					rmiURLAfficheurService);
//
//			// Return the remote on AfficheurService
//			return RmiURLAfficheurService;

	}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized RemoteAfficheurCreator_I getInstance()
			throws RemoteException {
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
		
		
//		AfficheurService_I afficheurService = new AfficheurFactory().createOnCentralPC(affichageOptions, meteoServiceRemote);
		AfficheurService_I afficheurService = new AfficheurFactory().createOnLocalPC(affichageOptions, meteoServiceRemote);
		
//		afficheurServiceList.add(afficheurService);
		
//		try {
//			RemoteAfficheurCreator_I remoteAfficheurCreator = RemoteAfficheurCreator.getInstance();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		return afficheurService;

	}

	private void server() throws RemoteException {
		
//		InetAddress inetIpAddress = null;
//		try {
//			inetIpAddress = InetAddress.getByName("192.168.0.14");
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		RmiURL rmiUrl = new RmiURL(PREFIXE, inetIpAddress,RMI_PORT);
//		RmiTools.shareObject(this, rmiUrl);
		

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

	private ArrayList<MeteoServiceWrapper_I> meteoServiceRemoteList;
//	private ArrayList<AfficheurService_I> afficheurServiceList;
	private Map<String, AfficheurService_I> afficheurServiceMap;
	private Map<String, RmiURL> rmiURLMap;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/
	
	private int disconnectedTimer;

	// Tools PRIVATE final
	private static final String PREFIXE = "AFFICHEUR_SERVICE";

	// Tools PUBLIC final
	public static final String RMI_ID = PREFIXE;

	// Tools
	public static int RMI_PORT = RmiTools.PORT_RMI_DEFAUT;
	public static RmiURL RMI_URL = null;
	private static RemoteAfficheurCreator_I INSTANCE = null;


}
