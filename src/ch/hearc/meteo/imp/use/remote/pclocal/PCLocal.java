package ch.hearc.meteo.imp.use.remote.pclocal;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.util.List;

import ch.hearc.meteo.imp.afficheur.real.AfficherFactory;
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
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory_I;

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
		this.connected = true;

		// portComs = new LinkedList<String>();
		// meteoServices = new LinkedList<MeteoService_I>();

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

	/*------------------------------*\
	|*				server			*|
	\*------------------------------*/

	private void server() throws MeteoServiceException, RemoteException {

		// create meteoServiceWrapper
		// meteoService = MeteoServiceFactory_I.create(portCom);
		// meteoServices.add(meteoService);

		meteoService = (new MeteoServiceSimulatorFactory()).create(portCom);
//		meteoService.connect();
//		meteoService.start(meteoServiceOptions);
		meteoServiceWrapper = new MeteoServiceWrapper(meteoService);
		rmiURLMeteoService = new RmiURL(IdTools.createID(PREFIXE));
		RmiTools.shareObject(meteoServiceWrapper, rmiURLMeteoService);
		
		AffichageOptions affichageOptionPCLocal = new AffichageOptions(3, "PC Local");
		afficheurService = (new AfficherFactory()).createOnLocalPC(affichageOptionPCLocal, meteoServiceWrapper);

	}

	/*------------------------------*\
	|*				client			*|
	\*------------------------------*/

	private void client() throws RemoteException, MeteoServiceException {
		
		AffichageOptions affichageOptionPCCentral = new AffichageOptions(3, "PC Central");
		RemoteAfficheurCreator_I remoteAfficheurCreator =(RemoteAfficheurCreator_I)RmiTools.connectionRemoteObjectBloquant(rmiURLafficheurManager);
		RmiURL rmiURLRemoteAfficheurCreator = remoteAfficheurCreator
				.createRemoteAfficheurService(affichageOptionPCCentral, rmiURLMeteoService);
		afficheurServiceWrapper = (AfficheurServiceWrapper_I) RmiTools
				.connectionRemoteObjectBloquant(rmiURLRemoteAfficheurCreator);
		
		// on PCLocal
		afficheurService = (new AfficheurFactory()).createOnLocalPC(affichageOptions, meteoServiceWrapper);

		meteoService.addMeteoListener(new MeteoListener_I() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void temperaturePerformed(MeteoEvent event) {
				try {
					if (connected) {
						afficheurService.printTemperature(event);
						afficheurServiceWrapper.printTemperature(event);
					}
				} catch (RemoteException e) {
					errorManager();
				}
			}

			@Override
			public void pressionPerformed(MeteoEvent event) {
				try {
					if (connected) {
						afficheurService.printPression(event);
						afficheurServiceWrapper.printPression(event);
					}
				} catch (RemoteException e) {
					errorManager();
				}
			}

			@Override
			public void altitudePerformed(MeteoEvent event) {
				try {
					if (connected) {
						afficheurService.printAltitude(event);
						afficheurServiceWrapper.printAltitude(event);
					}
				} catch (RemoteException e) {
					errorManager();
				}
			}
		});

		meteoService.connect();
		meteoService.start(meteoServiceOptions);

	}
	
	
	private void errorManager()
	{
	synchronized (this)
		{
		connected = false;
		System.err.println("Lost Connection");
		System.exit(-1);
		}
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

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
	private List<String> portComs;
	private List<MeteoService_I> meteoServices;

	public static int RMI_PORT = RmiTools.PORT_RMI_DEFAUT;
	private AfficheurServiceWrapper_I afficheurServiceWrapper;
	private AfficheurService_I afficheurService;
	private MeteoService_I meteoService;
	private MeteoServiceWrapper meteoServiceWrapper;

}
