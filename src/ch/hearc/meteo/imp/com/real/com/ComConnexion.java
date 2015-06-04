
package ch.hearc.meteo.imp.com.real.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;
import ch.hearc.meteo.imp.com.real.com.trame.TrameDecoder;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEventType_E;

// TODO student
//  Impl�menter cette classe
//  Updater l'impl�mentation de MeteoServiceFactory.create()

/**
 * <pre>
 * Aucune connaissance des autres aspects du projet ici
 *
 * Ouvrer les flux vers le port com
 * ecouter les trames arrivantes (pas boucle, mais listener)
 * decoder trame
 * avertir meteoServiceCallback
 *
 *</pre>
 */
public class ComConnexion implements ComConnexions_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ComConnexion(MeteoServiceCallback_I meteoServiceCallback, String portName, ComOption comOption)
		{
		this.comOption = comOption;
		this.portName = portName;
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/**
	 * <pre>
	 * Problem :
	 * 		MeteoService est un MeteoServiceCallback_I
	 * 		ComConnexions_I utilise MeteoServiceCallback_I
	 * 		MeteoService utilise ComConnexions_I
	 *
	 * On est dans la situation
	 * 		A(B)
	 * 		B(A)
	 *
	 * Solution
	 * 		 B
	 * 		 A(B)
	 *  	 B.setA(A)
	 *
	 *  Autrement dit:
	 *
	 *		ComConnexions_I comConnexion=new ComConnexion( portName,  comOption);
	 *      MeteoService_I meteoService=new MeteoService(comConnexion);
	 *      comConnexion.setMeteoServiceCallback(meteoService);
	 *
	 *      Ce travail doit se faire dans la factory
	 *
	 *  Warning : call next
	 *  	setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
	 *
	 *  </pre>
	 */
	public ComConnexion(String portName, ComOption comOption)
		{
		this(null, portName, comOption);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void start() throws Exception
		{
		System.out.println("Start Listener");
		port.addEventListener(new SerialPortEventListener()
			{
				@Override
				public void serialEvent(SerialPortEvent event)
					{
					switch(event.getEventType())
						{
						case SerialPortEvent.DATA_AVAILABLE:
							try
								{
								traiterDonnees();
								}
							catch (Exception e)
								{
								e.printStackTrace();
								}
							break;
						}

					}
			});
		}

	public void traiterDonnees() throws Exception
		{
		String line = reader.readLine();
		float valeur = TrameDecoder.valeur(line);

		MeteoEventType_E type = TrameDecoder.dataType(line);
		switch(type)
			{
			case TEMPERATURE:
				meteoServiceCallback.temperaturePerformed(valeur);
				//System.out.println("---Température : " + valeur);
				break;
			case ALTITUDE:
				meteoServiceCallback.altitudePerformed(valeur);
				//System.out.println("---Altitude : " + valeur);
				break;
			case PRESSION:
				meteoServiceCallback.pressionPerformed(valeur);
				//System.out.println("---Pression : " + valeur);
				break;
			default:
				break;
			}
		//meteoServiceCallback.pressionPerformed(valeur);
		}

	@Override
	public void stop() throws Exception
		{
		port.removeEventListener();
		}

	@Override
	public void askAltitudeAsync() throws Exception
		{
		//System.out.println("---AskAltitude to : " + portName);

		byte[] tabByte = TrameEncoder.coder(ASK_MSG_ALTITUDE);
		outputStream.write(tabByte);
		}

	@Override
	public void askPressionAsync() throws Exception
		{
		//System.out.println("---AskPression to : " + portName);

		byte[] tabByte = TrameEncoder.coder(ASK_MSG_PRESSION);
		outputStream.write(tabByte);
		}

	@Override
	public void askTemperatureAsync() throws Exception
		{
		//System.out.println("---AskTemp to : " + portName);

		byte[] tabByte = TrameEncoder.coder(ASK_MSG_TEMPERATURE);
		outputStream.write(tabByte);
		}

	/**
	 * ouvre la connexion a l'attribut "port" de la classe et l'outputstream
	 * @return : retourne un OutputStream pour écrire sur le port
	 * @throws Exception : lance une exception si le port est déjà en utilisation
	 */
	@Override
	public void connect() throws Exception
		{
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
		if (portId.isCurrentlyOwned())
			{
			throw new Exception("Error: Port " + portName + " is currently in use");
			}
		else
			{
			//Open port
			port = (SerialPort)portId.open(portName, comOption.getSpeed());

			//params port
			//v1
			//			int baudRates = 57600;
			//			port.setSerialPortParams(baudRates, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			//			port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

			//v2
			comOption.applyTo(port);
			port.notifyOnDataAvailable(true);

			//write
			outputStream = port.getOutputStream();

			//read
			reader = new BufferedReader(new InputStreamReader(port.getInputStream()));
			}
		}

	/**
	 * ferme la connexion au "port" et ferme l'OutputStream
	 * @throws IOException
	 */
	@Override
	public void disconnect() throws Exception
		{
		reader.close();
		outputStream.close();
		port.close();
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override
	public String getNamePort()
		{
		return portName;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * For post building
	 */
	public void setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
		{
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private ComOption comOption;
	private String portName;
	private MeteoServiceCallback_I meteoServiceCallback;

	// Tools
	private SerialPort port;
	private OutputStream outputStream;
	private BufferedReader reader;

	private static String ASK_MSG_TEMPERATURE = "010100";
	private static String ASK_MSG_PRESSION = "010000";
	private static String ASK_MSG_ALTITUDE = "010200";



	}
