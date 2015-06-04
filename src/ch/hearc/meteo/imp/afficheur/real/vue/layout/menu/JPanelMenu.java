
package ch.hearc.meteo.imp.afficheur.real.vue.layout.menu;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class JPanelMenu extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelMenu()
		{
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
		buttonStart = new JButton("Start");

		buttonStop = new JButton("Stop");
		buttonStop.setVisible(false);
		buttonStop.setEnabled(false);

		buttonSettings = new JButton("Settings");

		jdialogsettings = new JDialogSettings(null);
		jdialogsettings.pack();

			// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.LEFT);
			setLayout(flowlayout);
			add(buttonStart);
			add(buttonStop);
			add(buttonSettings);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add

		}

	private void control()
		{

		ActionListener actionListenerStartStop = new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					startStop(buttonStart.isEnabled());
					}
			};

		buttonStart.addActionListener(actionListenerStartStop);
		buttonStop.addActionListener(actionListenerStartStop);

		buttonSettings.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					jdialogsettings.setLocationRelativeTo(null);
					jdialogsettings.setVisible(true);
					}
			});
		}

	private void startStop(boolean state)
		{
		buttonStart.setVisible(!state);
		buttonStart.setEnabled(!state);

		buttonStop.setVisible(state);
		buttonStop.setEnabled(state);

		if (state)
			{
			System.out.println("Start");
			}
		else
			{
			System.out.println("Stop");
			}
		}

	private void appearance()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JButton buttonSettings;
	private JButton buttonStart;
	private JButton buttonStop;

	private JDialogSettings jdialogsettings;

	}
