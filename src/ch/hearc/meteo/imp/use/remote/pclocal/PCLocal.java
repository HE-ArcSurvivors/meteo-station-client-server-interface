package ch.hearc.meteo.imp.use.remote.pclocal;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper;
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
			client(); // apr�s
		} catch (RemoteException | MeteoServiceException e) {
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
	private static RmiURL rmiUrl(String ipAddress, String customPrefixe)
			throws UnknownHostException {
		String id = IdTools.createID(customPrefixe);

		// Convert ip address to inetAddress
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

		// get data

		// create meteoServiceWrapper
		meteoService = (new MeteoServiceSimulatorFactory()).create(portCom);
		meteoService.connect();
		meteoService.start(meteoServiceOptions);
		meteoServiceWrapper = new MeteoServiceWrapper(meteoService);

		// create afficheurServiceWrapper
		String titre = RmiTools.getLocalHost() + " " + meteoService.getPort();
		AffichageOptions affichageOptions = new AffichageOptions(3, titre);
		AfficheurService_I afficheurService = (new AfficheurSimulateurFactory())
				.createOnLocalPC(affichageOptions, meteoServiceWrapper);
		afficheurServiceWrapper = new AfficheurServiceWrapper(afficheurService);

		// share
		rmiURLMeteoService = rmiUrl(LOCALHOST_IP, PREFIXE_METEO_SERVICE);
		RmiTools.shareObject(meteoServiceWrapper, rmiURLMeteoService);
		// rmiURLAfficheurService =
		// rmiUrl(LOCALHOST_IP,PREFIXE_AFFICHEUR_SERVICE);

		// set remotes
		remoteAfficheurCreator = (RemoteAfficheurCreator_I) RmiTools
				.connectionRemoteObjectBloquant(RemoteAfficheurCreator.RMI_URL);
		rmiURLafficheurManager = remoteAfficheurCreator
				.createRemoteAfficheurService(affichageOptions,
						rmiURLMeteoService);

		afficheurServiceWrapper = (AfficheurServiceWrapper_I) RmiTools
				.connectionRemoteObjectBloquant(rmiURLafficheurManager);

	}

	/*------------------------------*\
	|*				client			*|
	\*------------------------------*/

	private void client() throws RemoteException, MeteoServiceException {
		
		synchroClientServer();
		// new AfficheurService();

		afficheurService = (new AfficheurSimulateurFactory()).createOnLocalPC(
				affichageOptions, meteoServiceWrapper);

		meteoService.addMeteoListener(new MeteoListener_I() {

			@Override
			public void temperaturePerformed(MeteoEvent event) {

				afficheurService.printTemperature(event);
				try {
					afficheurServiceWrapper.printTemperature(event);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void pressionPerformed(MeteoEvent event) {
				afficheurService.printPression(event);
				try {
					afficheurServiceWrapper.printPression(event);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void altitudePerformed(MeteoEvent event) {
				afficheurService.printAltitude(event);
				try {
					afficheurServiceWrapper.printAltitude(event);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private void synchroClientServer() {
		// TODO Auto-generated method stub
		
	}

	// Inputs
	private MeteoServiceOptions meteoServiceOptions;
	private String portCom = null;
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLAfficheurService;
	private RmiURL rmiURLMeteoService;
	private RmiURL rmiURLafficheurManager;

	// Tools PRIVATE final
	private static final String LOCALHOST_IP = "127.0.0.1";
	private static final String PREFIXE = "METEO_SERVICE";
	private static final String PREFIXE_METEO_SERVICE = "METEO_SERVICE";
	private static final String PREFIXE_AFFICHEUR_SERVICE = "AFFICHEUR_SERVICE";

	// Tools PUBLIC final
	public static final String RMI_ID = PREFIXE;

	// Tools
	public static int RMI_PORT = RmiTools.PORT_RMI_DEFAUT;
	private RemoteAfficheurCreator_I remoteAfficheurCreator;
	private AfficheurServiceWrapper_I afficheurServiceWrapper;
	private AfficheurService_I afficheurService;
	private MeteoService_I meteoService;
	private MeteoServiceWrapper meteoServiceWrapper;

}
