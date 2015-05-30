
package ch.hearc.meteo.imp.afficheur.real.vue.layout.menu;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener; //property change stuff

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

/* 1.4 example used by DialogDemo.java. */
class JDialogSettings extends JDialog implements ActionListener ,PropertyChangeListener
	{

	public JDialogSettings(Frame aFrame)
		{
		super(aFrame, true);
		setTitle("Settings");

		stringTemperature = "Modifier dt de la température";
		sliderTemperature = new JSlider(0, 100);

		stringPression = "Modifier dt de la température";
		sliderPression = new JSlider(0, 100);

		stringAltitude = "Modifier dt de la température";
		sliderAltitude = new JSlider(0, 100);

		buttonEdit = "Modifier";
		buttonCancel = "Annuler";

		Object[] array = { stringTemperature, sliderTemperature, stringPression, sliderPression, stringAltitude, sliderAltitude };
		Object[] options = { buttonEdit, buttonCancel };

		optionPane = new JOptionPane(array, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);
		setContentPane(optionPane);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter()
			{

				@Override
				public void windowClosing(WindowEvent we)
					{
					optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
					}
			});

		addComponentListener(new ComponentAdapter()
			{

				@Override
				public void componentShown(ComponentEvent ce)
					{
					sliderTemperature.requestFocusInWindow();
					}
			});

		optionPane.addPropertyChangeListener(this);

		}

	@Override
	public void actionPerformed(ActionEvent e)
		{
		optionPane.setValue(buttonEdit);
		}

	@Override
	public void propertyChange(PropertyChangeEvent e)
		{
		String prop = e.getPropertyName();

		if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop)))
			{
			Object value = optionPane.getValue();
			System.out.println("value : " + value);
			if (value == JOptionPane.UNINITIALIZED_VALUE)
				{
				//ignore reset
				return;
				}

			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
			clearAndHide();
			}
		}

	public void clearAndHide()
		{
		setVisible(false);
		}


	private JSlider sliderTemperature;
	private JSlider sliderPression;
	private JSlider sliderAltitude;

	private JOptionPane optionPane;

	private String buttonEdit;
	private String buttonCancel;

	private String stringTemperature;
	private String stringPression;
	private String stringAltitude;
	}
