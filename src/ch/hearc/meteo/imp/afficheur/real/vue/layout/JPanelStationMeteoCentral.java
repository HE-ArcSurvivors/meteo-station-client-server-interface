
package ch.hearc.meteo.imp.afficheur.real.vue.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;

public class JPanelStationMeteoCentral extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelStationMeteoCentral(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

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
		mapStation = new HashMap<String, JPanelStationMeteo>();

		jlistStation = new JList<Object>(mapStation.keySet().toArray());
		jlistStation.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jlistStation.setLayoutOrientation(JList.VERTICAL);
		jlistStation.setVisibleRowCount(-1);
		jlistStation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlistStation.addListSelectionListener(new ListSelectionListener()
			{
				@Override
				public void valueChanged(ListSelectionEvent le)
					{
					int idx = jlistStation.getSelectedIndex();
					if (idx != -1)
						{
						Object[] tab = mapStation.keySet().toArray();
						add(mapStation.get(tab[idx]), BorderLayout.CENTER);
						}
					}
			});

		JScrollPane listScroller = new JScrollPane(jlistStation);
		listScroller.setPreferredSize(new Dimension(250, 80));
			// Layout : Specification
			{
			BorderLayout layout = new BorderLayout();
			setLayout(layout);

			add(listScroller, BorderLayout.WEST);
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

	public void refresh()
		{
		jlistStation.updateUI();
		}

	public void addStation(AfficheurServiceMOO afficheurServiceMOO)
		{
		System.err.println("OK");
		mapStation.put("STATION",new JPanelStationMeteo(afficheurServiceMOO));
		updateUI();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JList<Object> jlistStation;
	private Map<String, JPanelStationMeteo> mapStation;

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	}
