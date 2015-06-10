
package ch.hearc.meteo.imp.afficheur.real;

import ch.hearc.meteo.imp.afficheur.real.vue.layout.JFrameStationMeteo;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class AfficheurService implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * n = #data to print
	 */
	public AfficheurService(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		afficheurServiceMOO = new AfficheurServiceMOO(affichageOptions, meteoServiceRemote);
		jframestationmeteo = new JFrameStationMeteo(afficheurServiceMOO);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void printAltitude(MeteoEvent event)
		{
		afficheurServiceMOO.printAltitude(event);
		jframestationmeteo.refresh();
		}

	@Override public void printTemperature(MeteoEvent event)
		{
		afficheurServiceMOO.printTemperature(event);
		jframestationmeteo.refresh();
		}

	@Override public void printPression(MeteoEvent event)
		{
		afficheurServiceMOO.printPression(event);
		jframestationmeteo.refresh();
		}

	@Override public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		System.out.println("UPDATE_METEO_SERVICE_OTPIONS_AFFICHEUR_SERVICE");
		jframestationmeteo.updateMeteoServiceOptions(meteoServiceOptions);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	//Tools
	private JFrameStationMeteo jframestationmeteo;
	}
