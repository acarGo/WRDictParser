package org.acg.parserDiccionario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JVentana extends JFrame {

	private JPanel contentPane;
	private JButton btnElegirFicheroPalabras;
	private JFileChooser chooserPalabras;
	private JFileChooser chooserDestino;
	private Controlador controlador;
	private JButton btnElegirRutaDe;
	private String fPalabras = "";
	private String rDestino = "";
	private JButton btnLetsGo;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JVentana frame = new JVentana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JVentana() {
		setMinimumSize(new Dimension(228, 319));
		setType(Type.UTILITY);
		setTitle("ParserDiccionario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 228, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getBtnLetsGo(), BorderLayout.SOUTH);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		// Inicializar filechooser origen
		chooserPalabras = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		chooserPalabras.setFileFilter(filter);
		// Inicializar filechooser destino
		chooserDestino = new JFileChooser();
		chooserDestino.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// Anadir escuchas
		btnElegirFicheroPalabras.addActionListener(getControlador());
		btnElegirRutaDe.addActionListener(getControlador());
		btnLetsGo.addActionListener(getControlador());
	}

	private JButton getBtnElegirFicheroPalabras() {
		if (btnElegirFicheroPalabras == null) {
			btnElegirFicheroPalabras = new JButton("Elegir fichero palabras");
		}
		return btnElegirFicheroPalabras;
	}

	private Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	private class Controlador extends WindowAdapter implements ActionListener {

		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnElegirFicheroPalabras) {
				int returnVal = chooserPalabras.showOpenDialog(JVentana.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fPalabras = chooserPalabras.getSelectedFile().getAbsolutePath();
				}
			} else if (e.getSource() == btnElegirRutaDe) {
				int returnVal = chooserDestino.showOpenDialog(JVentana.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					rDestino = chooserDestino.getSelectedFile().getAbsolutePath();
				}
			} else if (e.getSource() == btnLetsGo) {
				if (!fPalabras.isEmpty() && !rDestino.isEmpty()) {
					ParserDiccionario.getParserDiccionario().process(fPalabras, rDestino);
					JOptionPane.showMessageDialog(JVentana.this, "Proceso realizado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				} else JOptionPane.showMessageDialog(JVentana.this, "Elige ambas rutas!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JButton getBtnElegirRutaDe() {
		if (btnElegirRutaDe == null) {
			btnElegirRutaDe = new JButton("Elegir ruta de salida");
		}
		return btnElegirRutaDe;
	}

	private JButton getBtnLetsGo() {
		if (btnLetsGo == null) {
			btnLetsGo = new JButton("Let's Go!");
		}
		return btnLetsGo;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
					gl_panel.createSequentialGroup()
							.addGap(18)
							.addGroup(
									gl_panel.createParallelGroup(Alignment.TRAILING)
											.addComponent(getBtnElegirFicheroPalabras(), Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
													GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(getBtnElegirRutaDe(), Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
							.addGap(23)));
			gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
					gl_panel.createSequentialGroup().addGap(66).addComponent(getBtnElegirFicheroPalabras()).addGap(18)
							.addComponent(getBtnElegirRutaDe()).addContainerGap(122, Short.MAX_VALUE)));
			panel.setLayout(gl_panel);
		}
		return panel;
	}
}
