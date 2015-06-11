
package ch.hearc.meteo.imp.afficheur;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ch.hearc.meteo.imp.com.real.MeteoFactory;
import ch.hearc.meteo.imp.com.real.port.MeteoPortDetectionServiceFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.imp.use.remote.PropertiesSingleton;
import ch.hearc.meteo.imp.use.remote.pclocal.PCLocal;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class JFrameSelectionPortCom extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameSelectionPortCom()
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

	private void refresh()
		{
		listButtonCom.clear();
		box.removeAll();

		List<String> listPortsMeteo = new MeteoPortDetectionServiceFactory().create().findListPortMeteo();

		box.add(labelTitle);
		box.add(Box.createVerticalStrut(5));

		for(final String element:listPortsMeteo)
			{
			JButton button = new JButton(element);

			button.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
						{
						System.out.println("LAUNCH STATION ELEMENT"+element);
						launchStation(element);
						refresh();
						}
				});

			button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

			listButtonCom.add(button);
			box.add(button);
			box.add(Box.createVerticalStrut(5));
			}

		if (listPortsMeteo.size() <= 0)
			{
			box.add(labelEmpty);
			box.add(Box.createVerticalStrut(5));
			}

		box.add(refreshButton);
		box.add(Box.createVerticalStrut(5));
		box.add(simulationButton);
		setSize(2 * BUTTON_WIDTH, 3 * (listButtonCom.size() + 2) * BUTTON_HEIGHT);
		}

	private void geometry()
		{
		listButtonCom = new ArrayList<JButton>();
		refreshButton = new JButton("Recharger");
		refreshButton.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		refreshButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		refreshButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		simulationButton = new JButton("Simulation");
		simulationButton.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		simulationButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		simulationButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

		labelTitle = new JLabel("Choisissez votre port : ");
		labelEmpty = new JLabel("Aucun port disponible");

		box = Box.createVerticalBox();
		refresh();

		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		add(box);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		refreshButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
					{
					refresh();
					}
			});

		simulationButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
					{
					try
						{
						launchSimulation();
						}
					catch (MeteoServiceException e1)
						{
						System.err.println("MeteoServiceException in launchSimulation()");
						e1.printStackTrace();
						}
					}
			});
		}

	private void appearance()
		{
		setSize(2 * BUTTON_WIDTH, 3 * (listButtonCom.size() + 2) * BUTTON_HEIGHT);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		}

	private void launchStation(String portcom)
		{
		try
			{
			int dataToPrint = 3;

			MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
			String ipServer = PropertiesSingleton.getInstance().getIpServer();
			InetAddress inetIpAddress = InetAddress.getByName(ipServer);
			RmiURL rmiUrl = new RmiURL(RemoteAfficheurCreator.RMI_ID, inetIpAddress,
					RemoteAfficheurCreator.RMI_PORT);
			PCLocal pc = new PCLocal(meteoServiceOptions,portcom,null,rmiUrl);
			
			pc.run();

//			this.setVisible(false);
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}

	private void launchSimulation() throws MeteoServiceException
		{
		launchStation("SIMULATEUR");
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private List<JButton> listButtonCom;
	private JButton refreshButton;
	private JButton simulationButton;

	private JLabel labelTitle;
	private JLabel labelEmpty;

	private Box box;

	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 25;

	}
