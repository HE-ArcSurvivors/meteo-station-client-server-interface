
package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.hearc.meteo.imp.afficheur.real.vue.DataType;

public class JPanelSliderLine extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelSliderLine(int dataType, int value, JPanelSettings parent)
		{
		this.parent = parent;
		this.dataType = dataType;

		label = new JLabel("Δt " + DataType.getString(dataType) + " : ");
		slider = new JSlider();
		slider.setValue(value);
		slider.setMinimum(1);
		slider.setMaximum(1000);

		labelValue = new JLabel("" + value);

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void setValue(int value)
		{
		slider.setValue(value);
		labelValue.setText("" + value);
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
		label.setPreferredSize(new Dimension(120, 15));
		slider.setPreferredSize(new Dimension(200, 15));

		// Layout : Specification
		FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
		setLayout(flowlayout);

		Box boxH = Box.createHorizontalBox();
		boxH.add(label);
		boxH.add(Box.createHorizontalGlue());
		boxH.add(slider);
		boxH.add(Box.createHorizontalStrut(20));
		boxH.add(labelValue);

		// JComponent : add
		add(boxH);
		}

	private void control()
		{
		slider.addChangeListener(new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent e)
					{
					parent.setDelta(dataType, slider.getValue());
					labelValue.setText("" + slider.getValue());
					}
			});
		}

	private void appearance()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JSlider slider;
	private JLabel label;
	private JLabel labelValue;

	//Input
	private int dataType;
	private JPanelSettings parent;

	}
