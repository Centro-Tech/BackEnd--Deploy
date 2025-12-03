    package school.sptech.projetoMima.Controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import school.sptech.projetoMima.Model.Fornecedor;
    import school.sptech.projetoMima.Model.Roupa;
    import school.sptech.projetoMima.Repository.FornecedorRepository;
    import school.sptech.projetoMima.Repository.RoupaRepository;

    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import java.util.Random;

    @RestController
    @RequestMapping("/roupas")
    public class RoupaController {

        @Autowired
        private RoupaRepository roupaRepository;

        @Autowired
        private FornecedorRepository fornecedorRepository;

        @GetMapping("/{id}")
        public ResponseEntity<Roupa> buscarPorNome(@PathVariable Integer id) {
            Optional <Roupa> roupaExistente = roupaRepository.findById(id);

            if(roupaExistente.isPresent()) {
                Roupa roupaNova =roupaExistente.get();
                return ResponseEntity.ok(roupaNova);
            }

            return ResponseEntity.notFound().build();

        }




        @GetMapping("/estoque")
        public ResponseEntity<List<Roupa>> buscarEstoque() {
            List<Roupa> estoque = new ArrayList<>();

            for (Roupa r : roupaRepository.findAll()) {
                if (r != null && r.getQuantidade() > 0 && !r.getVendido()) {
                    estoque.add(r);
                }
            }

            if (!estoque.isEmpty()) {
                return ResponseEntity.ok(estoque);
            }

            return ResponseEntity.status(404).body(null);
        }

        @GetMapping("/filtro-data-venda")
        public ResponseEntity<List<Roupa>> buscarFiltroData(
                @RequestParam("inicio") LocalDate inicio,
                @RequestParam("fim") LocalDate fim
        ) {
            List<Roupa> filtroData = roupaRepository.findRoupaByDataVendaBetween(inicio, fim);
            if (filtroData.isEmpty()) {
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.ok(filtroData);


        }

        @PutMapping("/{id}")
        public ResponseEntity<Roupa> venderRoupa(@PathVariable int id, @RequestBody Roupa roupa) {
            Roupa roupaExistente = roupaRepository.findById(id).orElse(null);
            if (roupaExistente == null) {
                return ResponseEntity.status(404).body(null);
            }
            if (roupa.getQuantidade() != null && roupa.getQuantidade() > 0 && roupaExistente.getQuantidade() >= roupa.getQuantidade()) {
                roupaExistente.setQuantidade(roupaExistente.getQuantidade() - roupa.getQuantidade());
            }else {
                return ResponseEntity.status(404).body(null);
            }


            roupaExistente.setVendido(true);
            roupaExistente.setDataVenda(LocalDate.now());

            roupaRepository.save(roupaExistente);
            return ResponseEntity.ok(roupaExistente);
        }




        @PostMapping
        public ResponseEntity<Roupa> cadastrarRoupa(@RequestBody Roupa roupa) {
            if (roupaRepository.existsByCodigoIdentificacao(roupa.getCodigoIdentificacao())) {
                return ResponseEntity.status(400).body(null);
            }

            String nomeRoupa = roupa.getNome().toUpperCase();
            String tamanhoRoupa = roupa.getTamanho().toUpperCase();


            String codigoIdentificacao = null;

            if (nomeRoupa.contains("BERMUDA")) {
                codigoIdentificacao = "BZ";
            } else if (nomeRoupa.contains("BLAZER")) {
                codigoIdentificacao = "BL";
            } else if (nomeRoupa.contains("BLUSA")) {
                codigoIdentificacao = "BL";
            } else if (nomeRoupa.contains("BRACELETE")) {
                codigoIdentificacao = "BR";
            } else if (nomeRoupa.contains("BRINCO")) {
                codigoIdentificacao = "BC";
            } else if (nomeRoupa.contains("CALÇA")) {
                codigoIdentificacao = "CL";
            } else if (nomeRoupa.contains("CAMISA")) {
                codigoIdentificacao = "CA";
            } else if (nomeRoupa.contains("CAMISETA")) {
                codigoIdentificacao = "BL";
            } else if (nomeRoupa.contains("CARDIGAN")) {
                codigoIdentificacao = "TR";
            } else if (nomeRoupa.contains("CHEMISE")) {
                codigoIdentificacao = "CH";
            } else if (nomeRoupa.contains("COLAR")) {
                codigoIdentificacao = "CR";
            } else if (nomeRoupa.contains("CONJUNTO")) {
                codigoIdentificacao = "CO";
            } else if (nomeRoupa.contains("CROPPED")) {
                codigoIdentificacao = "BL";
            } else if (nomeRoupa.contains("ELASTICO")) {
                codigoIdentificacao = "EL";
            } else if (nomeRoupa.contains("JAQUETA")) {
                codigoIdentificacao = "JA";
            } else if (nomeRoupa.contains("LENÇO")) {
                codigoIdentificacao = "LE";
            } else if (nomeRoupa.contains("MACACÃO")) {
                codigoIdentificacao = "MA";
            } else if (nomeRoupa.contains("MACAQUINHO")) {
                codigoIdentificacao = "MA";
            } else if (nomeRoupa.contains("PARKA")) {
                codigoIdentificacao = "PK";
            } else if (nomeRoupa.contains("PONCHO")) {
                codigoIdentificacao = "TR";
            } else if (nomeRoupa.contains("PULSEIRA")) {
                codigoIdentificacao = "PU";
            } else if (nomeRoupa.contains("REGATA")) {
                codigoIdentificacao = "BL";
            } else if (nomeRoupa.contains("SAIA")) {
                codigoIdentificacao = "SA";
            } else if (nomeRoupa.contains("SHORT")) {
                codigoIdentificacao = "SH";
            } else if (nomeRoupa.contains("TOMARA QUE CAIA")) {
                codigoIdentificacao = "BL";
            } else if (nomeRoupa.contains("TRICOT")) {
                codigoIdentificacao = "TR";
            } else if (nomeRoupa.contains("T-SHIRT")) {
                codigoIdentificacao = "BL";
            } else if (nomeRoupa.contains("VESTIDO")) {
                codigoIdentificacao = "VE";
            } else {
                return ResponseEntity.status(400).body(null);
            }

            Random random = new Random();
            int numeroAleatorio = 1000000 + random.nextInt(9000000);

            String codigoFinal = codigoIdentificacao + numeroAleatorio + tamanhoRoupa;

            roupa.setCodigoIdentificacao(codigoFinal);
            roupa.setDataRegistro(LocalDate.now());

            Optional<Fornecedor> fornecedor = fornecedorRepository.findById(roupa.getFornecedorId());
            if (!fornecedor.isPresent()) {
                return ResponseEntity.status(404).body(null);
            }

            Fornecedor fornecedorExistente = fornecedor.get();
            roupa.setFornecedorId(fornecedorExistente.getId());


            Roupa novaRoupa = roupaRepository.save(roupa);

            return ResponseEntity.status(201).body(novaRoupa);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletarRoupa(@PathVariable int id){
            if(roupaRepository.existsById(id)){
                roupaRepository.deleteById(id);
                return ResponseEntity.status(204).build();
            };
            return ResponseEntity.status(404).build();


        }




    }
