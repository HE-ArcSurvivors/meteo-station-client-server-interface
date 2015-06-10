
package ch.hearc.meteo.imp.afficheur.real;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory_I;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

/**
 * On pourrait aussi faire un singleton
 */
public class AfficheurFactory implements AfficheurFactory_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AfficheurFactory()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public AfficheurService_I createOnLocalPC(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		return new AfficheurService(affichageOptions, meteoServiceRemote);
		}

	@Override
	public AfficheurService_I createOnCentralPC(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		if (afficheurCentral == null)
			{
			afficheurCentral = new AfficheurServiceCentral(affichageOptions, meteoServiceRemote);
			return afficheurCentral;
			}
		else
			{
			System.out.println("TITLE : "+affichageOptions.getTitre());
			AfficheurService afficheurLocal = new AfficheurService(affichageOptions, meteoServiceRemote);
			afficheurCentral.addStation(affichageOptions.getTitre(), afficheurLocal);
			return afficheurLocal;
			}

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private static AfficheurServiceCentral afficheurCentral;
	}
