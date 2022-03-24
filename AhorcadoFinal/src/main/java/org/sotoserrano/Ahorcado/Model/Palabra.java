package org.sotoserrano.Ahorcado.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alumno
 *
 */
public class Palabra {

	private final static String palabras = "./src/main/resources/static/txt/palabrasAhorcado.txt";
	private String palabra;
	private char[] arrayPalabra;
	private int intentos = 1;
	private int errores = 6;
	private String ultimaLetra;
	private List<String> Letras = new ArrayList<>();
	private String LetrasLimpio;

	private Map<Integer, String> imagen = new HashMap<Integer, String>();

	/**
	 * @throws IOException
	 */
	public Palabra() throws IOException {
		
		while (palabra == null) {
			palabra = eligePalabra();
		}
		arrayPalabra = new char[palabra.length()];
		// Conversion de la palabra seleccionada en un guiones
		for (int i = 0; i < arrayPalabra.length; i++) {
			arrayPalabra[i] = '-';
		}
		imagen.put(0, "imagenUno");
		imagen.put(1, "imagenDos");
		imagen.put(2, "imagenTres");
		imagen.put(3, "imagenCuatro");
		imagen.put(4, "imagenCinco");
		imagen.put(5, "imagenSeis");
		imagen.put(6, "imagenSiete");
	}

	/**
	 * @return void
	 * @throws IOException
	 */
	// Selecciona una palabra aleatoria del archivo palabrasAhorcado.txt
	public String eligePalabra() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(palabras));
		int random = 0;
		while ((palabra = br.readLine()) != null) {
			random++;
		}
		random = (int) (Math.random() * random);
		br.close();
		br = new BufferedReader(new FileReader(palabras));
		for (int i = 0; i < random; i++) {
			palabra = br.readLine().toLowerCase();
		}
		return palabra;
	}

	/**
	 * @param letra
	 */
	public void verificarPalabra(String letra) {
		letra = letra.toLowerCase();
		if (Letras.contains(letra) || !((letra.charAt(0)>='a' && letra.charAt(0)<='z') || letra.charAt(0)=='ñ')) {
			return;
		} else {
			ultimaLetra = letra;
			Letras.add(letra);
			boolean encontrado = false;
			if (palabra.contains(letra)) {
				for (int i = 0; i < arrayPalabra.length; i++) {
					if (palabra.charAt(i) == letra.charAt(0)) {
						arrayPalabra[i] = palabra.charAt(i);
						encontrado = true;
					}
				}
			}
			if (encontrado) {
				intentos++;
			} else {
				intentos++;
				errores--;
			}
		}
	}

	/**
	 * @return String
	 */
	public String quitarCorchetes() {
		LetrasLimpio = Arrays.toString(Letras.toArray());
		return LetrasLimpio.substring(1, LetrasLimpio.length() - 1);
	}

	/**
	 * @return boolean
	 */
	public boolean palabraAdivinada() {
		return !(new String(arrayPalabra).contains("-"));
	}

	/**
	 * @return palabras
	 */
	public static String getPalabras() {
		return palabras;
	}

	/**
	 * @return palabra
	 */
	public String getPalabra() {
		return palabra;
	}

	/**
	 * @param palabra
	 */
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	/**
	 * @return arrayPalabra
	 */
	public char[] getArrayPalabra() {
		return arrayPalabra;
	}

	/**
	 * @param arrayPalabra
	 */
	public void setArrayPalabra(char[] arrayPalabra) {
		this.arrayPalabra = arrayPalabra;
	}

	/**
	 * @return intentos
	 */
	public int getIntentos() {
		return intentos;
	}

	/**
	 * @param intentos
	 */
	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}

	/**
	 * @return errores
	 */
	public int getErrores() {
		return errores;
	}

	/**
	 * @param errores
	 */
	public void setErrores(int errores) {
		this.errores = errores;
	}

	/**
	 * @return ultimaLetra
	 */
	public String getUltimaLetra() {
		return ultimaLetra;
	}

	/**
	 * @param ultimaLetra
	 */
	public void setUltimaLetra(String ultimaLetra) {
		this.ultimaLetra = ultimaLetra;
	}

	/**
	 * @return Letras
	 */
	public List<String> getLetras() {
		return Letras;
	}

	/**
	 * @param letras
	 */
	public void setLetras(List<String> letras) {
		Letras = letras;
	}

	/**
	 * @return LetrasLimpio
	 */
	public String getLetrasLimpio() {
		return LetrasLimpio;
	}

	/**
	 * @param letrasLimpio
	 */
	public void setLetrasLimpio(String letrasLimpio) {
		LetrasLimpio = letrasLimpio;
	}

	/**
	 * @return imagen
	 */
	public Map<Integer, String> getImagen() {
		return imagen;
	}

	/**
	 * @param imagen
	 */
	public void setImagen(Map<Integer, String> imagen) {
		this.imagen = imagen;
	}

}
