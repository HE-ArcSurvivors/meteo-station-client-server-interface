
package tools;

import javax.swing.ImageIcon;


/**
* Les images doivent se trouver dans un jar, et le jar dans le classpth!
* Le jar doit contenir le folder ressources. A l'interieur du folder ressource doit se trouver les images aux formats (jpg, voir mieux png pour la transparance)
*/
public class MagasinImage
	{

	/*------------------------------------------------------------------*\
	|*		 Version Synchrone (bloquant)								*|
	\*------------------------------------------------------------------*/

	public static final ImageIcon mountain = ImageLoader.loadSynchroneJar("images/montagne.png");
	public static final ImageIcon barometreBorder = ImageLoader.loadSynchroneJar("images/barometre.png");
	public static final ImageIcon barometreArrow = ImageLoader.loadSynchroneJar("images/flechebarometre.png");

	/*------------------------------------------------------------------*\
	|*		Version Assynchrone	(non bloquant)							*|
	\*------------------------------------------------------------------*/

	//public static final ImageIcon warning = ImageLoader.loadAsynchroneJar("./ressources/warning.png");
	//public static final ImageIcon coffee = ImageLoader.loadAsynchroneJar("./ressources/coffee_logo.png");
	//public static final ImageIcon cervin = ImageLoader.loadAsynchroneJar("ressources/cervin.jpg");

	}

