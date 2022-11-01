
package com.portfolio.rac.Seguridad.Controlador;

import com.portfolio.rac.Seguridad.Dto.JwtDto;
import com.portfolio.rac.Seguridad.Dto.LoginUsuario;
import com.portfolio.rac.Seguridad.Dto.NuevoUsuario;
import com.portfolio.rac.Seguridad.Entidad.Rol;
import com.portfolio.rac.Seguridad.Entidad.Usuario;
import com.portfolio.rac.Seguridad.Enums.RolNombre;
import com.portfolio.rac.Seguridad.Servicio.RolServicio;
import com.portfolio.rac.Seguridad.Servicio.UsuarioServicio;
import com.portfolio.rac.Seguridad.Jwt.JwtProvider;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
 @CrossOrigin(origins = {"http://localhost:4200", "https://frontendrac-407e5.web.app"})
//@CrossOrigin(origins = "https://frontendrac-407e5.web.app")
public class AuthControlador {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    RolServicio rolServicio;
    @Autowired
    JwtProvider jwtProvider;
    
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email invalido"),HttpStatus.BAD_REQUEST);
        
        if(usuarioServicio.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        
        if(usuarioServicio.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("Ese mail ya existe"), HttpStatus.BAD_REQUEST);
        
        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(),
            nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));
        
        Set<Rol> roles = new HashSet<>();
        roles.add(rolServicio.getByRolNombre(RolNombre.ROLE_USER).get());
        
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolServicio.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioServicio.save(usuario);
        
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
           
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
            if(bindingResult.hasErrors())
                return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
            
            Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            String jwt = jwtProvider.generateToken(authentication);
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
            
            return new ResponseEntity(jwtDto, HttpStatus.OK);
            
    }
    
}
