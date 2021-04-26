package com.moimah.microservicios.app.examenes.controllers;

import com.moimah.commons.examenes.models.entity.Examen;
import com.moimah.commons.services.controllers.CommonController;
import com.moimah.microservicios.app.examenes.models.services.ExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

    @GetMapping("/respondidos-por-preguntas")
    public ResponseEntity<?> getExamenesIdByPreguntaIdsRespondidas(@RequestParam List<Long> preguntaIds){
        return ResponseEntity.ok(service.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen,BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return this.validar(result);
        }

        Optional<Examen> o = service.findById(id);

        if(!o.isPresent())
            return ResponseEntity.notFound().build();
        Examen examenDb = o.get();
        examenDb.setNombre(examen.getNombre());

        examenDb.getPreguntas().stream()
                .filter(pdb -> !examen.getPreguntas().contains(pdb))
                .forEach(examenDb::removePregunta);
        /*List<Pregunta> eliminadas = new ArrayList<>();
        examenDb.getPreguntas().forEach(pdb -> {
            if(!examen.getPreguntas().contains(pdb))
                eliminadas.add(pdb);
        });
        eliminadas.forEach(examenDb::removePregunta);
         */

        examenDb.setPreguntas(examen.getPreguntas());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> findByNombre(@PathVariable  String term){
        return ResponseEntity.ok(service.findByNombre(term));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas(){
        return ResponseEntity.ok(service.findAllAsignaturas());
    }
}
