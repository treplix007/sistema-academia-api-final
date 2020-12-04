package com.daniel.sistemaacademia.model.entity;

import com.daniel.sistemaacademia.model.dto.ExercicioTreinoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table( name = "exercicio_treino" , schema = "academias")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExercicioTreino {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_treino")
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "id_exercicio")
    private Exercicio exercicio;

    @Column(name = "repeticoes")
    private Integer repeticoes;

    @Column(name = "carga")
    private Integer carga;

    @Column(name = "series")
    private Integer series;

    public ExercicioTreino converter(ExercicioTreinoDTO exercicioDTO, Treino treino, Exercicio exercicio) {
        ExercicioTreino exercicioTreino = new ExercicioTreino();
        exercicioTreino.setTreino(treino);
        exercicioTreino.setExercicio(exercicio);
        exercicioTreino.setRepeticoes(exercicioDTO.getRepeticoes());
        exercicioTreino.setCarga(exercicioDTO.getCarga());
        exercicioTreino.setSeries(exercicioDTO.getSeries());
        return exercicioTreino;
    }
}
