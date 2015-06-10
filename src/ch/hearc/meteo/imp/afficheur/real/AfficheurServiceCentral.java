
package ch.hearc.meteo.imp.afficheur.real;

import ch.hearc.meteo.imp.afficheur.real.vue.layout.JFrameStationMeteoCentral;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.JPanelStationMeteo;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class AfficheurServiceCentral implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * n = #data to print
	 */
	public AfficheurServiceCentral(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		afficheurServiceMOO = new AfficheurServiceMOO(affichageOptions, meteoServiceRemote);
		jframestationmeteocentral = new JFrameStationMeteoCentral(afficheurServiceMOO);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void printAltitude(MeteoEvent event)
		{
		System.err.println("OK OK OK OK");
		afficheurServiceMOO.printAltitude(event);
		jframestationmeteocentral.refresh();
		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		System.err.println("OK OK OK OK");
		afficheurServiceMOO.printTemperature(event);
		jframestationmeteocentral.refresh();
		}

	@Override
	public void printPression(MeteoEvent event)
		{
		System.err.println("OK OK OK OK");
		afficheurServiceMOO.printPression(event);
		jframestationmeteocentral.refresh();
		}

	@Override
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		jframestationmeteocentral.updateMeteoServiceOptions(meteoServiceOptions);
		}

	public void addStation(JPanelStationMeteo jpanel)
		{
		jframestationmeteocentral.addStation(jpanel);
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
	private JFrameStationMeteoCentral jframestationmeteocentral;
	}
