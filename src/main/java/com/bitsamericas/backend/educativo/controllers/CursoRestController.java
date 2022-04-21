package com.bitsamericas.backend.educativo.controllers;

import com.bitsamericas.backend.educativo.models.entity.Curso;
import com.bitsamericas.backend.educativo.models.services.ICursoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/curso")
public class CursoRestController {

    @Autowired
    private ICursoService cursoService;

    private final Logger log = LoggerFactory.getLogger(CursoRestController.class);

    @GetMapping("/listar")
    public List<Curso> listar() {
        return cursoService.findAll();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        Curso cursoNew = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            cursoNew = cursoService.save(curso);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El curso ha sido creado con éxito");
        response.put("curso", cursoNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        Curso cursoActual = cursoService.findById(id);
        Curso cursoUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(cursoActual == null) {
            response.put("mensaje", "Error: No se pudo editar, el curso ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            cursoActual.setNombre(curso.getNombre());
            cursoActual.setProfesor(curso.getProfesor());

            cursoUpdated = cursoService.save(cursoActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el curso en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El curso ha sido actualizado con éxito");
        response.put("curso", cursoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Curso curso = cursoService.findById(id);

            cursoService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el curso en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El curso ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/mostrar/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id) {
        Curso curso = null;
        Map<String, Object> response = new HashMap<>();

        try {
            curso = cursoService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(curso == null) {
            response.put("mensaje", "El curso ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Curso>(curso, HttpStatus.OK);
    }
}
