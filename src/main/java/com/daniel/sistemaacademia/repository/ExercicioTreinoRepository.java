package com.daniel.sistemaacademia.repository;

import com.daniel.sistemaacademia.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercicioTreinoRepository extends JpaRepository<ExercicioTreino, Long> {

    List<ExercicioTreino> findAllByTreino(Treino treino);

    List<ExercicioTreino> findAllByExercicio(Exercicio exercicio);

    void deleteAllByTreino(Treino treino);

    void deleteAllByExercicio(Exercicio exercicio);
}
