
package com.portfolio.rac.Controlador;

import com.portfolio.rac.Dto.dtoPersona;
import com.portfolio.rac.Entidad.Persona;
import com.portfolio.rac.Seguridad.Controlador.Mensaje;
import com.portfolio.rac.Servicio.ImpPersonaServicio;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personas")
 @CrossOrigin(origins = {"http://localhost:4200", "https://frontendrac-407e5.web.app"})
//@CrossOrigin(origins =  "https://frontendrac-407e5.web.app")
public class PersonaControlador {
    @Autowired
    ImpPersonaServicio personaServicio;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list(){
        List<Persona> list = personaServicio.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id){
        if(!personaServicio.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Persona persona = personaServicio.getOne(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    } 
    
    /*@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!personaServicio.existsById(id)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        personaServicio.delete(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }*/
    
    /*@PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody dtoPersona dtopersona){      
        if(StringUtils.isBlank(dtopersona.getNombreE()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(personaServicio.existsByNombreE(dtopersona.getNombreE()))
            return new ResponseEntity(new Mensaje("Esa experiencia existe"), HttpStatus.BAD_REQUEST);
        
        Persona experiencia = new Persona(dtopersona.getNombreE(), dtopersona.getDescripcionE());
        personaServicio.save(experiencia);
        
        return new ResponseEntity(new Mensaje("Persona agregada"), HttpStatus.OK);
    }*/
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody dtoPersona dtopersona){
        //Validamos si existe el ID
        if(!personaServicio.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        //Compara nombre de experiencias
        if(personaServicio.existsByNombre(dtopersona.getNombre()) && personaServicio.getByNombre(dtopersona.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("Esa experiencia ya existe"), HttpStatus.BAD_REQUEST);
        //No puede estar vacio
        if(StringUtils.isBlank(dtopersona.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        
        Persona persona = personaServicio.getOne(id).get();
        persona.setNombre(dtopersona.getNombre());
        persona.setApellido(dtopersona.getApellido());
        persona.setDescripcion((dtopersona.getDescripcion()));
        persona.setImg(dtopersona.getImg());
        personaServicio.save(persona);
        return new ResponseEntity(new Mensaje("Persona actualizada"), HttpStatus.OK);
    }
}
