package com.daniel.sistemaacademia.repository;

import com.daniel.sistemaacademia.model.entity.Exercicio;
import com.daniel.sistemaacademia.model.entity.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    List<Exercicio> findAllByGrupoMuscular(GrupoMuscular grupoMuscular);
}
