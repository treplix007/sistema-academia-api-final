package com.daniel.sistemaacademia.controller;

import com.daniel.sistemaacademia.model.dto.AlunoDTO;
import com.daniel.sistemaacademia.exception.RegraNegocioException;
import com.daniel.sistemaacademia.model.entity.Aluno;
import com.daniel.sistemaacademia.model.entity.AvaliacaoFisica;
import com.daniel.sistemaacademia.model.entity.Treino;
import com.daniel.sistemaacademia.model.entity.Usuario;
import com.daniel.sistemaacademia.model.enums.TipoUsuario;
import com.daniel.sistemaacademia.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AvaliacaoFisicaRepository avaliacaoFisicaRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ExercicioTreinoRepository exercicioTreinoRepository;

    @GetMapping
    public ResponseEntity findAll() {
        List<Aluno> alunos = alunoRepository.findAll();
        List<Aluno> lstAlunos = new ArrayList<>();
        for(Aluno aluno : alunos) {
            if(aluno.getUsuario().getTipoUsuario().equals(TipoUsuario.ALUNO.getValor())) {
                lstAlunos.add(aluno);
            }
        }
        return ResponseEntity.ok(lstAlunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);

        // checando se o aluno com o id está na base de dados
        if(aluno.isPresent() && aluno.get().getUsuario().getTipoUsuario().equals(TipoUsuario.ALUNO.getValor())) {
            return ResponseEntity.ok(aluno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/usuarios")
    public ResponseEntity findByIdUsuario(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        
        if (usuario.isPresent()) {
            List<Aluno> alunos = alunoRepository.findByUsuario(usuario.get());
            System.out.print("Alunos: " + alunos);
            return ResponseEntity.ok(alunos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity save(@RequestBody AlunoDTO dto) {
        try {
            Usuario usuario = new Usuario().converterPorAlunoDTO(dto);
            usuario.setTipoUsuario(dto.getTipoUsuario());
            usuario = usuarioRepository.save(usuario);
            Aluno entidade = new Aluno().converter(dto);
            entidade.setUsuario(usuario);
            entidade = alunoRepository.save(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity edit(@PathVariable("id") Long id, @RequestBody AlunoDTO dto) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if(aluno.isPresent()) {
        	Aluno alunoConvertido = new Aluno().converter(dto);
        	alunoConvertido.setId(id);
        	alunoConvertido.setUsuario(aluno.get().getUsuario());
        	Optional<Usuario> usuario = usuarioRepository.findById(aluno.get().getUsuario().getId());
        	if (usuario.isPresent()) {
        		usuario.get().setEmail(dto.getEmail());
        		usuario.get().setNome(dto.getNome());
        		usuario.get().setSenha(dto.getSenha());
        		usuarioRepository.save(usuario.get());
        		alunoRepository.save(alunoConvertido);
                return ResponseEntity.ok(alunoConvertido);
        	} else {
        		return ResponseEntity.notFound().build();
        	}                 	
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if(aluno.isPresent()) {
            List<Treino> lstTreino = treinoRepository.findAllByAluno(aluno.get());
            for (Treino treino : lstTreino) {
                exercicioTreinoRepository.deleteAllByTreino(treino);
            }
            avaliacaoFisicaRepository.deleteAllByAluno(aluno.get());
            treinoRepository.deleteAllByAluno(aluno.get());
            alunoRepository.delete(aluno.get());
            usuarioRepository.delete(aluno.get().getUsuario());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Aluno não encontrado.");
        }
    }

}
