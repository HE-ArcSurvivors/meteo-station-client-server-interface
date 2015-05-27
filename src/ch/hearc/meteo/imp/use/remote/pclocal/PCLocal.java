package ch.hearc.meteo.imp.use.remote.pclocal;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;

import ch.hearc.meteo.imp.afficheur.real.AfficheurService;
import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class PCLocal implements PC_I {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCLocal(MeteoServiceOptions meteoServiceOptions, String portCom,
			AffichageOptions affichageOptions, RmiURL rmiURLafficheurManager) {
		this.meteoServiceOptions = meteoServiceOptions;
		this.portCom = portCom;
		this.affichageOptions = affichageOptions;
		this.rmiURLafficheurManager = rmiURLafficheurManager;
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run() {
		try {
			server(); // avant
		} catch (Exception e) {
			System.err.println("[PCLocal :  run : server : failed");
			e.printStackTrace();
		}

		try {
			client(); // aprüs
		} catch (RemoteException e) {
			System.err.println("[PCLocal :  run : client : failed");
			e.printStackTrace();
		}
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	
	
	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl(String ipAddress, String customPrefixe) throws UnknownHostException
	{
		String id = IdTools.createID(customPrefixe);

		//Convert ip address to inetAddress
		InetAddress inetConvertedIPAddress = null;
		try {
			inetConvertedIPAddress = InetAddress.getByName(ipAddress);
		} catch (java.net.UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new RmiURL(id, inetConvertedIPAddress, RMI_PORT);
	}
	
	

	/*------------------------------*\
	|*				server			*|
	\*------------------------------*/

	private void server() throws MeteoServiceException, RemoteException {

		 // Simulator
		meteoService_I = new MeteoServiceSimulatorFactory().create(portCom);
		meteoService_I.connect();
		
		
		meteoServiceWrapper = new MeteoServiceWrapper(meteoService_I);

		rmiURLservice = rmiUrl(LOCALHOST_IP, RMI_ID);
		RmiTools.shareObject(meteoServiceWrapper, rmiURLservice);

	}

	/*------------------------------*\
	|*				client			*|
	\*------------------------------*/

	private void client() throws RemoteException {
		new AfficheurService();

		remoteAfficheurCreator_I = (RemoteAfficheurCreator_I) RmiTools
				.connectionRemoteObjectBloquant(rmiURLafficheurManager);

		rmiURLafficheurService = remoteAfficheurCreator_I
				.createRemoteAfficheurService(affichageOptions, rmiURLservice);
		afficheurServiceWrapper_I = (AfficheurServiceWrapper_I) RmiTools
				.connectionRemoteObjectBloquant(rmiURLafficheurService);

	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
		
	// Inputs
	private MeteoServiceOptions meteoServiceOptions;
	private String portCom = null;
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;
	private RmiURL rmiURLservice;
	
	private MeteoService_I meteoService_I;
	private MeteoServiceWrapper meteoServiceWrapper;
	
	// Tools PRIVATE final
	private static final String LOCALHOST_IP = "127.0.0.1";
	private static final String PREFIXE = "METEO_SERVICE";
	
	// Tools PUBLIC final
	public static final String RMI_ID = PREFIXE;
	

	// Tools
	public static int RMI_PORT = RmiTools.PORT_RMI_DEFAUT;
	private RemoteAfficheurCreator_I remoteAfficheurCreator_I;
	private RmiURL rmiURLafficheurService;
	private AfficheurServiceWrapper_I afficheurServiceWrapper_I;
	private AfficheurService_I AfficheurService_I;

}
