
package com.portfolio.rac.Servicio;

import com.portfolio.rac.Entidad.Persona;
import com.portfolio.rac.Repositorio.IPersonaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class ImpPersonaServicio  {
    @Autowired 
    IPersonaRepositorio ipersonaRepositorio;
    
    public List<Persona> list(){
        return ipersonaRepositorio.findAll();
    }
    
    public Optional<Persona> getOne(int id){
         return ipersonaRepositorio.findById(id);
     }
     
     public Optional<Persona> getByNombre(String nombre){
         return ipersonaRepositorio.findByNombre(nombre);
     }
     
     public void save(Persona persona){
         ipersonaRepositorio.save(persona);
     }
     
     public void delete(int id){
         ipersonaRepositorio.deleteById(id);
     }
     
     public boolean existsById(int id){
         return ipersonaRepositorio.existsById(id);
     }
     
     public boolean existsByNombre(String nombre){
         return ipersonaRepositorio.existsByNombre(nombre);
     }
   
        
    
}
