package com.daniel.sistemaacademia.repository;

import com.daniel.sistemaacademia.model.entity.Aluno;
import com.daniel.sistemaacademia.model.entity.Treino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {

    List<Treino> findAllByAluno(Aluno aluno);

    void deleteAllByAluno(Aluno aluno);
}
