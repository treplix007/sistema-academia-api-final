package com.daniel.sistemaacademia.controller;

import com.daniel.sistemaacademia.model.dto.GrupoMuscularDTO;
import com.daniel.sistemaacademia.model.dto.TreinoDTO;
import com.daniel.sistemaacademia.model.entity.Aluno;
import com.daniel.sistemaacademia.model.entity.GrupoMuscular;
import com.daniel.sistemaacademia.model.entity.Treino;
import com.daniel.sistemaacademia.model.entity.Usuario;
import com.daniel.sistemaacademia.repository.GrupoMuscularRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/grupoMuscular")
@RequiredArgsConstructor
public class GrupoMuscularController {

    @Autowired
    private GrupoMuscularRepository grupoMuscularRepository;

    @GetMapping
    public ResponseEntity findAll() {
        try {
            List<GrupoMuscular> grupoMuscularList = grupoMuscularRepository.findAll();
            return ResponseEntity.ok(grupoMuscularList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            Optional<GrupoMuscular> grupoMuscular = grupoMuscularRepository.findById(id);
            if(grupoMuscular.isPresent()) {
                return ResponseEntity.ok(grupoMuscular);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity save(@RequestBody GrupoMuscularDTO grupoMuscularDTO) {
        try {
            GrupoMuscular grupoMuscular = new GrupoMuscular();
            grupoMuscular.setNome(grupoMuscularDTO.getNome());
            grupoMuscular = grupoMuscularRepository.save(grupoMuscular);
            return ResponseEntity.ok(grupoMuscular);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<GrupoMuscular> grupoMuscular = grupoMuscularRepository.findById(id);
        if(grupoMuscular.isPresent()) {
            grupoMuscularRepository.delete(grupoMuscular.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Grupo Muscular não encontrado.");
        }
    }

}
