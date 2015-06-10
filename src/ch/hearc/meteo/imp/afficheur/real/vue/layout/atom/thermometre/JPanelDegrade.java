
package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.thermometre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JPanelDegrade extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelDegrade()
		{
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
		dessiner(g2d);
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
		int w = bufferedImage.getWidth();
		int h = bufferedImage.getHeight();

		double hue = 1;
		deltaHue = (0.7) / w;

		for(int j = 0; j < w; j++)
			{
			int color = Color.HSBtoRGB((float)hue, 1, 1);
			for(int i = 0; i < h; i++)
				{
				bufferedImage.setRGB(i, j, color);
				}
			hue += deltaHue;
			}

		g2d.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);

		}

	private void geometry()
		{
		bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		setPreferredSize(new Dimension(50, 300));
		setMinimumSize(new Dimension(20, 300));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private double deltaHue;
	private BufferedImage bufferedImage;

	}
