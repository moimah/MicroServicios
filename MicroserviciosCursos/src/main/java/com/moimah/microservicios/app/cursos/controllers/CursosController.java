package com.moimah.microservicios.app.cursos.controllers;

import com.moimah.commons.alumno.models.entity.Alumno;
import com.moimah.commons.examenes.models.entity.Examen;
import com.moimah.commons.services.controllers.CommonController;
import com.moimah.microservicios.app.cursos.models.entity.Curso;
import com.moimah.microservicios.app.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursosController extends CommonController<Curso, CursoService> {

    @Value("${config.balanceador.test}")
    private String balanceadorTest;

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest(){
        Map<String, Object>response = new HashMap<>();
        response.put("balanceador", balanceadorTest);
        response.put("cursos", service.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return this.validar(result);
        }
        Optional<Curso> opt = service.findById(id);
        if(!opt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = opt.get();
        cursoDb.setNombre(curso.getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id){
        Optional<Curso> opt = service.findById(id);
        if(!opt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = opt.get();

        alumnos.forEach(alumno -> {
            cursoDb.addAlumno(alumno);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));

    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id){
        Optional<Curso> opt = service.findById(id);
        if(!opt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = opt.get();

       cursoDb.removeAlumno(alumno);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));

    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> findCursoByAlumnoId(@PathVariable Long id){
        Curso curso = service.findCursoByAlumnoId(id);

        if(curso != null){
            List<Long> examenesId = (List<Long>) service.findExamenesIdsConRespuestasByAlumno(id);
            List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                if(examenesId.contains(examen.getId())){
                    examen.setRespondido(true);
                }
                return examen;
            }).collect(Collectors.toList());

            curso.setExamenes(examenes);
        }
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examen, @PathVariable Long id){
        Optional<Curso> opt = service.findById(id);
        if(!opt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = opt.get();

        examen.forEach(e -> {
            cursoDb.addExamen(e);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));

    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id){
        Optional<Curso> opt = service.findById(id);
        if(!opt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = opt.get();

        cursoDb.removeExamen(examen);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));

    }


}
