package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.thermometre;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class JPanelValues extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelValues(float temperature)
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
		repaint();
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
			int h = getHeight();

			int countUnite = (Math.abs(maxValue) + Math.abs(minValue));
			int delta = h / countUnite;
			int decalage = 10;
			int value = maxValue;

			for(int i = 0; i <= countUnite; i++)
			{
				if(value%10 == 0)
				{
					g2d.drawLine(0, delta*i + decalage, 20, delta*i + decalage);
					g2d.drawString(Integer.toString(value), 25, (int) (delta*i + 1.4 * decalage));
				}
				else if(value%5 == 0)
				{
					g2d.drawLine(0, delta*i + decalage, 10, delta*i + decalage);
					g2d.drawString(Integer.toString(value), 15, (int) (delta*i + 1.4 * decalage));
				}
				else
				{
					g2d.drawLine(0, delta*i + decalage, 5, delta*i + decalage);
				}
				value -= 1;
			}
		}

	private void geometry()
		{
			// JComponent : Instanciation

			// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

			// JComponent : add

		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		setPreferredSize(new Dimension(50,this.getHeight()));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private float temperature;
	private int maxValue;
	private int minValue;

	}
