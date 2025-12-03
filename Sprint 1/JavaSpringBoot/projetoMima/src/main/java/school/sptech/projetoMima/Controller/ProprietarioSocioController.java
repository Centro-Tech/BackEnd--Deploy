package school.sptech.projetoMima.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.projetoMima.Model.ProprietarioSocio;
import school.sptech.projetoMima.Repository.ProprietarioSocioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proprietarios")
public class ProprietarioSocioController {

    @Autowired
    private ProprietarioSocioRepository proprietarioSocioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioSocio> getProprietarioSocio(@PathVariable int id) {
        Optional<ProprietarioSocio> proprietarioExistente = proprietarioSocioRepository.findById(id);

        if(proprietarioExistente.isPresent()){
            ProprietarioSocio proprietario = proprietarioExistente.get();
            return ResponseEntity.ok(proprietario);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ProprietarioSocio>> listarProprietarios() {
        List<ProprietarioSocio> proprietarios = proprietarioSocioRepository.findAll();

        if (proprietarios.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(proprietarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProprietarioSocio> atualizarDados(@RequestBody ProprietarioSocio proprietarioSocio, @PathVariable int id) {
        Optional<ProprietarioSocio> proprietarioExistente = proprietarioSocioRepository.findById(id);

        if (proprietarioExistente.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        ProprietarioSocio pessoaExistente = proprietarioExistente.get();

        if (proprietarioSocio.getCpf() != null) {
            pessoaExistente.setCpf(proprietarioSocio.getCpf());
        }
        if (proprietarioSocio.getNome() != null) {
            pessoaExistente.setNome(proprietarioSocio.getNome());
        }
        if (proprietarioSocio.getPapel() != null) {
            pessoaExistente.setPapel(proprietarioSocio.getPapel());
        }
        if (proprietarioSocio.getEmail() != null) {
            pessoaExistente.setEmail(proprietarioSocio.getEmail());
        }
        if (proprietarioSocio.getTelefone() != null) {
            pessoaExistente.setTelefone(proprietarioSocio.getTelefone());
        }

        proprietarioSocioRepository.save(pessoaExistente);
        return ResponseEntity.ok(pessoaExistente);
    }

    @PostMapping
    public ResponseEntity<ProprietarioSocio> cadastrar(@RequestBody ProprietarioSocio proprietarioSocio) {
        if (proprietarioSocio == null) {
            return ResponseEntity.status(400).build();
        }

        proprietarioSocioRepository.save(proprietarioSocio);
        return ResponseEntity.status(201).body(proprietarioSocio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        if (proprietarioSocioRepository.existsById(id)) {
            proprietarioSocioRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
