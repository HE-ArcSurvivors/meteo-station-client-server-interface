package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.altitude;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.MathTools;

public class JPanelAltitude extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelAltitude(Stat stat)
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
		jlabelaltitude.setText(MathTools.arrondir(stat.getLast())+" "+DataType.getUnite(DataType.ALTITUDE));
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
					DataType.getString(DataType.ALTITUDE));
			title.setTitleJustification(TitledBorder.RIGHT);
			this.setBorder(title);

			jlabelaltitude = new JLabel(MathTools.arrondir(stat.getLast())+" "+DataType.getUnite(DataType.ALTITUDE), SwingConstants.CENTER);

			int scaleFactor = 2;
			ImageIcon imgIcon = new ImageIcon(ClassLoader.getSystemResource("images/montagne.png"));
			Image img = imgIcon.getImage();
//			Image img = MagasinImage.mountain.getImage();
			Image newimg = img.getScaledInstance(img.getWidth(null)/scaleFactor, img.getHeight(null)/scaleFactor,  java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);

			Box boxV = Box.createVerticalBox();
			boxV.add(new JLabel(newIcon));
			boxV.add(new JLabel(ALTITUDE_TITLE, SwingConstants.CENTER));
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
