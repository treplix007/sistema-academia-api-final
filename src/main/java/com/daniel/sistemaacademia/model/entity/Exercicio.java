package com.daniel.sistemaacademia.model.entity;

import com.daniel.sistemaacademia.model.dto.ExercicioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table( name = "exercicio" , schema = "academias")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercicio {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_grupo_muscular")
    private GrupoMuscular grupoMuscular;

    @Column(name = "nome")
    private String nome;

    @Column(name = "dica")
    private String dica;

    public Exercicio converter(ExercicioDTO dto, GrupoMuscular grupMuscular) {
        Exercicio exercicio = new Exercicio();
        exercicio.setNome(dto.getNome());
        exercicio.setDica(dto.getDica());
        exercicio.setGrupoMuscular(grupMuscular);
        return exercicio;
    }

}
