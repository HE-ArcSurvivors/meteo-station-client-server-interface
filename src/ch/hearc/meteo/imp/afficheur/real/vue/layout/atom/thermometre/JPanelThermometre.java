package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.thermometre;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelThermometre extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelThermometre(Stat stat, List<MeteoEvent> listMeteoEvent)
		{
		this.stat = stat;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		temperature = stat.getLast();

		jpaneldegrade.setTemperature(temperature);
		jpanelfleche.setTemperature(temperature);
		jpanelvalues.setTemperature(temperature);
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
			temperature = stat.getLast();

			// JComponent : Instanciation
			jpaneldegrade = new JPanelDegrade(temperature);
			jpanelvalues = new JPanelValues(temperature);
			jpanelfleche = new JPanelFleche(temperature);

			// Layout : Specification
			Box boxH = Box.createHorizontalBox();
			boxH.add(jpanelfleche);
			boxH.add(jpaneldegrade);
			boxH.add(jpanelvalues);

			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// JComponent : add
			add(boxH);
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		setMinimumSize(new Dimension(100,200));
		setPreferredSize(new Dimension(200,400));
		setMaximumSize(new Dimension(300,600));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanelDegrade jpaneldegrade;
	private JPanelValues jpanelvalues;
	private JPanelFleche jpanelfleche;

	private float temperature;

	//Input
	private Stat stat;

	public static final int DEFAULT_MAX_VALUE = 40;
	public static final int DEFAULT_MIN_VALUE = -30;

	}
