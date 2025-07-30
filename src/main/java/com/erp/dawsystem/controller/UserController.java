package com.erp.dawsystem.controller;

import com.erp.dawsystem.entity.Role;
import com.erp.dawsystem.entity.User;
import com.erp.dawsystem.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mantenimiento")
public class UserController {

    private final UserService servicio;

    @Autowired
    public UserController(UserService servicio) {
        this.servicio = servicio;
    }

    // Página principal con filtro por nombre y rol
    @GetMapping("/gestion_usuario")
    public String mostrarGestionUsuario(@RequestParam(required = false) String filtro,
                                        @RequestParam(required = false) Role rol,
                                        @ModelAttribute("mensaje") String mensaje,
                                        Model model) {

        List<User> usuarios = servicio.findAll();

        // Filtro por nombre
        if (filtro != null && !filtro.isEmpty()) {
            usuarios = usuarios.stream()
                    .filter(u -> u.getUsername().toLowerCase().contains(filtro.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filtro por rol
        if (rol != null) {
            usuarios = usuarios.stream()
                    .filter(u -> u.getRole() == rol)
                    .collect(Collectors.toList());
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("filtro", filtro);
        model.addAttribute("rolSeleccionado", rol);
        model.addAttribute("roles", Role.values());
        model.addAttribute("mensaje", mensaje);

        return "mantenimiento/usuario/gestion_usuario";
    }

    // Formulario nuevo usuario
    @GetMapping("/usuario_nuevo")
    public String nuevoUsuario(Model model, @ModelAttribute("error") String error) {
        model.addAttribute("usuario", new User());
        model.addAttribute("roles", Role.values());
        model.addAttribute("error", error); // mostrar mensaje de error si lo hay
        return "mantenimiento/usuario/NuevoUsuario";
    }

    // Guardar usuario nuevo o editado
    @PostMapping("/usuario_guardar")
    public String guardarUsuario(@ModelAttribute User usuario,
                                 RedirectAttributes redirectAttributes) {

        // Validación solo para nuevo usuario
        if (usuario.getId() == null) {
            if (servicio.existsByUsername(usuario.getUsername())) {
                redirectAttributes.addFlashAttribute("error", "⚠️ El nombre de usuario ya está registrado. Intente con otro.");
                return "redirect:/mantenimiento/usuario_nuevo";
            }
            servicio.create(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Usuario registrado exitosamente.");
        } else {
            servicio.update(usuario.getId(), usuario);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Usuario actualizado exitosamente.");
        }

        return "redirect:/mantenimiento/gestion_usuario";
    }

    // Editar usuario
    @GetMapping("/usuario_editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        User usuario = servicio.findById(id).orElse(null);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", Role.values());
        return "mantenimiento/usuario/NuevoUsuario";
    }

    // Eliminar usuario
    @GetMapping("/usuario_eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        servicio.delete(id);
        redirectAttributes.addFlashAttribute("mensaje", "🗑️ Usuario eliminado correctamente.");
        return "redirect:/mantenimiento/gestion_usuario";
    }

    @PostMapping("/usuario/cambiar-password")
    public String cambiarPasswordDesdeNavbar(@RequestParam String newPassword,
                                             @RequestParam String confirmPassword,
                                             RedirectAttributes redirectAttributes,
                                             java.security.Principal principal) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "⚠️ Las contraseñas no coinciden.");
            return "redirect:/";
        }

        String username = principal.getName(); // Usuario autenticado
        servicio.cambiarPassword(username, newPassword);

        redirectAttributes.addFlashAttribute("mensaje", "✅ Contraseña actualizada correctamente.");
        return "redirect:/";
    }









}
