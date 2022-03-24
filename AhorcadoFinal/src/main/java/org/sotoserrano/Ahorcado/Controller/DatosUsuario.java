package org.sotoserrano.Ahorcado.Controller;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.sotoserrano.Ahorcado.Model.Usuario;

/**
 * @author alumno
 *
 */
public class DatosUsuario {
	/**
	 * 
	 */
	public static Usuario uLDTO;

	/**
	 * Creo un usario admin
	 */
	public static void creaUsuario() {
		uLDTO = new Usuario("admin", "admin");
	}

	/**
	 * @return null
	 */
	/*
	 * He buscado por activa y por pasiva y no he encontrado como obtener la MAC del
	 * usuario (el código que mostraste en clase se ayuda de un comando de Windows
	 * por lo que en Linux no funciona)
	 */
	public static String obtenerMAC() {
		try {
			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			while (networks.hasMoreElements()) {
				NetworkInterface network = networks.nextElement();
				byte[] mac = network.getHardwareAddress();
				if (mac != null) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < mac.length; i++) {
						sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
					}
					return String.valueOf(sb);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}
}
