package ch.hearc.meteo.imp.afficheur.real.vue.layout.tab;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.altitude.JPanelAltitude;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.barometre.JPanelBarometre;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.thermometre.JPanelThermometre;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.menu.JPanelMenu;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

public class JPanelTabOverview extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelTabOverview(AfficheurServiceMOO afficheurServiceMOO)
		{
		jpanelthermometre = new JPanelThermometre(afficheurServiceMOO.getStatTemperature(), afficheurServiceMOO.getListTemperature());
		jpanelaltitude = new JPanelAltitude(afficheurServiceMOO.getStatAltitude(), afficheurServiceMOO.getListAltitude());
		jpanelbarometre = new JPanelBarometre(afficheurServiceMOO.getStatPression(), afficheurServiceMOO.getListPression());
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*						Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		jpanelthermometre.update();
		jpanelaltitude.update();
		jpanelbarometre.update();
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
			jpanelmenu = new JPanelMenu();
			// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);
			add(jpanelthermometre);
			add(jpanelaltitude);
			add(jpanelbarometre);
			add(jpanelmenu);
			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add

		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanelThermometre jpanelthermometre;
	private JPanelAltitude jpanelaltitude;
	private JPanelBarometre jpanelbarometre;

	private JPanelMenu jpanelmenu;

	}
