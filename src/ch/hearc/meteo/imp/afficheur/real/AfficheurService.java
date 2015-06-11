
package ch.hearc.meteo.imp.afficheur.real;

import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.real.vue.layout.JFrameStationMeteo;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.JPanelStationMeteo;
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
		connected = true;
		jframestationmeteo.setConnected(connected);

		Thread threadPoolingOptions = new Thread(new Runnable()
			{
				@Override
				public void run()
					{
					while(true)
						{
						MeteoServiceOptions option;
						try
							{
							option = afficheurServiceMOO.getMeteoServiceOptions();
							updateMeteoServiceOptions(option);
							}
						catch (RemoteException e)
							{
							System.out.println("I LOST THE LOCAL PC, I AM SO SAD");
							connected = false;
							jframestationmeteo.setConnected(connected);
							// TODO ADD A RED ALERT ?
//							e.printStackTrace();
							}

						attendre(1000); //disons
						}
					}
			});

		threadPoolingOptions.start(); // update gui

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void printAltitude(MeteoEvent event)
		{
		afficheurServiceMOO.printAltitude(event);
		jframestationmeteo.refresh();
		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		afficheurServiceMOO.printTemperature(event);
		jframestationmeteo.refresh();
		}

	@Override
	public void printPression(MeteoEvent event)
		{
		afficheurServiceMOO.printPression(event);
		jframestationmeteo.refresh();
		}

	@Override
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		jframestationmeteo.updateMeteoServiceOptions(meteoServiceOptions);
		}

	public JPanelStationMeteo getPanelStationMeteo()
		{
		this.jframestationmeteo.setVisible(false);
		return jframestationmeteo.getPanelStationMeteo();
		}

	public boolean checkConnected()
	{
	return connected;
	}


	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private static void attendre(long delay)
		{
		try
			{
			Thread.sleep(delay);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	//Tools
	private JFrameStationMeteo jframestationmeteo;

	private boolean connected;
	}
