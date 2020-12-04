package com.daniel.sistemaacademia.controller;

import com.daniel.sistemaacademia.model.dto.ExercicioDTO;
import com.daniel.sistemaacademia.model.dto.TreinoDTO;
import com.daniel.sistemaacademia.model.entity.*;
import com.daniel.sistemaacademia.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercicios")
@RequiredArgsConstructor
public class ExercicioController {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private GrupoMuscularRepository grupoMuscularRepository;

    @Autowired
    private ExercicioTreinoRepository exercicioTreinoRepository;

    @GetMapping
    public ResponseEntity findAll() {
        List<Exercicio> exercicios = exercicioRepository.findAll();
        return ResponseEntity.ok(exercicios);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        Optional<Exercicio> exercicio = exercicioRepository.findById(id);
        if(exercicio.isPresent()) {
            return ResponseEntity.ok(exercicio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/grupoMuscular")
    public ResponseEntity findByIdGrupoMuscular(@PathVariable("id") Long id) {
        Optional<GrupoMuscular> grupoMuscular = grupoMuscularRepository.findById(id);
        if(grupoMuscular.isPresent()) {
            List<Exercicio> exercicios = exercicioRepository.findAllByGrupoMuscular(grupoMuscular.get());
            return ResponseEntity.ok(exercicios);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity save(@RequestBody ExercicioDTO exercicioDTO) {
        try {
            Optional<GrupoMuscular> grupoMuscular = grupoMuscularRepository.findById(exercicioDTO.getGrupoMuscular());
            if (grupoMuscular.isPresent()) {
                Exercicio exercicio = new Exercicio().converter(exercicioDTO, grupoMuscular.get());
                exercicio = exercicioRepository.save(exercicio);
                return ResponseEntity.ok(exercicio);
            } else {
                return ResponseEntity.badRequest().body("Grupo Muscular não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Exercicio> exercicio = exercicioRepository.findById(id);
        if(exercicio.isPresent()) {
            exercicioRepository.delete(exercicio.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Exercicio não encontrado.");
        }
    }

}
