package com.erp.dawsystem.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            // Obtener nombre de usuario
            String username = auth.getName();

            // Obtener rol y traducirlo
            String rol = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("SIN ROL");

            String rolTraducido = switch (rol) {
                case "ROLE_ADMIN" -> "Administrador";
                case "ROLE_VENDEDOR" -> "Vendedor";
                default -> "Invitado";
            };

            // Agregar atributos al modelo
            model.addAttribute("username", username);
            model.addAttribute("rolUsuario", rolTraducido);
        }
    }
}
