package ch.hearc.meteo.imp.afficheur.real.vue.layout;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.tab.JPanelTabCharts;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.tab.JPanelTabOverview;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

public class JFrameStationMeteo extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameStationMeteo(AfficheurServiceMOO afficheurServiceMOO)
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

			tabbedPane.addTab("Vue générale",jpaneltaboverview);
			tabbedPane.addTab("Statistiques de la température",jpaneltabchartTemperature);
			tabbedPane.addTab("Statistiques de la pression",jpaneltabchartPression);

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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void appearance()
		{
		setSize(800, 600);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
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
