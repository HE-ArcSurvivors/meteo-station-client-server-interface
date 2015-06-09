
package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.thermometre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.MathTools;

public class JPanelFleche extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelFleche(float temperature)
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

		//POUR LES TESTS, DEVRA PROVENIR DE L'EXTERIEUR OU �TRE UNIFIE EN FONCTION DE LA TAILLE
		int decalage = 10;

		int position = (int)(decalage + (maxValue - temperature) * delta);

		int[] tabX = { 50, 50, 60 };
		int[] tabY = { position - 10, position + 10, position };

		g2d.setColor(Color.BLACK);
		g2d.drawString(MathTools.arrondir(temperature)+DataType.getUnite(DataType.TEMPERATURE), 0, position + 5);

		g2d.setBackground(Color.BLACK);
		g2d.fillPolygon(tabX, tabY, 3);

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
		setPreferredSize(new Dimension(60, this.getHeight()));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private float temperature;
	private int maxValue;
	private int minValue;

	}
