import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Properties extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel pFondo = new JPanel();
	public JSpinner spinnerTracert;
	public JCheckBox checkBoxTracert;
	public JCheckBox chckbxActivarI;
	public JSlider sliderPing;
	public JCheckBox chckbxActivarV;

	// Recogemos lo que meta en el spinner (Convertido a String)
	public int saltosPingProperties = 4;
	public int saltosTracertProperties = 10;
	public String valorD;

	/**
	 * Lanza la ventana inicial, que requiere el objeto de Ventana principal para
	 * aplicarle al mismo objetos los cambios datos de manera retroactiva
	 */
	public void jdialogo(Ventana ventanaPrincipal) {
		try {
			Properties dialogo = new Properties(ventanaPrincipal);
			// X de cerrar
			dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		} catch (Exception e) {

		}
	}

	/**
	 * Creamos la ventana del Jdialog
	 */
	public Properties(Ventana ventanaPrincipal) {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBackground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Properties.class.getResource("/Material/icono.png")));

		// Realizarla el cuadrito para que sea modal
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Apartado Propiedades");
		setBounds(100, 100, 702, 349);
		getContentPane().setLayout(null);
		pFondo.setToolTipText("Slider para establecer un maximo de saltos en el Ping");
		pFondo.setBounds(0, 11, 686, 251);
		pFondo.setBorder(new EmptyBorder(5, 5, 5, 5));
		pFondo.setBackground(new Color(196, 221, 202));
		getContentPane().add(pFondo);
		pFondo.setLayout(null);
		{
			/*
			 * Eqtiqueta que indica el títutlo de la Opciones del comando Tracert que se van
			 * a modificar.
			 */
			JLabel lblTracert = new JLabel("Tracert");
			lblTracert.setFont(new Font("Montserrat", Font.BOLD, 28));
			lblTracert.setBounds(32, 25, 115, 37);
			pFondo.add(lblTracert);
		}
		{
			// Etiqueta que indicia el nombre de la variable -n en Tracert
			JLabel lblSaltosT = new JLabel("Máximo de saltos: ");
			lblSaltosT.setFont(new Font("Noto Sans HK", Font.PLAIN, 15));
			lblSaltosT.setBounds(42, 65, 126, 20);
			pFondo.add(lblSaltosT);
		}

		// Titulo de la opción de -d en Tracert
		JLabel lbl = new JLabel("Desactivar conversión de nombre de host");
		lbl.setFont(new Font("Noto Sans HK", Font.PLAIN, 15));
		lbl.setBounds(229, 65, 283, 20);
		pFondo.add(lbl);
		{
			/*
			 * Eqtiqueta que indica el títutlo de la Opciones del comando Ping que se van a
			 * modificar.
			 */
			JLabel lblPing = new JLabel("Ping");
			lblPing.setFont(new Font("Montserrat", Font.BOLD, 28));
			lblPing.setBounds(32, 96, 78, 37);
			pFondo.add(lblPing);
		}
		{
			// Etiqueta que indicia el nombre de la variable -n en Ping
			JLabel lblTiempoPing = new JLabel("Número de saltos: ");
			lblTiempoPing.setFont(new Font("Noto Sans HK", Font.PLAIN, 15));
			lblTiempoPing.setBounds(42, 142, 228, 20);
			pFondo.add(lblTiempoPing);
		}
		{
			// Etiqueta decorativa
			JLabel logoAjustes = new JLabel("");
			logoAjustes.setIcon(new ImageIcon(Properties.class.getResource("/Material/ajustes2.png")));
			logoAjustes.setBounds(560, 142, 116, 104);
			pFondo.add(logoAjustes);
		}

		sliderPing = new JSlider();
		sliderPing.setValue(4);
		sliderPing.setOpaque(false);
		sliderPing.setValueIsAdjusting(true);
		sliderPing.setSnapToTicks(true);
		sliderPing.setMaximum(20);
		sliderPing.setPaintLabels(true);
		sliderPing.setMajorTickSpacing(1);
		sliderPing.setMinimum(1);
		sliderPing.setBounds(175, 125, 345, 37);
		pFondo.add(sliderPing);
		sliderPing.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				saltosPingProperties = sliderPing.getValue();
				sliderPing.setValue(saltosPingProperties);
			}
		});

		spinnerTracert = new JSpinner();
		spinnerTracert.setToolTipText("Establece un máximo de saltos al Comando Tracert");
		spinnerTracert.setOpaque(false);
		/**
		 * Modo en el que interactua de 10 en 10 , su minimo es 1 y el maximo 100 , por
		 * defecto 10
		 */
		spinnerTracert.setModel(new SpinnerNumberModel(10, 1, 100, 10));
		spinnerTracert.setBounds(162, 67, 39, 20);
		pFondo.add(spinnerTracert);
		spinnerTracert.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				saltosTracertProperties = (int) spinnerTracert.getValue();
			}
		});

		checkBoxTracert = new JCheckBox("");
		checkBoxTracert.setToolTipText("Activa o desactiva el nombramiento de hosts");
		checkBoxTracert.setBackground(new Color(196, 221, 202));
		checkBoxTracert.setSelected(true);
		checkBoxTracert.setBounds(509, 64, 21, 21);
		pFondo.add(checkBoxTracert);
		checkBoxTracert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valorD = " -d ";
			}
		});

		{
			/*
			 * Eqtiqueta que indica el títutlo de la Opciones del comando Curl que se van a
			 * modificar.
			 */
			JLabel lblCurl = new JLabel("Curl");
			lblCurl.setFont(new Font("Dialog", Font.BOLD, 28));
			lblCurl.setBounds(32, 169, 78, 37);
			pFondo.add(lblCurl);
		}
		{
			// Etiqueta que indicia el nombre de la variable -i en Curl
			chckbxActivarI = new JCheckBox("Activar - i");
			chckbxActivarI.setToolTipText("Activa los errores que puedan ocurrir en el comando Curl");
			chckbxActivarI.setBackground(new Color(196, 221, 202));
			chckbxActivarI.setBounds(42, 213, 78, 21);
			pFondo.add(chckbxActivarI);
		}
		{
			// Etiqueta que indicia el nombre de la variable -v en Curl
			chckbxActivarV = new JCheckBox("Activar - v");
			chckbxActivarV.setToolTipText("Activa la opcion para que pueda tener más datos del error");
			chckbxActivarV.setBackground(new Color(196, 221, 202));
			chckbxActivarV.setBounds(132, 213, 86, 21);
			pFondo.add(chckbxActivarV);
		}

		{
			JPanel btnsPanel = new JPanel();
			btnsPanel.setBounds(0, 261, 686, 54);

			btnsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(btnsPanel);
			/**
			 * Funcionalidad de guardar o despreciar los cambios aplicados en la app.
			 */
			{
				JButton okButton = new JButton("OK");
				okButton.setForeground(new Color(255, 255, 255));
				okButton.setBackground(new Color(8, 112, 33));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Si no lo modifican
						if (saltosTracertProperties == 10) {
							JOptionPane.showMessageDialog(pFondo,
									"Alguno de los campos no se han introducido \n al hacer el comando Ping y Tracert se harán los comandos por defecto",
									"Campos sin modificar", JOptionPane.INFORMATION_MESSAGE);
						} else if (saltosPingProperties == 4) {
							JOptionPane.showMessageDialog(pFondo,
									"Alguno de los campos no se han introducido \n al hacer el comando Ping y Tracert se harán los comandos por defecto",
									"Campos sin modificar", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(pFondo,
									"Los datos elegidos se modificarán \n al hacer el comando Ping y Tracert",
									"Aceptar opciones", JOptionPane.INFORMATION_MESSAGE);
						}

						// Enviaremos las variables actualizadas a la Ventana Principal.
						((Ventana) ventanaPrincipal).setActICurl(chckbxActivarI.isSelected());
						((Ventana) ventanaPrincipal).setActVCurl(chckbxActivarV.isSelected());
						((Ventana) ventanaPrincipal).setConHostTra(checkBoxTracert.isSelected());
						((Ventana) ventanaPrincipal).setNumSaltTrac((int) spinnerTracert.getValue());
						((Ventana) ventanaPrincipal).setNumSaltPing(sliderPing.getValue());
						// haremos que cierre la ventana una vez dado en Ok
						dispose();
					}
				});

				okButton.setActionCommand("OK");
				btnsPanel.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setForeground(new Color(255, 255, 255));
				cancelButton.setBackground(new Color(8, 112, 33));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(pFondo, "No se guardara ningún dato elegido", "Cancelar opciones",
								JOptionPane.OK_OPTION);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				btnsPanel.add(cancelButton);
			}
		}
	}

}
