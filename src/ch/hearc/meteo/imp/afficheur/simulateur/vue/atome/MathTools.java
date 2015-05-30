
package ch.hearc.meteo.imp.afficheur.simulateur.vue.atome;

import java.text.DecimalFormat;

public class MathTools
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static String arrondir(float value)
		{
		int valueINT = (int)(value * 100);
		float arrondi = valueINT / (float)100;

		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(arrondi);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
