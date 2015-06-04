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

	public JPanelDegrade(float temperature)
		{
		this.temperature = temperature;
		setMinMax(JPanelThermometre.DEFAULT_MIN_VALUE,JPanelThermometre.DEFAULT_MAX_VALUE); //VALEURS PAR DEFAUT

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

	public void setTemperature(float temperature)
		{
		this.temperature = temperature;
		revalidate();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setMinMax(int minValue, int maxValue)
	{
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

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

		int countUnite = (Math.abs(maxValue) + Math.abs(minValue));
		int delta = h / countUnite;

		//POUR LES TESTS, DEVRA PROVENIR DE L'EXTERIEUR OU ÊTRE UNIFIE EN FONCTION DE LA TAILLE
		int decalage = 10;

		float position = decalage + (maxValue - temperature)*delta;
		int black = Color.HSBtoRGB(0, 0, 0);

		for(int j = 0; j < w; j++)
			{
			int color = Color.HSBtoRGB((float)hue, 1, 1);
			for(int i = 0; i < h; i++)
				{
//					if(MathTools.isEquals(j,position,0.01))
//					{
//						bufferedImage.setRGB(i, j, black);
//					}
//					else
//					{
						bufferedImage.setRGB(i, j, color);
//					}
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
		setPreferredSize(new Dimension(50,300));
		setMinimumSize(new Dimension(20,300));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private double deltaHue;
	private BufferedImage bufferedImage;

	private float temperature;
	private int maxValue;
	private int minValue;

	}
