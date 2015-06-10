package ch.hearc.meteo.imp.afficheur.real.vue.layout;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JFrame;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class JFrameStationMeteoCentral extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameStationMeteoCentral(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		jpanelstationmeteocentral.refresh();
		}

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		jpanelstationmeteocentral.updateMeteoServiceOptions(meteoServiceOptions);
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
			// JComponent : Instanciation
			jpanelstationmeteocentral = new JPanelStationMeteoCentral(afficheurServiceMOO);

			// Layout : Specification
			{
			BorderLayout borderLayout = new BorderLayout();
			setLayout(borderLayout);

			// borderLayout.setHgap(20);
			// borderLayout.setVgap(20);
			}

			// JComponent : add
			add(jpanelstationmeteocentral, BorderLayout.CENTER);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void appearance()
		{
		setSize(600, 400);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		}

	public void addStation(AffichageOptions afficheurOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		jpanelstationmeteocentral.addStation(afficheurOptions, meteoServiceRemote);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanelStationMeteoCentral jpanelstationmeteocentral;

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	}
