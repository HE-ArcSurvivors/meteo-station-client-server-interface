
package ch.hearc.meteo.imp.afficheur.real.vue.layout.atom.settings;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		slider = new JSliderSecondes();
		slider.setValue(value);
		slider.setMinimum(500);
		slider.setMaximum(MAXIMUM_MINUTE_DELTA * 60 * 1000);

		labelValue = new JLabel("" + slider.getValue());

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
		labelValue.setText(slider.getStringValue());
		updateUI();
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
		FlowLayout flowlayout = new FlowLayout(FlowLayout.LEFT);
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
					labelValue.setText(slider.getStringValue());
					}
			});
		
//		slider.addMouseListener(new MouseAdapter()
//		{
//
//			@Override
//			public void mouseReleased(MouseEvent arg0)
//				{
//				if (slider.getValue() == 0)
//					{
//					slider.setValue(1);
//					}
//				parent.setDelta(dataType, slider.getValue());
//				}
//		});
		}

	private void appearance()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JSliderSecondes slider;
	private JLabel label;
	private JLabel labelValue;

	//Input
	private int dataType;
	private JPanelSettings parent;

	private final static int MAXIMUM_MINUTE_DELTA = 5;

	}
