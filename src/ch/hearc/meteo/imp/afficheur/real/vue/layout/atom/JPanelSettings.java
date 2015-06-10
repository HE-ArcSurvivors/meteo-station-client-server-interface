
package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom;

import java.awt.FlowLayout;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JPanelSettings extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelSettings(final AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
//		System.out.println("UPDATE_METEO_SERVICE_OPTION");
		int dtTemperature = (int)meteoServiceOptions.getTemperatureDT();
//		System.out.println("dtTemperature " + dtTemperature);
		sliderDeltaTemperature.setValue(dtTemperature);

		int dtAltitude = (int)meteoServiceOptions.getAltitudeDT();
//		System.out.println("dtAltitude " + dtAltitude);
		sliderDeltaAltitude.setValue(dtAltitude);

		int dtPression = (int)meteoServiceOptions.getPressionDT();
//		System.out.println("dtPression " + dtPression);
		sliderDeltaPression.setValue(dtPression);
		}

	public void setDelta(int dataType, int value)
		{
		MeteoServiceOptions meteoServiceOptions;
		try
			{
			meteoServiceOptions = afficheurServiceMOO.getMeteoServiceOptions();
			switch(dataType)
				{
				case DataType.TEMPERATURE:
					System.out.println("VALUE : "+value);
					meteoServiceOptions.setTemperatureDT(value);
					break;
				case DataType.ALTITUDE:
					meteoServiceOptions.setAltitudeDT(value);
					break;
				case DataType.PRESSION:
					meteoServiceOptions.setPressionDT(value);
					break;
				default:
					break;
				}
			}
		catch (RemoteException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
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

		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Paramètres");
		title.setTitleJustification(TitledBorder.RIGHT);
		this.setBorder(title);

		// JComponent : Instanciation
		sliderDeltaTemperature = new JPanelSliderLine(DataType.TEMPERATURE, 0, this);
		sliderDeltaAltitude = new JPanelSliderLine(DataType.ALTITUDE, 0, this);
		sliderDeltaPression = new JPanelSliderLine(DataType.PRESSION, 0, this);

			// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		Box boxV = Box.createVerticalBox();

		// JComponent : add
		boxV.add(sliderDeltaTemperature);
		boxV.add(sliderDeltaAltitude);
		boxV.add(sliderDeltaPression);

		add(boxV);

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
	private JPanelSliderLine sliderDeltaTemperature;
	private JPanelSliderLine sliderDeltaAltitude;
	private JPanelSliderLine sliderDeltaPression;

	private AfficheurServiceMOO afficheurServiceMOO;

	private final static int POOLING_DELAY = 500000;

	}
