package com.moimah.microservicios.app.cursos.controllers;

import com.moimah.commons.alumno.models.entity.Alumno;
import com.moimah.commons.examenes.models.entity.Examen;
import com.moimah.commons.services.controllers.CommonController;
import com.moimah.microservicios.app.cursos.models.entity.Curso;
import com.moimah.microservicios.app.cursos.models.entity.CursoAlumno;
import com.moimah.microservicios.app.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<?> deleteCursoAlumnoById(@PathVariable Long id){
        service.deleteCursoAlumnoById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    @Override
    public ResponseEntity<?> listar() {
        List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map( c ->{
            c.getCursoAlumnos().forEach(ca ->{
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                c.addAlumno(alumno);
            });
            return c;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(cursos);
    }

    @GetMapping({"/pagina"})
    @Override
    public ResponseEntity<?> listar(Pageable pageable) {
        Page<Curso> cursos = service.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos().forEach(ca ->{
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                curso.addAlumno(alumno);
            });
            return curso;
        });
        return ResponseEntity.ok(cursos);
    }


    @GetMapping({"/{id}"})
    @Override
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Curso> opt = this.service.findById(id);
        if(!opt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Curso curso = opt.get();

        if(curso.getCursoAlumnos().isEmpty() == false){
            List<Long> ids = curso.getCursoAlumnos().stream().map(ca -> ca.getAlumnoId())
                    .collect(Collectors.toList());
            List<Alumno> alumnos = (List<Alumno>) service.getAlumnosPorCurso(ids);

            curso.setAlumnos(alumnos);
        }
        return ResponseEntity.ok(curso);
    }

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
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(alumno.getId());
            cursoAlumno.setCurso(cursoDb);
            cursoDb.addCursoAlumnos(cursoAlumno);
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

        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());
        cursoDb.removeCursoAlumnos(cursoAlumno);

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
