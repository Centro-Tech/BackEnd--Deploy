package school.sptech.projetoMima.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.Model.Funcionario;
import school.sptech.projetoMima.Repository.FuncionarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncionario(@PathVariable int id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
     List<Funcionario> funcionarios = new ArrayList<>();
     for(Funcionario f : funcionarioRepository.findAll()) {
         funcionarios.add(f);
     }
     if(funcionarios.isEmpty()) {
         return ResponseEntity.status(404).build();
     }
     return ResponseEntity.ok(funcionarios);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@RequestBody Funcionario funcionario, @PathVariable int id) {
        Optional<Funcionario> funcionarioExistente = funcionarioRepository.findById(id);

        if (funcionarioExistente.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Funcionario funcionarioAtualizar = funcionarioExistente.get();

        if (funcionario.getNome() != null) {
            funcionarioAtualizar.setNome(funcionario.getNome());
        }
        if (funcionario.getCargo() != null) {
            funcionarioAtualizar.setCargo(funcionario.getCargo());
        }
        if (funcionario.getEmail() != null) {
            funcionarioAtualizar.setEmail(funcionario.getEmail());
        }
        if (funcionario.getTelefone() != null) {
            funcionarioAtualizar.setTelefone(funcionario.getTelefone());
        }

        funcionarioRepository.save(funcionarioAtualizar);
        return ResponseEntity.ok(funcionarioAtualizar);
    }

    @PostMapping
    public ResponseEntity<Funcionario> inserirFuncionario(@RequestBody Funcionario funcionario) {
        if (funcionario.getEmail() == null || funcionario.getTelefone() == null) {
            return ResponseEntity.status(400).build();
        }

        if (funcionarioRepository.existsByEmail(funcionario.getEmail()) || funcionarioRepository.existsByTelefone(funcionario.getTelefone())) {
            return ResponseEntity.status(422).build();
        }

        Funcionario novoFuncionario = funcionarioRepository.save(funcionario);
        return ResponseEntity.status(201).body(novoFuncionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable int id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
