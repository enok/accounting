package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.factory.ContaFactory;
import br.com.accounting.core.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.core.util.Utils.SEPARADOR;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class ContaRepositoryImpl extends GenericAbstractRepository<Conta> implements ContaRepository {
    @Autowired
    private String diretorio;

    @Override
    public String getArquivo() {
        return diretorio + File.separator + "contas.csv";
    }

    @Override
    public String getArquivoContagem() {
        return diretorio + File.separator + "contas-contagem.txt";
    }

    @Override
    public String criarLinha(final Conta entity) {
        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(entity.nome()).append(SEPARADOR)
                .append(entity.descricao()).append(SEPARADOR)
                .append(entity.saldo());

        return builder.toString();
    }

    @Override
    public List<Conta> criarRegistros(final List<String> linhas) throws ParseException {
        List<Conta> contas = new ArrayList<>();

        for (String linha : linhas) {
            Conta conta = criarConta(linha);
            contas.add(conta);
        }

        return contas;
    }

    private Conta criarConta(final String linha) throws ParseException {
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
    public Conta filtrarCodigo(final List<Conta> contas, final Long codigo) {
        List<Conta> contasBuscadas = contas
                .stream()
                .filter(c -> (c.codigo().equals(codigo)))
                .collect(Collectors.toList());
        if (isEmpty(contasBuscadas)) {
            return null;
        }
        return contasBuscadas.get(0);
    }

    @Override
    public Conta filtrarPorNomeDescricao(final List<Conta> contas, final String nome, final String descricao) {
        List<Conta> contasBuscadas = contas
                .stream()
                .filter(c -> (c.nome().equals(nome) && c.descricao().equals(descricao)))
                .collect(Collectors.toList());
        if (isEmpty(contasBuscadas)) {
            return null;
        }
        return contasBuscadas.get(0);
    }
}
