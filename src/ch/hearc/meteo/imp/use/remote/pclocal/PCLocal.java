package ch.hearc.meteo.imp.use.remote.pclocal;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.real.AfficheurFactory;
import ch.hearc.meteo.imp.com.real.MeteoFactory;
import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.imp.use.remote.PropertiesSingleton;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

// Developpé en collaboration avec Nils Ryter

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
		this.connected = false;

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
		} catch (MeteoServiceException | IOException e) {
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

	/*------------------------------*\
	|*				server			*|
	\*------------------------------*/

	private void server() throws MeteoServiceException, IOException {

		if (portCom == "SIMULATEUR")
		{
			meteoService = (new MeteoServiceSimulatorFactory()).create("COM1");
		}else
		{
			meteoService = (new MeteoFactory()).create(portCom);
		}

		meteoService.connect();
		meteoService.start(meteoServiceOptions);

		meteoServiceWrapper = new MeteoServiceWrapper(meteoService);


		// PC Local
		AffichageOptions affichageOptionPCLocal = new AffichageOptions(3,
				"PC Local: " + portCom);
		afficheurService = (new AfficheurFactory()).createOnLocalPC(
				affichageOptionPCLocal, meteoServiceWrapper);
		
		affichageOptions = affichageOptionPCLocal;

//		printAfficheurService();
		
		String ipServer = PropertiesSingleton.getInstance().getIpServer();
		InetAddress inetIpAddress = InetAddress.getByName(ipServer);
//		rmiURLMeteoService = new RmiURL(IdTools.createID("AFFICHEUR_SERVICE"),inetIpAddress);
		rmiURLMeteoService = new RmiURL(IdTools.createID(PREFIXE),inetIpAddress);
		
        RmiTools.shareObject(meteoServiceWrapper, rmiURLMeteoService);


	}

	/*------------------------------*\
	|*				client			*|
	\*------------------------------*/

	private void client() throws MeteoServiceException, IOException {
		
		
		String ipLocal = PropertiesSingleton.getInstance().getIpLocal();
//		System.setProperty("java.rmi.server.hostname", ipLocal);
		InetAddress inetIpAddress = InetAddress.getByName(ipLocal);
		rmiURLMeteoService = new RmiURL(IdTools.createID(PREFIXE),inetIpAddress);
		
		// PC Central
		final AffichageOptions affichageOptionPCCentral = new AffichageOptions(
				3, PropertiesSingleton.getInstance().getStationName() + " @ " + PropertiesSingleton.getInstance().getIpLocal() + " [" + portCom + "]");

		
		Thread threadReconnection = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						try {
							if (!connected) {
								System.out.println("Connexion en cours");

								RemoteAfficheurCreator_I remoteAfficheurCreator = (RemoteAfficheurCreator_I) RmiTools
										.connectionRemoteObjectBloquant(
												rmiURLafficheurManager, 1000, 5);
								rmiURLRemoteAfficheurCreator = remoteAfficheurCreator
										.createRemoteAfficheurService(
												affichageOptionPCCentral,
												rmiURLMeteoService);

								afficheurServiceWrapper = (AfficheurServiceWrapper_I) RmiTools
										.connectionRemoteObjectBloquant(
												rmiURLRemoteAfficheurCreator,
												1000, 5);

								connected = true;
								System.out.println("Connecté");
							}
						} catch (RemoteException e) {
							e.printStackTrace();
							System.out.println("Echec de connexion");
						}

						Thread.sleep(10000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		threadReconnection.start();

		meteoService.addMeteoListener(new MeteoListener_I() {

			private static final long serialVersionUID = 1L;

			@Override
			public void temperaturePerformed(MeteoEvent event) {
				afficheurService.printTemperature(event);
				try {

					if (connected) {
						
						afficheurServiceWrapper.printTemperature(event);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
					errorManager();
				}
			}

			@Override
			public void pressionPerformed(MeteoEvent event) {
				afficheurService.printPression(event);
				try {

					if (connected) {

						afficheurServiceWrapper.printPression(event);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
					errorManager();
				}
			}

			@Override
			public void altitudePerformed(MeteoEvent event) {
				afficheurService.printAltitude(event);
				try {

					if (connected) {

						afficheurServiceWrapper.printAltitude(event);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
					errorManager();
				}
			}
		});



	}

	private synchronized void errorManager() {
		{
			connected = false;

			// System.exit(-1);
		}
	}
	
	private void shutdown() {
		{
			System.exit(0);
		}
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	RmiURL rmiURLRemoteAfficheurCreator = null;

	// Inputs
	private MeteoServiceOptions meteoServiceOptions;
	private String portCom = null;
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;
	private RmiURL rmiURLMeteoService;

	// Tools PRIVATE final
	private static final String PREFIXE = "METEO_SERVICE";

	// Tools PUBLIC final
	public static final String RMI_ID = PREFIXE;

	// Tools
	private boolean connected;

	public static int RMI_PORT = RmiTools.PORT_RMI_DEFAUT;
	private AfficheurServiceWrapper_I afficheurServiceWrapper;
	private AfficheurService_I afficheurService;
	private MeteoService_I meteoService;
	private MeteoServiceWrapper meteoServiceWrapper;

}
