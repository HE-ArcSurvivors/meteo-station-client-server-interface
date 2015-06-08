
package ch.hearc.meteo.imp.afficheur.real.vue.layout.tab;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.JPanelSliderLine;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JPanelTabSettings extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelTabSettings()
		{
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{

		if (this.meteoServiceOptions == null)
			{
			this.meteoServiceOptions = meteoServiceOptions;
			}

		System.out.println("LAST UPDATE METEO SERVICE OPTIONS");
		int dtTemperature = (int)meteoServiceOptions.getTemperatureDT();
		System.out.println("dtTemperature " + dtTemperature);
		sliderDeltaTemperature.setValue(dtTemperature);

		int dtAltitude = (int)meteoServiceOptions.getAltitudeDT();
		System.out.println("dtAltitude " + dtAltitude);
		sliderDeltaAltitude.setValue(dtAltitude);

		int dtPression = (int)meteoServiceOptions.getPressionDT();
		System.out.println("dtPression " + dtPression);
		sliderDeltaPression.setValue(dtPression);
		}

	public void setDelta(int dataType, int value)
		{
		if (meteoServiceOptions != null)
			{
			switch(dataType)
				{
				case DataType.TEMPERATURE:
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
				"Modifier les dt");
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
		boxV.add(Box.createVerticalGlue());
		boxV.add(sliderDeltaTemperature);
		boxV.add(Box.createVerticalGlue());
		boxV.add(sliderDeltaAltitude);
		boxV.add(Box.createVerticalGlue());
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

	private MeteoServiceOptions meteoServiceOptions;

	}
