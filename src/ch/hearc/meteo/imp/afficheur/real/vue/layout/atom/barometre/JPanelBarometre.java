package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.barometre;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.MathTools;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelBarometre extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelBarometre(Stat stat, List<MeteoEvent> listMeteoEvent)
		{
		this.stat = stat;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	protected void paintComponent(Graphics g)
		{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform initG2d = g2d.getTransform();
		dessiner(g2d);
		g2d.setTransform(initG2d);
		}

	public void update()
		{
		pression = stat.getLast();
		jlabelpression.setText(MathTools.arrondir(pression));
		repaint();
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

	private void dessiner(Graphics2D g2d)
		{
		//MagasinImage.barometreBorder.getImage()
		ImageIcon borderBarometre = new ImageIcon(ClassLoader.getSystemResource("images/barometre.png"));

		ImageIcon arrowBarometre = new ImageIcon(ClassLoader.getSystemResource("images/flecheBarometre.png"));

		g2d.drawImage(borderBarometre.getImage(), 0, 0, null);

		Image img = arrowBarometre.getImage();
		g2d.translate(img.getWidth(null)/2, img.getHeight(null)/2);
		double position = 970 + pression - START_POSITION;
		g2d.rotate(ALPHA * position * Math.PI / 180);
		g2d.translate(-img.getWidth(null)/2, -img.getHeight(null)/2);
		g2d.drawImage(img, 0, 0, null);

		}

	private void geometry()
		{
			// JComponent : Instanciation
			jlabelpression = new JLabel(MathTools.arrondir(pression));

			// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add

			add(jlabelpression);

		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		setMinimumSize(new Dimension(300,300));
		setPreferredSize(new Dimension(300,300));
		setMaximumSize(new Dimension(300,300));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private Stat stat;

	// Tools
	private float pression;
	private JLabel jlabelpression;

	private final static double ALPHA = 4.5;
	private final static double START_POSITION = 990;

	}
