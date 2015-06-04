package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.altitude;

import java.awt.FlowLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.MathTools;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelAltitude extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelAltitude(Stat stat, List<MeteoEvent> listMeteoEvent)
		{
		this.stat = stat;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		jlabelaltitude.setText(ALTITUDE_TITLE+MathTools.arrondir(stat.getLast())+" m");
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
			// JComponent : Instanciation
			jlabelaltitude = new JLabel(ALTITUDE_TITLE+MathTools.arrondir(stat.getLast())+" m");
			Box boxV = Box.createVerticalBox();

			int scaleFactor = 2;
			ImageIcon imgIcon = new ImageIcon(ClassLoader.getSystemResource("images/montagne.png"));
			Image img = imgIcon.getImage();
//			Image img = MagasinImage.mountain.getImage();
			Image newimg = img.getScaledInstance(img.getWidth(null)/scaleFactor, img.getHeight(null)/scaleFactor,  java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);

			boxV.add(new JLabel(newIcon));

			boxV.add(jlabelaltitude);

			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);
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

	// Input
	private Stat stat;

	//Tools
	private JLabel jlabelaltitude;

	private static final String ALTITUDE_TITLE = "Altitude de la station météo : ";

	}
