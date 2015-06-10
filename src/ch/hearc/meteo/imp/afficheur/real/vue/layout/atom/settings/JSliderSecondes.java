
package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.settings;

import javax.swing.JSlider;

public class JSliderSecondes extends JSlider
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JSliderSecondes()
		{
		super();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public String getStringValue()
		{
		String stringValue;

		if (getValue() / 1000 <= 0)
			{
			stringValue = "< 0 secondes";
			}
		else if((getValue() / 1000) % 60 == 0)
			{
			stringValue = (getValue() / 1000) / 60 + " minute(s)";
			}
		else
			{
			stringValue = getValue() / 1000 + " seconde(s)";
			}
		return stringValue;
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
	}
