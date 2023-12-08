

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.swing.JTextArea;

public class hilo extends Thread {
	
	// Declaración de atributos
	private Runtime tiempoEjecucion;
	private Process proceso;
	private String cadenaRetorno;
	private String comando;
	private String txtUrl;
	private JTextArea txtArea;

	// Contructor por parametros
	public hilo(Runtime tiempoEjecucion, Process proceso, String cadenaRetorno, String comando, String txtUrl,
			JTextArea txtArea) {
	
		this.tiempoEjecucion = tiempoEjecucion;
		this.proceso = proceso;
		this.cadenaRetorno = cadenaRetorno;
		this.comando = comando;
		this.txtUrl = txtUrl;
		this.txtArea = txtArea;
	}
	
	/**
	 * Método para crear los hilos apartir de la clase Run
	 */
	@Override
	public void run() {
		comandos(tiempoEjecucion, proceso, cadenaRetorno, comando, txtUrl, txtArea);
	}
	
	/**
	 * Método donde se realizan todos los comandos
	 * @param tiempoEjecucion
	 * @param proceso
	 * @param cadenaRetorno
	 * @param direcciónDominio
	 * @param txtUrl
	 * @param txtArea
	 */
	private void comandos(Runtime tiempoEjecucion, Process proceso, String cadenaRetorno, String direcciónDominio, String txtUrl , JTextArea txtArea) {
		try {
			// Iniciamos con exec y le pasamos el comando que queremos que realize recogiendolo por parametro 
			proceso = tiempoEjecucion.exec(direcciónDominio + txtUrl);
			
			InputStream is = proceso.getInputStream();
			// Tildes por consola
			InputStreamReader isr = new InputStreamReader(is, Charset.forName("IBM00858"));
			BufferedReader br = new BufferedReader(isr);

			String linea;
			// Recorre todo lo que contiene el buffer y lo guarda en linea
			while ((linea = br.readLine()) != null) {
				// Lo que tiene linea lo guarda en la cadena 
				cadenaRetorno = cadenaRetorno.concat(linea + "\n");
				// Le pedimos que ponga en el texto correspondiente lo que va leyendo con el while
				txtArea.setText(cadenaRetorno);

				// No hace falta
				// Actualizamos los gráficos
				//txtArea.update(txtArea.getGraphics());
			}
			br.close();
		} catch (IOException e1) {
			System.out.println("Error al intentar realizar el comando");
		}
	}

	
}
