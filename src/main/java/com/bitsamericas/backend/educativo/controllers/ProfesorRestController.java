package com.bitsamericas.backend.educativo.controllers;

import com.bitsamericas.backend.educativo.models.entity.Profesor;
import com.bitsamericas.backend.educativo.models.services.IProfesorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/profesor")
public class ProfesorRestController {
    @Autowired
    private IProfesorService profesorService;

    private final Logger log = LoggerFactory.getLogger(ProfesorRestController.class);

    @GetMapping("/listar")
    public List<Profesor> listar() {
        return profesorService.findAll();
    }

    @GetMapping("/listar/page/{page}")
    public Page<Profesor> listarPerPage(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return profesorService.findAll(pageable);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Profesor profesor, BindingResult result) {
        Profesor profesorNew = null;
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
            profesorNew = profesorService.save(profesor);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El profesor ha sido creado con éxito");
        response.put("profesor", profesorNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Profesor profesor, BindingResult result, @PathVariable Long id) {
        Profesor profesorActual = profesorService.findById(id);
        Profesor profesorUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(profesorActual == null) {
            response.put("mensaje", "Error: No se pudo editar, el profesor ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            profesorActual.setApellido(profesor.getApellido());
            profesorActual.setNombre(profesor.getNombre());
            profesorActual.setEmail(profesor.getEmail());
            profesorActual.setCreateAt(profesor.getCreateAt());

            profesorUpdated = profesorService.save(profesorActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el profesor en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El profesor ha sido actualizado con éxito");
        response.put("profesor", profesorUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Profesor profesor = profesorService.findById(id);

            profesorService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el profesor en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El profesor ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/mostrar/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id) {
        Profesor profesor = null;
        Map<String, Object> response = new HashMap<>();

        try {
            profesor = profesorService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(profesor == null) {
            response.put("mensaje", "El profesor ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Profesor>(profesor, HttpStatus.OK);
    }
}
