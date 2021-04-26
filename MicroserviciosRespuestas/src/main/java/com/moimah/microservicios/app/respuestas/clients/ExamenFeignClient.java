package com.moimah.microservicios.app.respuestas.clients;

import com.moimah.commons.examenes.models.entity.Examen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-examenes")
public interface ExamenFeignClient {

    @GetMapping("/{id}")
    public Examen getExamenById(@PathVariable Long id);

    @GetMapping("/respondidos-por-preguntas")
    public List<Long> getExamenesIdByPreguntaIdsRespondidas(@RequestParam List<Long> preguntaIds);


}
