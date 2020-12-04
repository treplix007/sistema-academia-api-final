package com.daniel.sistemaacademia.controller;

import com.daniel.sistemaacademia.model.dto.ExercicioDTO;
import com.daniel.sistemaacademia.model.dto.ExercicioTreinoDTO;
import com.daniel.sistemaacademia.model.entity.Exercicio;
import com.daniel.sistemaacademia.model.entity.ExercicioTreino;
import com.daniel.sistemaacademia.model.entity.GrupoMuscular;
import com.daniel.sistemaacademia.model.entity.Treino;
import com.daniel.sistemaacademia.repository.ExercicioRepository;
import com.daniel.sistemaacademia.repository.ExercicioTreinoRepository;
import com.daniel.sistemaacademia.repository.GrupoMuscularRepository;
import com.daniel.sistemaacademia.repository.TreinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercicioTreino")
@RequiredArgsConstructor
public class ExercicioTreinoController {

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private ExercicioTreinoRepository exercicioTreinoRepository;

    @Autowired
    private GrupoMuscularRepository grupoMuscularRepository;

    @GetMapping
    public ResponseEntity findAll() {
        List<ExercicioTreino> list = exercicioTreinoRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/treino")
    public ResponseEntity getByTreino(@PathVariable("id") Long id) {
        Optional<Treino> treino = treinoRepository.findById(id);
        if (treino.isPresent()) {
            List<ExercicioTreino> list = exercicioTreinoRepository.findAllByTreino(treino.get());
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/exercicio")
    public ResponseEntity getByExercicio(@PathVariable("id") Long id) {
        List<ExercicioTreino> list = new ArrayList<>();
        Optional<GrupoMuscular> grupoMuscular = grupoMuscularRepository.findById(id);
        if (grupoMuscular.isPresent()) {
            List<Exercicio> exercicios = exercicioRepository.findAllByGrupoMuscular(grupoMuscular.get());
            for(Exercicio exercicio : exercicios) {
                List<ExercicioTreino> listExerc = exercicioTreinoRepository.findAllByExercicio(exercicio);
                for(ExercicioTreino exerc : listExerc) {
                    list.add(exerc);
                }
            }
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity save(@RequestBody ExercicioTreinoDTO exercicioDTO) {
        try {
            Optional<Treino> treino = treinoRepository.findById(exercicioDTO.getTreino());
            Optional<Exercicio> exercicio = exercicioRepository.findById(exercicioDTO.getExercicio());
            if (treino.isPresent() && exercicio.isPresent()) {
                ExercicioTreino exercicioTreino = new ExercicioTreino().converter(exercicioDTO, treino.get(), exercicio.get());
                exercicioTreino = exercicioTreinoRepository.save(exercicioTreino);
                return ResponseEntity.ok(exercicioTreino);
            } else {
                return ResponseEntity.badRequest().body("Exercicio ou Treino não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
