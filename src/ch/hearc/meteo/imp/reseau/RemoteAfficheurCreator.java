
package ch.hearc.meteo.imp.reseau;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ch.hearc.meteo.imp.afficheur.real.AfficheurFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory_I;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper_I;
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
public class RemoteAfficheurCreator implements RemoteAfficheurCreator_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private RemoteAfficheurCreator() throws RemoteException
		{
		server();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Remote use
	 */
	@Override public RmiURL createRemoteAfficheurService(AffichageOptions affichageOptions, RmiURL meteoServiceRmiURL) throws RemoteException
		{
		MeteoServiceWrapper_I meteoServiceRemote = null;
			// client
			{
			// connection to meteoService on PC-Local with meteoServiceRmiURL
			meteoServiceRemote = (MeteoServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(meteoServiceRmiURL);
			}

			// server
			{
			AfficheurService_I afficheurService = createAfficheurService(affichageOptions, meteoServiceRemote);

			// Building RmiURL
			 RmiURL afficheurServicermiURL = rmiUrl();
//			RmiURL afficheurServicermiURL = rmiUrl(PC_CENTRAL_IP, PREFIXE);

			// Share Object afficheurService
			AfficheurServiceWrapper_I afficheurServiceWrapper = new AfficheurServiceWrapper(afficheurService);
			RmiTools.shareObject(afficheurServiceWrapper, afficheurServicermiURL);

			return afficheurServicermiURL; // Retourner le RMI-ID pour une connection distante sur le serveur d'affichage
			}
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized RemoteAfficheurCreator_I getInstance() throws RemoteException
		{
		if (INSTANCE == null)
			{
			INSTANCE = new RemoteAfficheurCreator();
			}

		return INSTANCE;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurService_I createAfficheurService(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
	{
		//create AfficheurService
		AfficheurFactory_I afficheurFactory = new AfficheurFactory();
		AfficheurService_I afficheurService = afficheurFactory.createOnCentralPC(affichageOptions, meteoServiceRemote);
		afficheurServiceList.add(afficheurService);
		return afficheurService;
	}

	private void server() throws RemoteException
	{
		// TODO FICHIER CONFIG IP SERVEUR

		afficheurServiceList = new ArrayList<AfficheurService_I>();

		try
		{
			// Building RMI URL
			inetConvertedIPAddress = InetAddress.getByName(PC_CENTRAL_IP);
			RmiURL rmi_server_url = new RmiURL(PREFIXE, inetConvertedIPAddress, RMI_PORT);
			//Share
			RmiTools.shareObject(this, rmi_server_url);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl()
	{
		String id = IdTools.createID(PREFIXE);

		return new RmiURL(id);
//		return new RmiURL(id, RMI_PORT);
	}

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl(String ipAddress, String customPrefixe)
	{
		String id = IdTools.createID(customPrefixe);

		try
		{
			//Convert ip address to inetAddress
			InetAddress inetConvertedIPAddress = InetAddress.getByName(ipAddress);
			return new RmiURL(id, inetConvertedIPAddress, RMI_PORT);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			return null;
		}
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
	public static RmiURL RMI_URL = new RmiURL(RMI_ID,RMI_PORT);
	private static RemoteAfficheurCreator_I INSTANCE = null;
	private static String PC_CENTRAL_IP = LOCALHOST_IP;
	private static InetAddress inetConvertedIPAddress;


	}
