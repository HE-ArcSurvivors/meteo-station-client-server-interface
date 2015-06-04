package ch.hearc.meteo.imp.use.remote.pccentral;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.hearc.meteo.imp.afficheur.real.AfficheurService;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreatorFactory;
import ch.hearc.meteo.imp.use.remote.PC_I;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;

public class PCCentral extends JFrame implements PC_I {

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCCentral() {
		// rien
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run() {
		server();
	}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void server() {
		geometry();
		control();
		apparence();
		try {
			setRemoteAfficheurCreator(RemoteAfficheurCreatorFactory
					.create());

		} catch (RemoteException e2) {
			System.err.println("Error: RemoteAfficheurCreatorFactory");
			e2.printStackTrace();
		}
	}

	private void apparence() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	private void control() {

		btn_serverAddress.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

//				new AfficheurService();

				try {
					setRemoteAfficheurCreator(RemoteAfficheurCreatorFactory
							.create());

				} catch (RemoteException e2) {
					System.err.println("Error: RemoteAfficheurCreatorFactory");
					e2.printStackTrace();
				}

			}
		});

	}

	private void geometry() {

		frame_serverAddress = new JFrame();
		frame_serverAddress.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		frame_serverAddress.add(panel);
		panel.setSize(400, 200);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		label_serverAddress = new JLabel("hello world");
		panel.add(label_serverAddress);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		textField_serverAddress = new JTextField();
		panel.add(textField_serverAddress);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Lay out the buttons from left to right.
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		btn_serverAddress = new JButton("Connect");
		buttonPane.add(btn_serverAddress);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(btn_serverAddress);

		// Put everything together, using the content pane's BorderLayout.
		Container contentPane = getContentPane();
		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

	}

	/*------------------------------------------------------------------*\
	|*							GET Methodes							*|
	\*------------------------------------------------------------------*/

	public RemoteAfficheurCreator_I getRemoteAfficheurCreator() {
		return remoteAfficheurCreator;
	}

	/*------------------------------------------------------------------*\
	|*							SET Methodes							*|
	\*------------------------------------------------------------------*/

	private void setRemoteAfficheurCreator(
			RemoteAfficheurCreator_I remoteAfficheurCreator) {
		this.remoteAfficheurCreator = remoteAfficheurCreator;
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private RemoteAfficheurCreator_I remoteAfficheurCreator;

	// tmp tools
	private JFrame frame_serverAddress;
	private JTextField textField_serverAddress;
	private JLabel label_serverAddress;
	private JButton btn_serverAddress;
}
