
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

import ch.hearc.meteo.imp.afficheur.real.AfficheurFactory;
import ch.hearc.meteo.imp.com.real.MeteoFactory;
import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.imp.com.real.port.MeteoPortDetectionService;
import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.imp.use.remote.pclocal.PCLocal;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoAdapter;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

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

		List<String> listPortsMeteo = new MeteoPortDetectionService(new ComOption()).findListPortMeteo();

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
		// JComponent : Instanciation
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

		// Layout : Specification

		box = Box.createVerticalBox();
		refresh();

		// borderLayout.setHgap(20);
		// borderLayout.setVgap(20);

		// JComponent : add

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
						// TODO Auto-generated catch block
						e1.printStackTrace();
						}
					}
			});
		}

	//TODO remove
	private static void threadTest(final MeteoService_I meteoService, final AfficheurService_I afficheurService)
		{
//		// Modify MeteoServiceOptions
//		Thread threadSimulationChangementDt = new Thread(new Runnable()
//			{
//				@Override
//				public void run()
//					{
//					double x = 0;
//					double dx = Math.PI / 10;
//
//					while(true)
//						{
//						long dt = 1000 + (long)(5000 * Math.abs(Math.cos(x))); //ms
//
//						System.out.println("modification dt temperature = " + dt);
//
//						meteoService.getMeteoServiceOptions().setTemperatureDT(dt);
//
//						//	System.out.println(meteoService.getMeteoServiceOptions());
//
//						attendre(3000); // disons
//						x += dx;
//						}
//					}
//			});

		// Update GUI MeteoServiceOptions
		Thread threadPoolingOptions = new Thread(new Runnable()
			{

				@Override
				public void run()
					{

					while(true)
						{
						MeteoServiceOptions option = meteoService.getMeteoServiceOptions();
						afficheurService.updateMeteoServiceOptions(option);

						//System.out.println(option);

						attendre(1000); //disons
						}
					}
			});

//		threadSimulationChangementDt.start();
		threadPoolingOptions.start(); // update gui
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

			//Making simulator data
			MeteoService_I meteoService = (new MeteoFactory()).create(portcom);
			MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);

			//Get Properties from config file
			FileInputStream fis = new FileInputStream(FILE_NAME);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Properties property = new Properties();
			property.load(bis);
			String ipAddress = property.getProperty(IP_ADDRESS);
			InetAddress inetIpAddress = InetAddress.getByName(ipAddress);
			String moduleName = property.getProperty(MODULE_NAME);
			bis.close();
			fis.close();

			RmiURL rmiUrl = new RmiURL(RemoteAfficheurCreator.RMI_ID, inetIpAddress, RemoteAfficheurCreator.RMI_PORT);

			String moduleTitle = moduleName + ": " + RmiTools.getLocalHost() + " " + meteoService.getPort();

			AffichageOptions affichageOptions = new AffichageOptions(dataToPrint, moduleTitle);

			PCLocal pc = new PCLocal(meteoServiceOptions, portcom, affichageOptions, rmiUrl);
			pc.run();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}

		}

	private void launchSimulation() throws MeteoServiceException
		{
		String portName = "COM1";
		MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create(portName);
		use(meteoService);
		}

	public static void use(MeteoService_I meteoService) throws MeteoServiceException
		{
		// Service Meteo
		meteoService.connect();
		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
		meteoService.start(meteoServiceOptions);

		// Service Affichage
		MeteoServiceWrapper_I meteoServiceWrapper = new MeteoServiceWrapper(meteoService);

		String titre = RmiTools.getLocalHost() + " " + meteoService.getPort();
		AffichageOptions affichageOption = new AffichageOptions(3, titre);
		AfficheurService_I afficheurService = new AfficheurFactory().createOnLocalPC(affichageOption, meteoServiceWrapper);

		threadTest(meteoService, afficheurService);
		use(meteoService, afficheurService);
		}

	/**
	 * Liason entre les deux services d'affichage : MeteoService_I et AfficheurService_I
	 */
	public static void use(final MeteoService_I meteoService, final AfficheurService_I afficheurService) throws MeteoServiceException
		{
		meteoService.addMeteoListener(new MeteoAdapter()
			{

				@Override
				public void temperaturePerformed(MeteoEvent event)
					{
					afficheurService.printTemperature(event);
					}

				@Override
				public void altitudePerformed(MeteoEvent event)
					{
					afficheurService.printAltitude(event);
					}

				@Override
				public void pressionPerformed(MeteoEvent event)
					{
					afficheurService.printPression(event);
					}

			});
		}

	private static void attendre(long delay)
		{
		try
			{
			Thread.sleep(delay);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
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

	// Static tools
	private static final String IP_ADDRESS = "IP_ADDRESS";
	private static final String MODULE_NAME = "MODULE_NAME";
	private static final String FILE_NAME = "./settings.properties";
	//	private static final String FILE_NAME = "/Users/Rocla/Clouds/OneDrive/HE-Arc/Java/Meteo/settings.properties";
	//	private static final String FILE_NAME = "/home/timetraveler/Desktop/settings.properties";

	}
