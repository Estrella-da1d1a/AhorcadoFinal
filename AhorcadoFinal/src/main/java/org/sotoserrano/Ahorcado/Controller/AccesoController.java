package org.sotoserrano.Ahorcado.Controller;

import org.sotoserrano.Ahorcado.Model.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author alumno
 *
 */
@Controller
@RequestMapping("acceso")
public class AccesoController {
	private static boolean crearRoot = true;

	/**
	 * @return crearRoot
	 */
	public static boolean isCrearRoot() {
		return crearRoot;
	}

	/**
	 * @param crearRoot
	 */
	public static void setCrearRoot(boolean crearRoot) {
		AccesoController.crearRoot = crearRoot;
	}

	/**
	 * @param solicitud
	 * @return mAV
	 * @throws UnknownHostException
	 */
	@GetMapping("login")
	public ModelAndView devuelveFormularioLogin(HttpServletRequest solicitud) throws UnknownHostException {
		ModelAndView mAV = new ModelAndView();
		if (isCrearRoot()) {
			DatosUsuario.creaUsuario();
		}
		setCrearRoot(false);
		Usuario Usuario;
		String ip = InetAddress.getLocalHost().getHostAddress();
		String mac = DatosUsuario.obtenerMAC();
		String navegador = solicitud.getHeader("user-agent");
		if (solicitud.getSession().getAttribute("Usuario") != null) {
			Usuario = (Usuario) solicitud.getSession().getAttribute("Usuario");
		} else {
			Usuario = new Usuario();
		}
		mAV.addObject("Usuario", Usuario);
		mAV.addObject("ip", ip);
		mAV.addObject("MAC", mac);
		mAV.addObject("navegador", navegador);
		mAV.setViewName("login");
		return mAV;
	}

	/**
	 * @param Usuario
	 * @param br
	 * @param solicitud
	 * @return mAV
	 * @throws UnknownHostException
	 */
	@PostMapping("login")
	public ModelAndView recibeCredencialesLogin(@Valid Usuario Usuario, BindingResult br, HttpServletRequest solicitud)
			throws UnknownHostException {
		ModelAndView mAV = new ModelAndView();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String mac = DatosUsuario.obtenerMAC();
		String navegador = solicitud.getHeader("user-agent");
		boolean ahorcado = false;
		if (br.hasErrors()) {
			mAV.addObject("otroError", "No se encuentra ningun usuario con los campos dados");
			mAV.addObject("ip", ip);
			mAV.addObject("MAC", mac);
			mAV.addObject("navegador", navegador);
			mAV.addObject("Usuario", Usuario);
			mAV.setViewName("login");
		} else {
			if (Usuario.getUsuario().equals(DatosUsuario.uLDTO.getUsuario())
					&& Usuario.getClave().equals(DatosUsuario.uLDTO.getClave())) {
				ahorcado = true;
			}
			if (ahorcado) {
				solicitud.getSession().setAttribute("Usuario", Usuario);
				mAV.setViewName("redirect:/juego/ahorcado");
			} else {
				mAV.addObject("otroError", "No se encuentra ningun usuario con los campos dados");
				mAV.addObject("ip", ip);
				mAV.addObject("MAC", mac);
				mAV.addObject("navegador", navegador);
				mAV.addObject("Usuario", Usuario);
				mAV.setViewName("login");
			}
		}
		return mAV;
	}

	/**
	 * @param solicitud
	 * @return mAV
	 */
	@GetMapping("logout")
	public ModelAndView desconexion(HttpServletRequest solicitud) {
		ModelAndView mAV = new ModelAndView();
		solicitud.getSession().invalidate();
		mAV.setViewName("redirect:login");
		return mAV;
	}
}
