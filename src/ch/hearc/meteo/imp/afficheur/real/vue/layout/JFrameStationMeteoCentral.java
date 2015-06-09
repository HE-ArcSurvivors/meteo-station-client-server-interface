package ch.hearc.meteo.imp.afficheur.real.vue.layout;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import ch.hearc.meteo.imp.afficheur.real.AfficheurService;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

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
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		}

	public void addStation(AfficheurService afficheurService)
		{
//		jpanelstationmeteocentral.addStation(afficheurService);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanelStationMeteoCentral jpanelstationmeteocentral;

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	}
