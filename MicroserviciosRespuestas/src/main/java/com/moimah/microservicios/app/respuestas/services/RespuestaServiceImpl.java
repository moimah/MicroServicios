package com.moimah.microservicios.app.respuestas.services;

import com.moimah.microservicios.app.respuestas.models.entity.Respuesta;
import com.moimah.microservicios.app.respuestas.models.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository repository;

   /* @Autowired
    private ExamenFeignClient examenClient;*/

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
       return repository.saveAll(respuestas);
    }

    @Override
     public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
       /* Examen examen = examenClient.getExamenById(examenId);
        List<Pregunta> preguntas = examen.getPreguntas();
        List<Long> preguntasIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
        List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByPreguntaId(alumnoId, preguntasIds);
        respuestas = respuestas.stream().map(r -> {
            preguntas.forEach( p -> {
                if(p.getId() == r.getPreguntaId()){
                    r.setPregunta(p);
                }
            });
            return r;
        }).collect(Collectors.toList());*/

        List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
       /* List<Respuesta> respuestaAlumno = (List<Respuesta>) repository.findByAlumnoId(alumnoId);

        List<Long> examenIds = Collections.emptyList();
        if(respuestaAlumno.size() > 0){
            List<Long> preguntaIds = respuestaAlumno.stream().map( r -> r.getPreguntaId()).collect(Collectors.toList());
            examenIds = examenClient.getExamenesIdByPreguntaIdsRespondidas(preguntaIds);
        }*/

        List<Respuesta> respuestaAlumno = (List<Respuesta>) repository.findExamenIdsConRespuestaByAlumno(alumnoId);
        List<Long> examenIds = respuestaAlumno.stream().map( r -> r.getPregunta().getExamen().getId())
                .distinct()
                .collect(Collectors.toList());
        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
        return repository.findByAlumnoId(alumnoId);
    }
}
