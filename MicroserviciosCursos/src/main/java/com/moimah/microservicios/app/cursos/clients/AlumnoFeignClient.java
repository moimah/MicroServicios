package com.moimah.microservicios.app.cursos.clients;

import com.moimah.commons.alumno.models.entity.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservicio-usuarios")
public interface AlumnoFeignClient {

    @GetMapping("/alumnos-por-curso")
    public Iterable<Alumno> getAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
