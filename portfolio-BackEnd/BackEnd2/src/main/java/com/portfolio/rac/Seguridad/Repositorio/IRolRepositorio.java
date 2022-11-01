
package com.portfolio.rac.Seguridad.Repositorio;

import com.portfolio.rac.Seguridad.Entidad.Rol;
import com.portfolio.rac.Seguridad.Enums.RolNombre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepositorio extends JpaRepository<Rol, Integer>{
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
