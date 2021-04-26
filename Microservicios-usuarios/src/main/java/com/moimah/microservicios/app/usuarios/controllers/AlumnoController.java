package com.moimah.microservicios.app.usuarios.controllers;

import com.moimah.commons.alumno.models.entity.Alumno;
import com.moimah.commons.services.controllers.CommonController;
import com.moimah.microservicios.app.usuarios.services.AlumnoService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable Long id){
        Optional<Alumno> opt = service.findById(id);
        if(!opt.isPresent() || opt.get().getFoto() == null){
            return ResponseEntity.notFound().build();
        }
        Resource image = new ByteArrayResource(opt.get().getFoto());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
        if(!archivo.isEmpty()){
            alumno.setFoto(archivo.getBytes());
        }
        return super.crear(alumno, result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return this.validar(result);
        }

        Optional<Alumno> opt = service.findById(id);
        if(!opt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDb = opt.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
    }

    @PutMapping("/editar-con-foto/{id}")
    public ResponseEntity<?> editarConFoto(@Valid Alumno alumno,
                                           BindingResult result,
                                           @PathVariable Long id,
                                           @RequestParam MultipartFile archivo) throws IOException {
        if(result.hasErrors()){
            return this.validar(result);
        }

        Optional<Alumno> opt = service.findById(id);
        if(!opt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDb = opt.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());

        if(!archivo.isEmpty()){
            alumnoDb.setFoto(archivo.getBytes());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term){
        return ResponseEntity.ok(service.findByNombreOrApellido(term));
    }

}
