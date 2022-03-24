package org.sotoserrano.Ahorcado.Controller;

import org.sotoserrano.Ahorcado.Model.Palabra;
import org.sotoserrano.Ahorcado.Model.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author alumno
 *
 */
@Controller
@RequestMapping("juego")
public class AhorcadoController {
	private boolean noCookie = false;
	private int contador;
	private Cookie partidas;
	private Cookie id;

	/**
	 * @param _id
	 * @param solicitud
	 * @param respuesta
	 * @return mAV
	 * @throws IOException
	 */
	@GetMapping("ahorcado")
	public ModelAndView devuelveAhorcado(@CookieValue(value = "_id", required = false) String _id,
			HttpServletRequest solicitud, HttpServletResponse respuesta) throws IOException {
		ModelAndView mAV = new ModelAndView();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String mac = DatosUsuario.obtenerMAC();
		String navegador = solicitud.getHeader("user-agent");
		Palabra palabra;
		Usuario Usuario;
		String desenlace = "";
		String saludo = "";

		Usuario = (Usuario) solicitud.getSession().getAttribute("Usuario");

		if (solicitud.getSession().getAttribute("palabra") != null) {
			palabra = (Palabra) solicitud.getSession().getAttribute("palabra");
		} else {
			palabra = new Palabra();
			solicitud.getSession().setAttribute("palabra", palabra);
		}

		if (palabra.getPalabra().equals(String.valueOf(palabra.getArrayPalabra()))) {
			desenlace = "Has ganado";
		}

		if (palabra.getErrores() == 0) {
			desenlace = "Has perdido";
		} else if (palabra.getErrores() < 0) {
			solicitud.getSession().removeAttribute("palabra");
			palabra = new Palabra();
			solicitud.getSession().setAttribute("palabra", palabra);
			noCookie = true;
		}

		if (_id != null) {
			id = new Cookie("_id", solicitud.getCookies()[0].getValue());
			contador = Integer.parseInt(solicitud.getCookies()[1].getValue());
			if (noCookie) {
				contador++;
			}
			partidas = new Cookie("_contador", String.valueOf(contador));
			respuesta.addCookie(id);
			respuesta.addCookie(partidas);
			saludo = "Gracias por volver: ";
			noCookie = false;
		} else {
			contador = 1;
			id = new Cookie("_id", Usuario.getUsuario());
			partidas = new Cookie("_contador", String.valueOf(contador));
			respuesta.addCookie(id);
			respuesta.addCookie(partidas);
			saludo = "Encantado: ";
		}

		mAV.addObject("ID", id.getValue());
		mAV.addObject("textoCONT", partidas.getValue());
		mAV.addObject("ip", ip);
		mAV.addObject("MAC", mac);
		mAV.addObject("navegador", navegador);
		mAV.addObject("saludo", saludo);
		mAV.addObject("palabra", palabra.getArrayPalabra());
		mAV.addObject("ultimaLetra", palabra.getUltimaLetra());
		mAV.addObject("letras", palabra.quitarCorchetes());
		mAV.addObject("palabraOculta", palabra.getPalabra());
		mAV.addObject("palabraSI", palabra.palabraAdivinada());
		mAV.addObject("intentos", palabra.getIntentos());
		mAV.addObject("fallos", palabra.getErrores());
		mAV.addObject("desenlace", desenlace);
		mAV.addObject("imagen", palabra.getImagen());
		mAV.setViewName("juego");
		return mAV;
	}

	/**
	 * @param solicitud
	 * @param letra
	 * @return mAV
	 */
	@PostMapping("ahorcado")
	public ModelAndView procesaAhorcado(HttpServletRequest solicitud, @RequestParam(required = false) String letra) {
		ModelAndView mAV = new ModelAndView();
		if (letra.isBlank()) {
			mAV.setViewName("redirect:ahorcado");
		} else {
			Palabra palabra;
			palabra = (Palabra) solicitud.getSession().getAttribute("palabra");
			palabra.verificarPalabra(letra);
			solicitud.getSession().setAttribute("palabra", palabra);
			solicitud.getSession().setAttribute("ultimaLetra", palabra.getUltimaLetra());
			solicitud.getSession().setAttribute("letras", palabra.quitarCorchetes());
			mAV.setViewName("redirect:ahorcado");
		}
		return mAV;
	}

	/**
	 * @param solicitud
	 * @return mAV
	 * @throws IOException
	 */
	@PostMapping("nuevaPartida")
	public ModelAndView partidaNueva(HttpServletRequest solicitud) throws IOException {
		ModelAndView mAV = new ModelAndView();
		solicitud.getSession().removeAttribute("palabra");
		Palabra palabra = new Palabra();
		solicitud.getSession().setAttribute("palabra", palabra);
		mAV.setViewName("redirect:ahorcado");
		noCookie = true;
		return mAV;
	}
}
