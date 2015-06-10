
package ch.hearc.meteo.imp.afficheur.real.vue.layout;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.tab.JPanelTabCharts;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.tab.JPanelTabOverview;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JPanelStationMeteo extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelStationMeteo(AfficheurServiceMOO afficheurServiceMOO)
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
		jpaneltaboverview.update();
		jpaneltabchartTemperature.update();
		jpaneltabchartPression.update();
		}

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		jpaneltaboverview.updateMeteoServiceOptions(meteoServiceOptions);
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
		tabbedPane = new JTabbedPane();
		jpaneltaboverview = new JPanelTabOverview(afficheurServiceMOO);
		jpaneltabchartTemperature = new JPanelTabCharts(afficheurServiceMOO, DataType.TEMPERATURE);
		jpaneltabchartPression = new JPanelTabCharts(afficheurServiceMOO, DataType.PRESSION);

		tabbedPane.addTab("Vue générale", jpaneltaboverview);
		tabbedPane.addTab("Statistiques de la température", jpaneltabchartTemperature);
		tabbedPane.addTab("Statistiques de la pression", jpaneltabchartPression);

			// Layout : Specification
			{
			BorderLayout borderLayout = new BorderLayout();
			setLayout(borderLayout);
			}

		// JComponent : add
		add(tabbedPane, BorderLayout.CENTER);
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
	private JTabbedPane tabbedPane;
	private JPanelTabOverview jpaneltaboverview;
	private JPanelTabCharts jpaneltabchartTemperature;
	private JPanelTabCharts jpaneltabchartPression;

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	}
