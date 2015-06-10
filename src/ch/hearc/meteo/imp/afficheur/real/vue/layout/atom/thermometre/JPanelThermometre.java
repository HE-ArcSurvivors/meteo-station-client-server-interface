
package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.thermometre;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;

public class JPanelThermometre extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelThermometre(Stat stat)
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
		jpanelfleche.setTemperature(temperature);
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
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), DataType.getString(DataType.TEMPERATURE));
		title.setTitleJustification(TitledBorder.RIGHT);
		this.setBorder(title);

		temperature = stat.getLast();

		// JComponent : Instanciation
		jpaneldegrade = new JPanelDegrade();
		jpanelvalues = new JPanelValues();
		jpanelfleche = new JPanelFleche(temperature);

		// Layout : Specification
		Box boxH = Box.createHorizontalBox();
		boxH.add(jpanelfleche);
		boxH.add(jpaneldegrade);
		boxH.add(jpanelvalues);

		FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
		setLayout(flowlayout);
		add(boxH);
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		setMinimumSize(new Dimension(100, 200));
		setPreferredSize(new Dimension(200, 340));
		setMaximumSize(new Dimension(300, 340));
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

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final int DEFAULT_MAX_VALUE = 40;
	public static final int DEFAULT_MIN_VALUE = -30;
	public static final int THERMOMETRE_DECALAGE = 10;

	}
