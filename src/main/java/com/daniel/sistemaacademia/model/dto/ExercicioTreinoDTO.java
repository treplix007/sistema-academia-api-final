package com.daniel.sistemaacademia.model.dto;

import com.daniel.sistemaacademia.model.entity.Exercicio;
import com.daniel.sistemaacademia.model.entity.Treino;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExercicioTreinoDTO {

    private Long treino;
    private Long exercicio;
    private Integer repeticoes;
    private Integer carga;
    private Integer series;
}
