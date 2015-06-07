
package ch.hearc.meteo.imp.afficheur.real.vue;

import java.util.List;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class DataType
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static String getString(int type)
		{
		switch(type)
			{
			case TEMPERATURE:
				return "Température";
			case ALTITUDE:
				return "Altitude";
			case PRESSION:
				return "Pression";
			default:
				return "";
			}
		}

	public static String getLegend(int type)
		{
		switch(type)
			{
			case TEMPERATURE:
				return getString(type) + " [°C]";
			case ALTITUDE:
				return getString(type) + " [m]";
			case PRESSION:
				return getString(type) + " [mB]";
			default:
				return "";
			}
		}

	public static List<MeteoEvent> getList(int type, AfficheurServiceMOO afficheurServiceMOO)
	{
		switch(type)
		{
		case TEMPERATURE:
			return afficheurServiceMOO.getListTemperature();
		case ALTITUDE:
			return afficheurServiceMOO.getListAltitude();
		case PRESSION:
			return afficheurServiceMOO.getListPression();
		default:
			return null;
		}

	}

	public static Stat getStat(int type, AfficheurServiceMOO afficheurServiceMOO)
	{
		switch(type)
		{
		case TEMPERATURE:
			return afficheurServiceMOO.getStatTemperature();
		case ALTITUDE:
			return afficheurServiceMOO.getStatAltitude();
		case PRESSION:
			return afficheurServiceMOO.getStatPression();
		default:
			return null;
		}
	}

	/*------------------------------------------------------------------*\
	|*								Attributs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final int TEMPERATURE = 0;
	public static final int ALTITUDE = 1;
	public static final int PRESSION = 2;

	}
