package ch.hearc.meteo.imp.afficheur.real.vue.layout;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.menu.JPanelMenu;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.tab.JPanelTabCharts;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.tab.JPanelTabOverview;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

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

			jpanelmenu = new JPanelMenu();

			tabbedPane.addTab("Vue générale",jpaneltaboverview);
			tabbedPane.addTab("Statistiques de la température",jpaneltabchartTemperature);
			tabbedPane.addTab("Statistiques de la pression",jpaneltabchartPression);

			// Layout : Specification
			{
			BorderLayout borderLayout = new BorderLayout();
			setLayout(borderLayout);
			}

			// JComponent : add
			add(jpanelmenu, BorderLayout.NORTH);
			add(tabbedPane, BorderLayout.CENTER);
		}

	private void control()
		{

		}

	private void appearance()
		{

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JTabbedPane tabbedPane;
	private JPanelTabOverview jpaneltaboverview;
	private JPanelTabCharts jpaneltabchartTemperature;
	private JPanelTabCharts jpaneltabchartPression;
	private JPanelMenu jpanelmenu;

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	}
