package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.factory.ContaFactory;
import br.com.accounting.core.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.core.util.Utils.SEPARADOR;

@Slf4j
@Repository
public class ContaRepositoryImpl extends GenericAbstractRepository<Conta> implements ContaRepository {
    @Autowired
    private String diretorio;

    @Override
    public String getArquivo() {
        log.debug("[ getArquivo ]");
        return diretorio + File.separator + "contas.csv";
    }

    @Override
    public String getArquivoContagem() {
        log.debug("[ getArquivoContagem ]");
        return diretorio + File.separator + "contas-contagem.txt";
    }

    @Override
    public String criarLinha(Conta entity) {
        log.debug("[ criarLinha ]");
        log.debug("conta: {}" + entity);

        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(entity.nome()).append(SEPARADOR)
                .append(entity.descricao()).append(SEPARADOR)
                .append(entity.saldo());

        return builder.toString();
    }

    @Override
    public List<Conta> criarRegistros(List<String> linhas) throws ParseException {
        log.debug("[ criarRegistros ]");
        log.debug("linhas: " + linhas);

        List<Conta> contas = new ArrayList<>();

        for (String linha : linhas) {
            Conta conta = criarConta(linha);
            log.debug("conta: " + conta);
            contas.add(conta);
        }

        return contas;
    }

    private Conta criarConta(String linha) throws ParseException {
        log.debug("[ criarConta ]");
        log.debug("linha: {}", linha);

        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());

        return ContaFactory
                .begin()
                .withCodigo(registro.get(0))
                .withNome(registro.get(1))
                .withDescricao(registro.get(2))
                .withSaldo(registro.get(3))
                .build();
    }

    @Override
    public Conta filtrarPorNomeDescricao(List<Conta> contas, String nome, String descricao) {
        List<Conta> contasBuscadas = contas
                .stream()
                .filter(c -> (c.nome().equals(nome) && c.descricao().equals(descricao)))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(contasBuscadas)) {
            return null;
        }
        return contasBuscadas.get(0);
    }
}
