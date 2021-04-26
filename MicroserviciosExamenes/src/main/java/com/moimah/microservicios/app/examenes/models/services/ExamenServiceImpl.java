package com.moimah.microservicios.app.examenes.models.services;

import com.moimah.commons.examenes.models.entity.Asignatura;
import com.moimah.commons.examenes.models.entity.Examen;
import com.moimah.commons.services.services.CommonServiceImpl;
import com.moimah.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.moimah.microservicios.app.examenes.models.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String term) {
        return repository.findByNombre(term);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> findAllAsignaturas() {
        return (List<Asignatura>) asignaturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds) {
        return repository.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds);
    }
}
