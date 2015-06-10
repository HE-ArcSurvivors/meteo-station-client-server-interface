
package ch.hearc.meteo.imp.afficheur.real.vue.layout.tab;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.real.vue.chart.Chart;
import ch.hearc.meteo.imp.afficheur.real.vue.chart.JPanelStats;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

public class JPanelTabCharts extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelTabCharts(AfficheurServiceMOO afficheurServiceMOO, int dataType)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;
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
		chart.update();
		jpanelstat.update();
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
		chart = new Chart(dataType, DataType.getList(dataType, afficheurServiceMOO));
		jpanelstat = new JPanelStats(dataType, DataType.getStat(dataType, afficheurServiceMOO));

		Box boxlayout = Box.createVerticalBox();
		boxlayout.add(chart);
		boxlayout.add(jpanelstat);

		FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
		setLayout(flowlayout);
		add(boxlayout);
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

	// Input
	private AfficheurServiceMOO afficheurServiceMOO;
	private int dataType;

	// Tools
	private Chart chart;
	private JPanelStats jpanelstat;

	}
