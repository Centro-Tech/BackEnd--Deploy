package school.sptech.projetoMima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.projetoMima.dto.itemDto.ItemRequestDto;
import school.sptech.projetoMima.entity.Fornecedor;
import school.sptech.projetoMima.entity.item.*;
import school.sptech.projetoMima.exception.Item.ItemCampoVazioException;
import school.sptech.projetoMima.exception.Item.ItemNaoEncontradoException;
import school.sptech.projetoMima.exception.Item.ItemQuantidadeInvalida;
import school.sptech.projetoMima.repository.ItemRepository;
import school.sptech.projetoMima.repository.FornecedorRepository;
import school.sptech.projetoMima.repository.auxiliares.CategoriaRepository;
import school.sptech.projetoMima.repository.auxiliares.CorRepository;
import school.sptech.projetoMima.repository.auxiliares.MaterialRepository;
import school.sptech.projetoMima.repository.auxiliares.TamanhoRepository;
import school.sptech.projetoMima.service.auxiliares.CategoriaService;
import school.sptech.projetoMima.service.auxiliares.CorService;
import school.sptech.projetoMima.service.auxiliares.MaterialService;
import school.sptech.projetoMima.service.auxiliares.TamanhoService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private TamanhoRepository tamanhoRepository;
    @Autowired
    private CorRepository corRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    public Item buscarPorId(int id) {
        return itemRepository.findById(id).orElseThrow(() -> new ItemNaoEncontradoException("Item com o id " + id + " não encontrado."));
    }

    public List<Item> listarEstoque() {
        return itemRepository.findAll();
    }

    public void deletar(Item itemParaDeletar) {
        itemRepository.delete(itemParaDeletar);
    }

    public boolean existsByCodigo(String codigo) {
        return itemRepository.existsByCodigo(codigo);
    }

    public boolean isCategoriaValida(String nome) {
        return nome.contains("BERMUDA") || nome.contains("BLAZER") || nome.contains("BLUSA") ||
                nome.contains("BRACELETE") || nome.contains("BRINCO") || nome.contains("CALÇA") ||
                nome.contains("CAMISA") || nome.contains("CAMISETA") || nome.contains("CARDIGAN") ||
                nome.contains("CHEMISE") || nome.contains("COLAR") || nome.contains("CONJUNTO") ||
                nome.contains("CROPPED") || nome.contains("ELASTICO") || nome.contains("JAQUETA") ||
                nome.contains("LENÇO") || nome.contains("MACACÃO") || nome.contains("MACAQUINHO") ||
                nome.contains("PARKA") || nome.contains("PONCHO") || nome.contains("PULSEIRA") ||
                nome.contains("REGATA") || nome.contains("SAIA") || nome.contains("SHORT") ||
                nome.contains("TOMARA QUE CAIA") || nome.contains("TRICOT") || nome.contains("T-SHIRT") ||
                nome.contains("VESTIDO");
    }


    public void validarPreco(Item item) {
        if (item.getPreco() == null) {
            throw new ItemCampoVazioException("Preço inválido ou vazio");
        }
        try {
            double precoNumerico = Double.parseDouble(item.getPreco().toString());
            if (precoNumerico <= 0) {
                throw new ItemCampoVazioException("Preço inválido ou vazio");
            }
        } catch (NumberFormatException e) {
            throw new ItemCampoVazioException("Preço inválido ou vazio");
        }
    }


    public Integer validarQuantidade(Item item) {
        if (item.getQtdEstoque() <= 0) {
            throw new ItemQuantidadeInvalida("Quantidade deve ser maior que zero");
        }

        return item.getQtdEstoque();
    }

    public boolean validarCaracteres(Item item) {

        String texto = item.getCategoria().getNome().toUpperCase();

        if (texto == null || texto.isBlank()) return false;

        String caracteresValidos = "^[\\p{L}0-9]+$";

        return !texto.matches(caracteresValidos);
    }

    public Item cadastrarItem(Item item, Fornecedor fornecedor, ItemRequestDto dto) {
        String nome = item.getCategoria().getNome().toUpperCase();
        String tamanho = item.getTamanho().getNome().toUpperCase();
        String codigoIdentificacao = null;

       validarCampoVazio(item);
       validarPreco(item);
       validarQuantidade(item);
       validarCaracteres(item);

        if (nome.contains("BERMUDA")) codigoIdentificacao = "BZ";
        else if (nome.contains("BLAZER")) codigoIdentificacao = "BL";
        else if (nome.contains("BLUSA")) codigoIdentificacao = "BL";
        else if (nome.contains("BRACELETE")) codigoIdentificacao = "BR";
        else if (nome.contains("BRINCO")) codigoIdentificacao = "BC";
        else if (nome.contains("CALÇA")) codigoIdentificacao = "CL";
        else if (nome.contains("CAMISA")) codigoIdentificacao = "CA";
        else if (nome.contains("CAMISETA")) codigoIdentificacao = "BL";
        else if (nome.contains("CARDIGAN")) codigoIdentificacao = "TR";
        else if (nome.contains("CHEMISE")) codigoIdentificacao = "CH";
        else if (nome.contains("COLAR")) codigoIdentificacao = "CR";
        else if (nome.contains("CONJUNTO")) codigoIdentificacao = "CO";
        else if (nome.contains("CROPPED")) codigoIdentificacao = "BL";
        else if (nome.contains("ELASTICO")) codigoIdentificacao = "EL";
        else if (nome.contains("JAQUETA")) codigoIdentificacao = "JA";
        else if (nome.contains("LENÇO")) codigoIdentificacao = "LE";
        else if (nome.contains("MACACÃO")) codigoIdentificacao = "MA";
        else if (nome.contains("MACAQUINHO")) codigoIdentificacao = "MA";
        else if (nome.contains("PARKA")) codigoIdentificacao = "PK";
        else if (nome.contains("PONCHO")) codigoIdentificacao = "TR";
        else if (nome.contains("PULSEIRA")) codigoIdentificacao = "PU";
        else if (nome.contains("REGATA")) codigoIdentificacao = "BL";
        else if (nome.contains("SAIA")) codigoIdentificacao = "SA";
        else if (nome.contains("SHORT")) codigoIdentificacao = "SH";
        else if (nome.contains("TOMARA QUE CAIA")) codigoIdentificacao = "BL";
        else if (nome.contains("TRICOT")) codigoIdentificacao = "TR";
        else if (nome.contains("T-SHIRT")) codigoIdentificacao = "BL";
        else if (nome.contains("VESTIDO")) codigoIdentificacao = "VE";

        int numeroAleatorio = 1000000 + new Random().nextInt(9000000);
        String codigoFinal = codigoIdentificacao + numeroAleatorio + tamanho;

        item.setCodigo(codigoFinal);
        item.setFornecedor(fornecedor);

        Tamanho tamanhoo = tamanhoRepository.findById(dto.getIdTamanho())
                .orElseThrow(() -> new RuntimeException("Tamanho não encontrado"));
        item.setTamanho(tamanhoo);

        Cor cor = corRepository.findById(dto.getIdCor())
                .orElseThrow(() -> new RuntimeException("Cor não encontrada"));
        item.setCor(cor);

        Material material = materialRepository.findById(dto.getIdMaterial())
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));
        item.setMaterial(material);
        
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        item.setCategoria(categoria);

        Fornecedor fornecedor1 = fornecedorRepository.findById(dto.getIdFornecedor())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));


        return itemRepository.save(item);
    }

    public List<Item> filtrarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Não há nenhum termo digitado!");
        }
        List<Item> itens = itemRepository.findByCategoriaNomeContainsIgnoreCase(categoria);

        if (itens.isEmpty()) {
            throw new ItemNaoEncontradoException("Itens não encontrados no estoque!");
        }

        return itens;
    }


    public List<Item> filtrarPorFornecedor (String nome) {
        List<Item> itens = itemRepository.findByFornecedorNomeContainsIgnoreCase(nome);

        if(nome == null) {
            throw new NullPointerException("Não há nenhum termo digitado!");
        }

        if(itens.isEmpty()) {
            throw new ItemNaoEncontradoException("Itens não encontrados no estoque!");
        }
        return itens;
    }

    public List<Item> filtrarPorNome (String nome) {
        List<Item> itens = itemRepository.findByNomeContainsIgnoreCase(nome);

        if(nome == null) {
            throw new NullPointerException("Não há nenhum termo digitado!");
        }

        if(itens.isEmpty()) {
            throw new ItemNaoEncontradoException("Itens não encontrados no estoque!");
        }

        return itens;
    }

    public void deletarPorCodigo(String codigo) {
        Optional<Item> itemOpt = itemRepository.findByCodigo(codigo);
        if (itemOpt.isEmpty()) {
            throw new ItemNaoEncontradoException("Item com código '" + codigo + "' não encontrado.");
        }
        itemRepository.delete(itemOpt.get());
    }

    public void validarCampoVazio(Item item) {
        Categoria categoria = item.getCategoria();
        Tamanho tamanho = item.getTamanho();
        Cor cor = item.getCor();
        Material material = item.getMaterial();
        Fornecedor fornecedorCadastrado = item.getFornecedor();

        if (categoria == null || categoria.getNome() == null || categoria.getNome().isBlank()
                || tamanho == null || tamanho.getNome() == null || tamanho.getNome().isBlank()
                || cor == null || cor.getNome() == null || cor.getNome().isBlank()
                || material == null || material.getNome() == null || material.getNome().isBlank()
                || fornecedorCadastrado == null
                || item.getQtdEstoque() <= 0) {
            throw new ItemCampoVazioException("Campos em vazio no cadastro de item");
        }
    }



}
