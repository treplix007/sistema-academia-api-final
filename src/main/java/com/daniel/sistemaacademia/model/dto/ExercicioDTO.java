package com.daniel.sistemaacademia.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExercicioDTO {

    private String nome;
    private String dica;
    private Long grupoMuscular;
}
