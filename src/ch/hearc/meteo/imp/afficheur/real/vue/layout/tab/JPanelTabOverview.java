
package ch.hearc.meteo.imp.afficheur.real.vue.layout.tab;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.JPanelSettings;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.altitude.JPanelAltitude;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.barometre.JPanelBarometre;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.thermometre.JPanelThermometre;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

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
		jpanelsettings = new JPanelSettings(afficheurServiceMOO);

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

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		jpanelsettings.updateMeteoServiceOptions(meteoServiceOptions);
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
		// Layout : Specification
		FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
		setLayout(flowlayout);

		Box boxH = Box.createHorizontalBox();
		boxH.add(jpanelthermometre);
		boxH.add(jpanelaltitude);
		boxH.add(jpanelbarometre);

		Box boxV = Box.createVerticalBox();
		boxV.add(boxH);
		boxV.add(jpanelsettings);

		// JComponent : add
		add(boxV);

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
	private JPanelSettings jpanelsettings;

	}
