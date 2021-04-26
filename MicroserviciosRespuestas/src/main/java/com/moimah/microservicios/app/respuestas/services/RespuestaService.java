package com.moimah.microservicios.app.respuestas.services;

import com.moimah.microservicios.app.respuestas.models.entity.Respuesta;


public interface RespuestaService  {
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

    public  Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
