
package ch.hearc.meteo.imp.com.real.port;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;
import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;

public class MeteoPortDetectionService implements MeteoPortDetectionService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoPortDetectionService(ComOption comOption)
		{
		this.comOption = comOption;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Retourne la liste de tous les ports séries
	 */
	@Override
	public List<String> findListPortSerie()
		{
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();

		List<String> listSerial = new LinkedList<String>();
		while(portEnum.hasMoreElements())
			{
			CommPortIdentifier portIdentifier = portEnum.nextElement();

			if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL)
				{
				listSerial.add(portIdentifier.getName());
				}
			}
		return listSerial;
		}

	/**
	 * Implementation conseil:
	 * 		(C1) Catcher l'ouverture du port qui peut echouer si le port est deja ouvert, et renvoyer false (le port est deja utilis�)
	 * 		(C2) Envoyer une trame, attendre une r�ponse en tenant compte d'un timeout
	 *
	 * Output:
	 * 		Return true si station meteo est connect� au portName
	 * 		Return false si
	 * 				(1) pas sation meteo connect� au PortName
	 * 				TODO : (2) StationMeteo connecte au PortName mais deja en utilisation (impossible d'ouvrir le port alors)
	 */
	@Override
	public boolean isStationMeteoAvailable(String portName, long timeoutMS)
		{
		try
			{
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);

			if (portId.isCurrentlyOwned())
				{
				System.out.println("Error: Port " + portName + " is currently in use");
				return false;
				}
			else
				{
				//Open port
				SerialPort port;

				port = (SerialPort)portId.open(portName, comOption.getSpeed());
				comOption.applyTo(port);
				port.notifyOnDataAvailable(true);

				//write
				OutputStream outputStream = port.getOutputStream();
				//read
				reader = new BufferedReader(new InputStreamReader(port.getInputStream()));

				//I'm Listening
				valueReceived = false;
				port.addEventListener(new SerialPortEventListener()
					{
						@Override
						public void serialEvent(SerialPortEvent event)
							{
							switch(event.getEventType())
								{
								case SerialPortEvent.DATA_AVAILABLE:
									//I HEARD YOU ! \o/
									valueReceived = true;
									break;
								}
							}
					});

				//do you hear me ?
				byte[] tabByte = TrameEncoder.coder("010200");
				outputStream.write(tabByte);

				Thread.sleep(timeoutMS); //timeOut

				//Close all shits
				port.removeEventListener();
				port.close();
				reader.close();
				outputStream.close();

				return valueReceived;
				}
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			return false;
			}
		}

	/**
	 * Contraintes :
	 * 		(C1) Doit refermer les ports!
	 * 		(C2) Doit �tre safe (dans le sens ou un port com peut contenir un hardware sensible qui ne doit imp�rativement pas �tre d�ranger, ie aucune tentative d'ouverture de port autoris�e)
	 *
	 * Implementation conseil:
	 * 		(I1) Utiliser la m�thode  isStationMeteoAvailable(String portName)
	 * 		(I2) Pour satisfaire la contrainte C2
	 * 				Step1 : Utiliser findPortSerie (ci-dessus)																						---> listPortCom
	 * 				Step2 :	Soustraction de listPortExcluded � listPortCom	(via removeAll)															---> listPortCom (updater)
	 * 				Step3 : Instancien listPortComMeteoAvailable																					---> listPortComMeteoAvailable	(vide)
	 * 				Step4 :	Parcourir listPortCom et utiliser isStationMeteoAvailable (ci-dessous) pour peupler listPortComMeteoAvailable			---> listPortComMeteoAvailable
	 *
	 *  Output:
	 *  	Return la liste des ports surlesquels sont branch�s une station m�t�o (non encore utilis�e) , except listPortExcluded
	 */
	@Override
	public List<String> findListPortMeteo(List<String> listPortExcluded)
		{
		List<String> listPortCom = findListPortSerie();
		listPortCom.removeAll(listPortExcluded);

		List<String> listPortComMeteoAvailable = new LinkedList<String>();

		for(String portCom:listPortCom)
			{
			if(isStationMeteoAvailable(portCom, 100))
				{
				listPortComMeteoAvailable.add(portCom);
				}
			}

		return listPortComMeteoAvailable;
		}


	/**
	 * Implementation conseil:
	 * 		(C1) Utiliser la m�thode
	 * 						findListPortMeteo(List<String> listPortExcluded)
	 * 			 avec une listPortExcluded qui existe (Instancier) mais de taille 0
	 */
	@Override
	public List<String> findListPortMeteo()
		{
		return findListPortMeteo(new LinkedList<String>());
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

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	//in
	private ComOption comOption;

	//tools
	private BufferedReader reader;
	private boolean valueReceived;

	}
