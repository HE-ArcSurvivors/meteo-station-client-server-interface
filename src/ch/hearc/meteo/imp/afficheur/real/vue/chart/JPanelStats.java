
package ch.hearc.meteo.imp.afficheur.real.vue.chart;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.MathTools;

public class JPanelStats extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelStats(int dataType, Stat stat)
		{
		this.stat = stat;
		this.dataType = dataType;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		labelCurrent.setText(DataType.getString(dataType)+" Actuelle : " + MathTools.arrondir(stat.getLast()));
		labelMin.setText(DataType.getString(dataType)+" Minimum : " + MathTools.arrondir(stat.getMin()));
		labelMax.setText(DataType.getString(dataType)+" Maximum :  " + MathTools.arrondir(stat.getMax()));
		labelMoy.setText(DataType.getString(dataType)+" Moyenne :  " + MathTools.arrondir(stat.getMoy()));
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

		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Statistiques");
		title.setTitleJustification(TitledBorder.RIGHT);
		this.setBorder(title);


		labelCurrent = new JLabel(DataType.getString(dataType)+" Actuelle : -");
		labelMin = new JLabel(DataType.getString(dataType)+" Minimum : -");
		labelMax = new JLabel(DataType.getString(dataType)+" Maximum : -");
		labelMoy = new JLabel(DataType.getString(dataType)+" Moyenne : -");

		Box boxLayout = Box.createVerticalBox();
		boxLayout.add(labelCurrent);
		boxLayout.add(labelMin);
		boxLayout.add(labelMax);
		boxLayout.add(labelMoy);

		FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
		setLayout(flowlayout);
		add(boxLayout);
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
	private JLabel labelCurrent;
	private JLabel labelMin;
	private JLabel labelMax;
	private JLabel labelMoy;

	// Input
	private Stat stat;
	private int dataType;

	}
