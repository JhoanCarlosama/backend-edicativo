package com.bitsamericas.backend.educativo.controllers;

import com.bitsamericas.backend.educativo.models.entity.Examen;
import com.bitsamericas.backend.educativo.models.services.IExamenService;
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
@RequestMapping("/api/examen")
public class ExamenRestController {

    @Autowired
    private IExamenService examenService;

    private final Logger log = LoggerFactory.getLogger(ExamenRestController.class);

    @GetMapping("/listar")
    public List<Examen> listar() {
        return examenService.findAll();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Examen examen, BindingResult result) {
        Examen examenNew = null;
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
            examenNew = examenService.save(examen);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El examen ha sido creado con éxito");
        response.put("examen", examenNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {
        Examen examenActual = examenService.findById(id);
        Examen examenUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(examenActual == null) {
            response.put("mensaje", "Error: No se pudo editar, el examen ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            examenActual.setNombre(examen.getNombre());
            examenActual.setFecha(examen.getFecha());
            examenActual.setInicio(examen.getInicio());
            examenActual.setFin(examen.getFin());
            examenActual.setCurso(examen.getCurso());

            examenUpdated = examenService.save(examenActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el examen en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El examen ha sido actualizado con éxito");
        response.put("examen", examenUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Examen examen = examenService.findById(id);

            examenService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El examen ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/mostrar/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id) {
        Examen examen = null;
        Map<String, Object> response = new HashMap<>();

        try {
            examen = examenService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(examen == null) {
            response.put("mensaje", "El examen ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Examen>(examen, HttpStatus.OK);
    }
}
