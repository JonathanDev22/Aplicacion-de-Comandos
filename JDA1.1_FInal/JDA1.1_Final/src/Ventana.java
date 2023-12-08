import java.awt.EventQueue;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Desktop;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import java.awt.Dimension;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * Inicio de Ventana
 */

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	// Variables Globales
	// Panel de fondo de aplicación.
	private JPanel fondoapp;
	// Panel de recogida de texto del usuario
	private JTextField txtUrl = new JTextField();
	// JTextArea que recoge toda la información recogida por el comando Ping
	private JTextArea txtAreaPing = new JTextArea();
	// JTextArea que recoge toda la información recogida por el comando Curl
	private JTextArea txtAreaCurl = new JTextArea();
	// JTextArea que recoge toda la información recogida por el comando Tracert
	private JTextArea txtAreaTracert = new JTextArea();
	// JTextArea que recoge toda la información recogida por el comando NSLookUp
	private JTextArea txtAreaNsLookUp = new JTextArea();

	// Creamos el filtro del FileChooser para que solo se puedan ver estos tipos de
	// archivo
	private FileNameExtensionFilter filtro = new FileNameExtensionFilter("Documentos de texto y Páginas Web", "txt",
			"html");

	// Elemento Menú que recoge las opciones de modificar.
	private JMenuBar menuBar;
	// Menu que recoge los componentes de creación de documentos
	private JMenu mnFile;
	// Menu que recoge los elemetnos de Edicion de la aplicación
	private JMenu mnEdit;
	// Menu que accede a la pestaña de ayuda.
	private JMenu mnAyuda;
	// Panel que recoge los elementos de caracter principal para la app
	private JPanel panelElementos;
	// Boton que ejcuta todos los hilos
	private JButton btnAceptar;
	// Boton que permite abrir la página introducida por el usuario
	private JButton btnAbrirPagina;
	// Jpanel decorativo para el footer de la app
	private JPanel pFooter;

	// Variables de control del Propiedades.

	private int numSaltTrac = 10;
	private boolean conHostTra = true;
	private int numSaltPing = 4;
	private boolean actICurl = false;
	private boolean actVCurl = false;

	// Hilos declarados como variables globales
	private hilo h1 = null;
	private hilo h2 = null;
	private hilo h3 = null;
	private hilo h4 = null;

	/**
	 * Ventana de la aplicación Ejecuta el inicio
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
					frame.setVisible(true);
					frame.addWindowStateListener(null);

					// Centrar la pantalla
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Se crea el Frame de la Ventana Principal
	 */
	public Ventana() {
		setMinimumSize(new Dimension(1300, 540));
		setBackground(new Color(107, 189, 121));
		// Icono de la app
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ventana.class.getResource("/Material/andalucia.png")));
		// Apartado para poder cerrar y abrir
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1033, 720);

		// Titulo de la Aplicación
		setTitle("Aplicación de Comandos");

		// -------------------------------- Barra de menus
		barraMenus();

		fondoapp = new JPanel();
		fondoapp.setBackground(new Color(247, 251, 248));
		fondoapp.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(fondoapp);

		panelElementos = new JPanel();
		panelElementos.setBackground(new Color(196, 221, 202));
		panelElementos.setBorder(new LineBorder(new Color(54, 143, 63)));

		txtUrl = new JTextField();
		txtUrl.setMargin(new Insets(2, 15, 2, 2));
		txtUrl.setIgnoreRepaint(true);
		txtUrl.setToolTipText("Introduzca una URL..");
		txtUrl.setSelectedTextColor(new Color(54, 143, 63));
		txtUrl.setColumns(10);

		// Etiqueta de título
		JLabel lblURL = new JLabel("Dirección de dominio");
		lblURL.setForeground(new Color(51, 51, 51));
		lblURL.setFont(new Font("Montserrat", Font.BOLD, 28));

		// Logo de la Junta de Andalucía
		JLabel LogoLetras = new JLabel("");
		LogoLetras.setIcon(new ImageIcon(Ventana.class.getResource("/Material/andalucia2 (3).png")));

		/**
		 * Elemento panelado que recoge los valores de los text área que van a ser
		 * mostrados por la app
		 */
		JTabbedPane tblPestañas = new JTabbedPane(JTabbedPane.TOP);
		tblPestañas.setBackground(new Color(107, 189, 121));
		tblPestañas.setFont(new Font("Noto Sans HK", Font.PLAIN, 15));
		tblPestañas.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		// JPanel dedicado a recoger los elementos del ping
		JPanel PPing = new JPanel();
		PPing.setBackground(new Color(255, 255, 255));
		PPing.setToolTipText("Volcará los datos de Ping");
		PPing.setForeground(new Color(0, 0, 0));
		PPing.setFont(new Font("Noto Sans HK", Font.PLAIN, 15));
		tblPestañas.addTab("Comprobación de Red", new ImageIcon(Ventana.class.getResource("/Material/ping.png")), PPing,
				"Realizaremos el ping mediante comandos de consola");
		tblPestañas.setDisabledIconAt(0, new ImageIcon(Ventana.class.getResource("/Material/ping.png")));
		tblPestañas.setForegroundAt(0, new Color(51, 51, 51));
		tblPestañas.setBackgroundAt(0, new Color(247, 251, 248));
		/**
		 * Tipo de panelado que permite la capacidad de desplazarse a lo largo de un
		 * documetno de tamaño superior al del panel padre.
		 */
		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setAutoscrolls(true);

		txtAreaPing.setForeground(new Color(255, 255, 255));
		txtAreaPing.setBackground(new Color(51, 51, 51));
		txtAreaPing.setLineWrap(true);
		scrollPane_1_1.setViewportView(txtAreaPing);
		GroupLayout gl_PPing = new GroupLayout(PPing);
		gl_PPing.setHorizontalGroup(gl_PPing.createParallelGroup(Alignment.LEADING).addComponent(scrollPane_1_1,
				GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE));
		gl_PPing.setVerticalGroup(gl_PPing.createParallelGroup(Alignment.LEADING).addComponent(scrollPane_1_1,
				GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE));
		gl_PPing.setHonorsVisibility(false);
		PPing.setLayout(gl_PPing);

		// JPanel dedicado a recoger los elementos del Curl
		JPanel PCopiar = new JPanel();
		PCopiar.setToolTipText("Volcará los datos del comando CURL");
		PCopiar.setForeground(new Color(255, 255, 255));
		PCopiar.setFont(new Font("Noto Sans HK", Font.PLAIN, 15));
		tblPestañas.addTab("Copiar Página", new ImageIcon(Ventana.class.getResource("/Material/copiar.png")), PCopiar,
				"Realizaremos una copia de la página Web");
		tblPestañas.setForegroundAt(1, new Color(51, 51, 51));
		tblPestañas.setBackgroundAt(1, new Color(247, 251, 248));

		JScrollPane scrollPane_1_1_1 = new JScrollPane();
		scrollPane_1_1_1.setAutoscrolls(true);

		txtAreaCurl = new JTextArea();
		txtAreaCurl.setForeground(new Color(255, 255, 255));
		txtAreaCurl.setBackground(new Color(51, 51, 51));
		txtAreaCurl.setLineWrap(true);
		scrollPane_1_1_1.setViewportView(txtAreaCurl);
		GroupLayout gl_PCopiar = new GroupLayout(PCopiar);
		gl_PCopiar.setHorizontalGroup(gl_PCopiar.createParallelGroup(Alignment.LEADING).addComponent(scrollPane_1_1_1,
				GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE));
		gl_PCopiar.setVerticalGroup(
				gl_PCopiar.createParallelGroup(Alignment.LEADING).addGroup(gl_PCopiar.createSequentialGroup().addGap(0)
						.addComponent(scrollPane_1_1_1, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE).addGap(0)));
		PCopiar.setLayout(gl_PCopiar);

		// JPanel dedicado a recoger los elementos del Tracert
		JPanel PRutaWeb = new JPanel();
		PRutaWeb.setToolTipText("Volcará los datos del TRACERT");
		PRutaWeb.setForeground(new Color(255, 255, 255));
		PRutaWeb.setFont(new Font("Noto Sans HK", Font.PLAIN, 15));
		tblPestañas.addTab("Ruta del sitio WEB", new ImageIcon(Ventana.class.getResource("/Material/ruta.png")),
				PRutaWeb, "Mostrará los saltos que realiza para conectar al servidor");

		JScrollPane scrollPane_1_1_2 = new JScrollPane();
		scrollPane_1_1_2.setAutoscrolls(true);

		txtAreaTracert = new JTextArea();
		txtAreaTracert.setBackground(new Color(51, 51, 51));
		txtAreaTracert.setForeground(new Color(255, 255, 255));
		txtAreaTracert.setLineWrap(true);
		scrollPane_1_1_2.setViewportView(txtAreaTracert);
		GroupLayout gl_PRutaWeb = new GroupLayout(PRutaWeb);
		gl_PRutaWeb.setHorizontalGroup(gl_PRutaWeb.createParallelGroup(Alignment.LEADING).addComponent(scrollPane_1_1_2,
				GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE));
		gl_PRutaWeb.setVerticalGroup(gl_PRutaWeb.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PRutaWeb.createSequentialGroup().addGap(0)
						.addComponent(scrollPane_1_1_2, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE).addGap(0)));
		PRutaWeb.setLayout(gl_PRutaWeb);
		tblPestañas.setForegroundAt(2, new Color(51, 51, 51));
		tblPestañas.setBackgroundAt(2, new Color(247, 251, 248));

		// JPanel dedicado a recoger los elementos del NSLOOKUP
		JPanel PIpUrl = new JPanel();
		PIpUrl.setToolTipText("Volcará los datos del comando NSLOOKUP");
		PIpUrl.setForeground(new Color(255, 255, 255));
		PIpUrl.setFont(new Font("Noto Sans HK", Font.PLAIN, 15));
		tblPestañas.addTab("IP de la URL", new ImageIcon(Ventana.class.getResource("/Material/nslookup.png")), PIpUrl,
				"Volcará los datos del comando NSLOOKUP");

		JScrollPane scrollPane_1_1_3 = new JScrollPane();
		scrollPane_1_1_3.setAutoscrolls(true);

		txtAreaNsLookUp = new JTextArea();
		txtAreaNsLookUp.setForeground(new Color(255, 255, 255));
		txtAreaNsLookUp.setBackground(new Color(51, 51, 51));
		txtAreaNsLookUp.setLineWrap(true);
		scrollPane_1_1_3.setViewportView(txtAreaNsLookUp);
		GroupLayout gl_PIpUrl = new GroupLayout(PIpUrl);
		gl_PIpUrl.setHorizontalGroup(gl_PIpUrl.createParallelGroup(Alignment.LEADING).addComponent(scrollPane_1_1_3,
				GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE));
		gl_PIpUrl.setVerticalGroup(
				gl_PIpUrl.createParallelGroup(Alignment.LEADING).addGroup(gl_PIpUrl.createSequentialGroup().addGap(0)
						.addComponent(scrollPane_1_1_3, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE).addGap(0)));
		PIpUrl.setLayout(gl_PIpUrl);
		tblPestañas.setForegroundAt(3, new Color(51, 51, 51));
		tblPestañas.setBackgroundAt(3, new Color(247, 251, 248));

		btnAbrirPagina = new JButton("Mostrar");
		btnAbrirPagina.setToolTipText("Abrirá el explorador con la página introducida ");
		btnAbrirPagina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Trae la Url puesta en el texto de la URL
				String url = txtUrl.getText();
				if (url.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(tblPestañas, "Porfavor introduce una dirección de dominio válida",
							"Error con Dirección de dominio", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						// Mostrará la página que hemos utilizado como URL
						Desktop.getDesktop().browse(new URI(url));
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(tblPestañas, "Error de Entrada Salida");
					} catch (URISyntaxException e1) {
						JOptionPane.showMessageDialog(tblPestañas, "Error al conectar con el sevidor de la página");
					}
				}
			}
		});
		btnAbrirPagina.setFont(new Font("Noto Sans HK", Font.BOLD, 15));
		btnAbrirPagina.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAbrirPagina.setForeground(new Color(255, 255, 255));
		btnAbrirPagina.setBackground(new Color(8, 112, 33));

		btnAceptar = new JButton("Ejecutar");
		btnAceptar.setToolTipText("Ejecutará los comandos establecidos");

		/**
		 * Al dar al botón de Ejecutar realiza todos los comandos
		 * 
		 */
		txtUrl.setText("www.tunombrededominio.es");

		btnAceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnAceptar.setEnabled(false);
				// Variables actualizadas y convertidas a valor String para ser usadas por el
				// procesador de texto de manera correcta
				String saltosTracert = "" + numSaltTrac + " ";
				String saltosPing = "" + numSaltPing + " ";
				String valorD = " ";
				if (isConHostTra()) {
					valorD = " -d ";
				}
				String iCurl = " ";
				String vCurl = " ";

				if (isActICurl() && isActVCurl()) {
					iCurl = "-iv ";

				} else if (isActICurl() && !isActVCurl()) {
					iCurl = " -i ";
				} else if (isActVCurl() && !isActICurl()) {
					vCurl = " -v ";
				}

				// Clase para llamar a la consola
				Runtime tiempoEjecucion = Runtime.getRuntime();
				// Creamos el proceso
				Process proceso = null;
				// Donde vuelca los datos
				String cadenaRetorno = "";
				String compro = txtUrl.getText();
				char[] buscaPunto = compro.toCharArray();
				int cuentaPuntos = 0, cuentaBarras = 0;
				// Primera criba inicial de dirección por entendimiento de Ip
				for (char c : buscaPunto) {
					if (c == '.') {
						cuentaPuntos++;
					}
				}
				// Segunda criba inicial de dirección que identifica secciones separadas por '/'
				for (char c : buscaPunto) {
					if (c == '/') {
						cuentaBarras++;
					}
				}
				/**
				 * Hecha la criba, en función de los criterios anteriores permite lanzar de
				 * manera correcta los elementos
				 */
				try {
					if (cuentaPuntos >= 3) {
						// En la parte del comando le pasamos el comando que haya introducido el usuario
						// en Properties ping Slider 1 -20 //4
						h1 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "ping -n " + saltosPing + " ",
								txtUrl.getText(), txtAreaPing);
						// Checkbox -i -v
						h2 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "cmd /C curl" + iCurl + vCurl,
								txtUrl.getText() + " 2>&1", txtAreaCurl); // Para que nos devuelva toda la traza le
																			// abrimos desde consola
						h3 = new hilo(tiempoEjecucion, proceso, cadenaRetorno,
								"tracert -w 10" + valorD + " -h " + saltosTracert + " ", txtUrl.getText(),
								txtAreaTracert);
						// -d 10 checkbox // -w 0 - 100 de 10 / 10
						h4 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "nslookup ", txtUrl.getText(),
								txtAreaNsLookUp);

						h4.start();

						try {
							h4.join();
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						String textoRecogido = txtAreaNsLookUp.getText();
						String segundaDireccionIP = obtenerSegundaDireccionIP(textoRecogido);
						if (segundaDireccionIP != null) {
							h1.start();
							h2.start();
							h3.start();
						} else {
							JOptionPane.showMessageDialog(txtAreaPing,
									"La dirección IP no es válida. No se ejecutarán los otros hilos.");
						}

					} else if (cuentaPuntos == 2 && txtUrl.getText().contains("https://")
							|| txtUrl.getText().contains("http://") || txtUrl.getText().contains("file://")
							|| txtUrl.getText().contains("ftp://") && cuentaBarras >= 4) {
						String https;
						https = txtUrl.getText();
						String[] separa = https.split("//");
						https = separa[1];
						String[] separa2 = https.split("/");
						String https2 = separa2[0];
						h1 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "ping -n " + saltosPing + " ", https2,
								txtAreaPing);
						// Checkbox -i -v
						h2 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "cmd /C curl -iv ", https + " 2>&1",
								txtAreaCurl); // Para que nos devuelva toda la traza le
												// abrimos desde consola
						h3 = new hilo(tiempoEjecucion, proceso, cadenaRetorno,
								"tracert -w 10" + valorD + " -h " + saltosTracert + " ", txtUrl.getText(),
								txtAreaTracert);
						// -d 10 checkbox // -w 0 - 100https://www.youtube.com/watch?v=e-TWiuP5N4s de 10
						// / 10

						h4 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "nslookup ", https2, txtAreaNsLookUp);

						// Método empezar de Run()
						h4.start();

						try {
							h4.join();
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						String textoRecogido = txtAreaNsLookUp.getText();
						String segundaDireccionIP = obtenerSegundaDireccionIP(textoRecogido);
						if (segundaDireccionIP == null) {
							h1.start();
							h2.start();
							h3.start();
						} else {
							JOptionPane.showMessageDialog(txtAreaPing,
									"La dirección IP no es válida. No se ejecutarán los otros hilos.");
						}
					} else if (cuentaPuntos == 2) {
						String[] separa = txtUrl.getText().split("/");
						String https = separa[0];
						if (https.contains(".")) {
							https = separa[0];
						} else {
							separa = txtUrl.getText().split("//");
							JOptionPane.showMessageDialog(txtAreaPing,
									"El protocolo esta mal escrito, realizaremos los comandos con el fqdn");
							separa = separa[1].split("/");
							https = separa[0];
						}
						/**
						 * En la parte del comando le pasamos el comando que haya introducido el usuario
						 * en Properties ping Slider 1 -20
						 */
						h1 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "ping -n " + saltosPing + " ", https,
								txtAreaPing);
						// Checkbox -i -v
						h2 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "cmd /C curl -iv ", https + " 2>&1",
								txtAreaCurl); // Para que nos devuelva toda la traza le
												// abrimos desde consola
						h3 = new hilo(tiempoEjecucion, proceso, cadenaRetorno,
								"tracert -w 10" + valorD + " -h " + saltosTracert + " ", txtUrl.getText(),
								txtAreaTracert);
						// -d 10 checkbox // -w 0 - 100 de 10 / 10
						h4 = new hilo(tiempoEjecucion, proceso, cadenaRetorno, "nslookup ", https, txtAreaNsLookUp);

						h4.start();
						try {
							h4.join();
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						String textoRecogido = txtAreaNsLookUp.getText();
						String segundaDireccionIP = obtenerSegundaDireccionIP(textoRecogido);
						if (segundaDireccionIP != null) {
							h1.start();
							h2.start();
							h3.start();
						} else {
							JOptionPane.showMessageDialog(txtAreaPing,
									"La dirección IP no es válida. No se ejecutarán los otros hilos.");
						}
					} else {
						JOptionPane.showMessageDialog(txtAreaPing,
								"URL mal conformada, porque el FQDN no tiene ningún punto, porque el protocolo no es ni file:, http:, https: o ftp: o porque no dispone del URL en <protocolo>://<fqdn>/ (debe tener las / necesarias)\r\n");
					}

				} catch (StringIndexOutOfBoundsException e2) {
					/*
					 * Le ponemos que ponga el error en txtAreaPing para que salga el dialogo en ese
					 * texto
					 */
					JOptionPane.showMessageDialog(txtAreaPing,
							"URL mal conformada, porque el FQDN no tiene ningún punto, porque el protocolo no es ni file:, http:, https: o ftp: o porque no dispone del URL en <protocolo>://<fqdn>/ (debe tener las / necesarias)\r\n");
				}
			}
		});

		
		txtUrl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Borramos lo introducido en el campo de l url
				txtUrl.setText("");
				btnAceptar.setEnabled(true);
			}
		});
		
		btnAceptar.setForeground(new Color(255, 255, 255));
		btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAceptar.setBackground(new Color(8, 112, 33));
		btnAceptar.setFont(new Font("Source Sans Pro", Font.BOLD, 15));

		// Panel del footer con las imagenes y el color Negro
		pFooter = new JPanel();
		pFooter.setForeground(new Color(255, 255, 255));
		pFooter.setBackground(new Color(51, 51, 51));

		JLabel lblIconos = new JLabel("");
		lblIconos.setIcon(new ImageIcon(Ventana.class.getResource("/Material/logos_footer (1).png")));
		GroupLayout gl_pFooter = new GroupLayout(pFooter);
		gl_pFooter.setHorizontalGroup(
			gl_pFooter.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pFooter.createSequentialGroup()
					.addContainerGap(501, Short.MAX_VALUE)
					.addComponent(lblIconos, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addGap(489))
		);
		gl_pFooter.setVerticalGroup(
			gl_pFooter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pFooter.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblIconos, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pFooter.setLayout(gl_pFooter);

		/**
		 * Funcionalidad añadida que permite el reajuste del tamaño de los elementos
		 * tras el reajuste de la ventana
		 */
		GroupLayout gl_panelElementos = new GroupLayout(panelElementos);
		gl_panelElementos.setHorizontalGroup(gl_panelElementos.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelElementos.createSequentialGroup().addGap(0)
						.addGroup(gl_panelElementos.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelElementos.createSequentialGroup().addGap(24)
										.addComponent(
												LogoLetras, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addGroup(gl_panelElementos.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panelElementos.createSequentialGroup()
														.addComponent(lblURL, GroupLayout.DEFAULT_SIZE, 320,
																Short.MAX_VALUE)
														.addGap(122))
												.addComponent(txtUrl, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
										.addGap(28)
										.addComponent(btnAceptar, GroupLayout.PREFERRED_SIZE, 120,
												GroupLayout.PREFERRED_SIZE)
										.addGap(28)
										.addComponent(btnAbrirPagina, GroupLayout.PREFERRED_SIZE, 120,
												GroupLayout.PREFERRED_SIZE)
										.addGap(12))
								.addGroup(
										gl_panelElementos.createSequentialGroup().addGap(53).addComponent(tblPestañas)))
						.addGap(45))
				.addComponent(pFooter, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1007, Short.MAX_VALUE));
		gl_panelElementos.setVerticalGroup(gl_panelElementos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelElementos.createSequentialGroup().addGap(20).addGroup(gl_panelElementos
						.createParallelGroup(Alignment.LEADING)
						.addComponent(LogoLetras, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelElementos.createSequentialGroup().addGap(20).addComponent(lblURL).addGap(4)
								.addComponent(txtUrl, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelElementos.createSequentialGroup().addGap(60).addComponent(btnAceptar,
								GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelElementos.createSequentialGroup().addGap(59).addComponent(btnAbrirPagina,
								GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
						.addGap(6).addComponent(tblPestañas, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE).addGap(18)
						.addComponent(pFooter, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)));
		panelElementos.setLayout(gl_panelElementos);

		GroupLayout gl_fondoapp = new GroupLayout(fondoapp);
		gl_fondoapp.setHorizontalGroup(gl_fondoapp.createParallelGroup(Alignment.LEADING).addComponent(panelElementos,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1009, Short.MAX_VALUE));
		gl_fondoapp.setVerticalGroup(gl_fondoapp.createParallelGroup(Alignment.LEADING).addComponent(panelElementos,
				GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE));
		fondoapp.setLayout(gl_fondoapp);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				menuBar.setBounds(0, 0, getWidth(), 22);
			}
		});
	}

	/**
	 * Barra menu con File Edit y Help
	 */
	private void barraMenus() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		mnFile = new JMenu("File");
		mnFile.setToolTipText("En esta opción podemos guardar todo , guardar y salir de la app");
		mnFile.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		menuBar.add(mnFile);

		// Botón de guardado general que muestra el resto de botones.
		JMenu mnSave = new JMenu("Save");
		mnSave.setToolTipText("Opciones de guardado");
		mnFile.add(mnSave);

		// Botón de guardado del txtaPing
		JMenuItem mntmGuardaPing = new JMenuItem("Guardar Ping");
		mntmGuardaPing.setToolTipText("Guardara todo lo que devuelve el comando Ping");
		// Atajo para entrar en esta opción
		mntmGuardaPing.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		mntmGuardaPing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipoDocumento = ".txt";
				metodoChooser(tipoDocumento, txtAreaPing);
			}
		});
		mnSave.add(mntmGuardaPing);

		// Botón de guardado del txtaCurl
		JMenuItem mntmGuardaCurl = new JMenuItem("Guardar Página");
		mntmGuardaCurl.setToolTipText("Guardara todo lo que devuelve el comando Curl");
		// Atajo para entrar en esta opción
		mntmGuardaCurl.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mntmGuardaCurl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipoDocumento = ".html";
				metodoChooser(tipoDocumento, txtAreaCurl);
			}
		});

		mnSave.add(mntmGuardaCurl);

		// Botón de guardado del txtaTracert
		JMenuItem mntmGuardarTracert = new JMenuItem("Guardar Tracert");
		mntmGuardarTracert.setToolTipText("Guardara todo lo que devuelve el comando Tracert\r\n");
		// Atajo para entrar en esta opción
		mntmGuardarTracert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		mntmGuardarTracert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipoDocumento = ".txt";
				metodoChooser(tipoDocumento, txtAreaTracert);
			}
		});
		mnSave.add(mntmGuardarTracert);

		// Botón de guardado del txtaNSLookUp
		JMenuItem mntmGuardarNslookup = new JMenuItem("Guardar NSlookUp");
		mntmGuardarNslookup.setToolTipText("Guardara todo lo que devuelve el comando Nslookup");

		// Atajo para entrar en esta opción
		mntmGuardarNslookup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		mntmGuardarNslookup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipoDocumento = ".txt";
				metodoChooser(tipoDocumento, txtAreaNsLookUp);
			}
		});
		mnSave.add(mntmGuardarNslookup);

		// Botón de guardado a decisión propia del usuario
		JMenuItem mnSaveAs = new JMenuItem("Save as...");
		mnSaveAs.setToolTipText("Guardara todo lo que devuelven todos los comandos");

		// Atajo para entrar en esta opción
		mnSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		mnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoChooser2();
			}
		});
		mnFile.add(mnSaveAs);

		// Botón de salida de la app
		JMenuItem mnExit = new JMenuItem("Exit");
		mnExit.setToolTipText("Botón para salir de la aplicación");

		// Atajo para entrar en esta opción
		mnExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));

		/**
		 * Salir de la aplicación
		 */
		mnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JSeparator separator_3 = new JSeparator();
		mnFile.add(separator_3);

		mnExit.setMaximumSize(new Dimension(32700, 32767));
		mnFile.add(mnExit);

		mnEdit = new JMenu("Edit");
		mnEdit.setToolTipText("Opción para dar propiedades sobre la aplicación");
		menuBar.add(mnEdit);
		// Lanza la ventana de propiedades.
		JMenuItem mntmMenuPropiedades = new JMenuItem("Properties...");
		mntmMenuPropiedades.setToolTipText("Opción para poder  modifcar los valores del comando tracert y ping");
		mntmMenuPropiedades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Properties dialogo = new Properties(Ventana.this);
				dialogo.setLocationRelativeTo(null);
				dialogo.setVisible(true);
			}
		});

		// Atajo para entrar en esta opción
		mntmMenuPropiedades.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		mntmMenuPropiedades.setMaximumSize(new Dimension(32000, 32767));
		mnEdit.add(mntmMenuPropiedades);
		mnAyuda = new JMenu("Help");
		mnAyuda.setToolTipText("Apartado de ayuda ");
		menuBar.add(mnAyuda);
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Content...");
		mntmNewMenuItem_2.setToolTipText("Pagina web donde aparecerá el manual de usuario");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String urlPagina = "paginaHtml\\contentPagina.html";
				File pagina = new File(urlPagina);

				// Mostrará la página que hemos utilizado como URL
				try {
					Desktop.getDesktop().browse(pagina.toURI());
				} catch (IOException e1) {
				}
			}
		});

		// Atajo para entrar en esta opción
		mntmNewMenuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		mnAyuda.add(mntmNewMenuItem_2);
		JSeparator separator_2 = new JSeparator();
		mnAyuda.add(separator_2);
		JMenuItem mntmAbout = new JMenuItem("About...");
		mntmAbout.setToolTipText("Sobre nosotros y nuestra empresa junto con la versión");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Area donde queremos que salga el dialogo , Texto de dentro , Titulo de la
				 * ventana , tipomensaje y icono que quieres que te salga
				 */
				JOptionPane.showMessageDialog(txtAreaPing,
						"Aplicación de Comandos \n Version 1.2 \n Programaciones S.L \n - Jonathan Carpio \n - Fernando García \n - Alejandro López \n - Alejandro Panizo \n - Piero Lara \n\n ©Copyright" , "Datos de la Aplicación",
						JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(Ventana.class.getResource("/Material/logoEmpresa.png")));
			}
		});

		// Atajo para entrar en esta opción
		mntmAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		mnAyuda.add(mntmAbout);
	}

	/**
	 * Metodo del chooser
	 * 
	 * @param tipo         Si es txt o html
	 * @param areaEscribir Que area coge para escribir el texto en el archivo nuevo
	 */
	private void metodoChooser(String tipo, JTextArea areaEscribir) {
		// Creamos el objeto JFileChooser
		JFileChooser fileChoooser = new JFileChooser();
		// Le indicamos el filtro una vez establecido de manera global
		fileChoooser.setFileFilter(filtro);

		/*
		 * Abrimos la ventana, guardamos la opcion seleccionada por el usuario devuelve
		 * entero
		 */
		int seleccion = fileChoooser.showSaveDialog(fondoapp);

		// Si el usuario, pincha en aceptar
		if (seleccion == JFileChooser.APPROVE_OPTION) {
			try {
				String rutaArchivo = fileChoooser.getSelectedFile() + tipo;
				BufferedWriter escribir = new BufferedWriter(new FileWriter(rutaArchivo, true));
				if (tipo.equals(".txt")) {
					escribir.write(areaEscribir.getText());
					JOptionPane.showMessageDialog(areaEscribir,
							"El archivo ha sido creado correctamente en la ruta: " + rutaArchivo);
					escribir.close();
				} else if (tipo.equals(".html")) {
					escribir.write(areaEscribir.getText());
					JOptionPane.showMessageDialog(areaEscribir,
							"El archivo ha sido creado correctamente en la ruta: " + rutaArchivo);
					escribir.close();
				} else {
					JOptionPane.showMessageDialog(areaEscribir, "Porfavor introduce un tipo de archivo correcto");
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(areaEscribir, "Error al escribir en el fichero", "Error al guardar..",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(areaEscribir, "No se ha guardado ningún fichero", "Error al guardar..",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void metodoChooser2() {
		// Creamos el objeto JFileChooser
		JFileChooser fileChoooser = new JFileChooser();
		// Le indicamos el filtro
		fileChoooser.setFileFilter(filtro);
		// Abrimos la ventana, guardamos la opcion seleccionada por el usuario
		int seleccion = fileChoooser.showSaveDialog(fondoapp);
		// Si el usuario, pincha en aceptar
		try {
			// La ruta que elija en el momento
			String rutaArchivo = fileChoooser.getSelectedFile() + ".txt";
			BufferedWriter escribir = new BufferedWriter(new FileWriter(rutaArchivo));
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				// Escribira todo en el fichero donde lo ubiquemos
				escribir.write("Resultado que devuelve el comando PING a " + txtUrl.getText());
				escribir.write("\n**************************************************\n");
				escribir.write("\n");
				escribir.write(txtAreaPing.getText());
				escribir.write("\n");
				escribir.write("Resultado que devuelve el comando CURL a " + txtUrl.getText());
				escribir.write("\n**************************************************\n");
				escribir.write("\n");
				escribir.write(txtAreaCurl.getText());
				escribir.write("\n");
				escribir.write("Resultado que devuelve el comando TRACERT a " + txtUrl.getText());
				escribir.write("\n**************************************************\n");
				escribir.write("\n");
				escribir.write(txtAreaTracert.getText());
				escribir.write("\n");
				escribir.write("Resultado que devuelve el comando NSLOOKUP a " + txtUrl.getText());
				escribir.write("\n**************************************************\n");
				escribir.write("\n");
				escribir.write(txtAreaNsLookUp.getText());
				escribir.write("\n");
				JOptionPane.showMessageDialog(txtAreaPing,
						"El archivo ha sido creado correctamente en la ruta: " + rutaArchivo);
				escribir.close();
			} else {
				JOptionPane.showMessageDialog(txtAreaPing, "No se ha guardado ningún fichero", "Error al guardar..",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(txtAreaPing, "Error al escribir en el fichero", "Error al guardar..",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Método de comprobación de la validez de la dirección introducida, tras la
	 * criba inicial. Conforma una segunda capa de seguridad ante el valores
	 * introducidos.
	 */
	private String obtenerSegundaDireccionIP(String texto) {
		int contadorAddress = 0;
		int contadorAddresses = 0;
		int index = -1;
		int index2 = -1;
		while ((index = texto.indexOf("Address:", index + 1)) != -1) {
			contadorAddress++;
			if (contadorAddress == 2) {
				int startIndex = index + "Address:".length();
				int endIndex = texto.indexOf("\n", startIndex);
				if (endIndex == -1) {
					endIndex = texto.length();
				}
				return texto.substring(startIndex, endIndex).trim();
			}
		}
		while ((index2 = texto.indexOf("Addresses:", index2 + 1)) != -1) {
			contadorAddresses++;
			if (contadorAddresses == 1) {
				int startIndex = texto.indexOf("\n", index2 + "Addresses:".length()) + 1;
				while (startIndex < texto.length()) {
					String potentialIP = texto.substring(startIndex, texto.indexOf("\n", startIndex));
					if (isIPv4Address(potentialIP.trim())) {
						return potentialIP.trim();
					}
					startIndex = texto.indexOf("\n", startIndex) + 1;
				}
				return null;
			}
		}
		return null;
	}

	// Getters y setters de la clase, para actualizar los valores guardados en la
	// app.

	private boolean isIPv4Address(String input) {
		return input.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
	}

	public int getNumSaltTrac() {
		return numSaltTrac;
	}

	public void setNumSaltTrac(int numSaltTrac) {
		this.numSaltTrac = numSaltTrac;
	}

	public boolean isConHostTra() {
		return conHostTra;
	}

	public void setConHostTra(boolean conHostTra) {
		this.conHostTra = conHostTra;
	}

	public int getNumSaltPing() {
		return numSaltPing;
	}

	public void setNumSaltPing(int numSaltPing) {
		this.numSaltPing = numSaltPing;
	}

	public boolean isActICurl() {
		return actICurl;
	}

	public void setActICurl(boolean actICurl) {
		this.actICurl = actICurl;
	}

	public boolean isActVCurl() {
		return actVCurl;
	}

	public void setActVCurl(boolean actVCurl) {
		this.actVCurl = actVCurl;
	}
}