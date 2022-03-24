package org.sotoserrano.Ahorcado.Model;

import javax.persistence.Entity;

/**
 * @author alumno
 *
 */
@Entity
public class Usuario {

	private String usuario;
	private String clave;

	/**
	 * Contructor vacio
	 */
	public Usuario() {
	}

	/**
	 * @param usuario
	 * @param clave
	 */
	public Usuario(String usuario, String clave) {
		this.usuario = usuario;
		this.clave = clave;
	}

	/**
	 * @return usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
}