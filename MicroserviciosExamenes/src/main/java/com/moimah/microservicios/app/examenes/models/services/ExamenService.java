package com.moimah.microservicios.app.examenes.models.services;


import com.moimah.commons.examenes.models.entity.Asignatura;
import com.moimah.commons.examenes.models.entity.Examen;
import com.moimah.commons.services.services.CommonService;

import java.util.List;

public interface ExamenService extends CommonService<Examen> {
    public List<Examen> findByNombre(String term);

    public List<Asignatura> findAllAsignaturas();
}
