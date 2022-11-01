package com.portfolio.rac.Seguridad.Servicio;

import com.portfolio.rac.Seguridad.Entidad.Rol;
import com.portfolio.rac.Seguridad.Enums.RolNombre;
import com.portfolio.rac.Seguridad.Repositorio.IRolRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RolServicio {
    @Autowired
    IRolRepositorio irolRepositorio;
    
    public Optional<Rol> getByRolNombre (RolNombre rolNombre){
        return irolRepositorio.findByRolNombre(rolNombre);
    }
    public void save(Rol rol){
        irolRepositorio.save(rol);
    }
}
