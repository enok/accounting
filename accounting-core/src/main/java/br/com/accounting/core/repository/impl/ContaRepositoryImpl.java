package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.factory.ContaFactory;
import br.com.accounting.core.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.core.util.Utils.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class ContaRepositoryImpl extends GenericAbstractRepository<Conta> implements ContaRepository {
    @Autowired
    private String diretorio;

    @Override
    public Conta filtrarPorNome(final List<Conta> entities, final String nome) {
        List<Conta> contasBuscadas = entities
                .stream()
                .filter(c -> (c.nome().equals(nome)))
                .collect(Collectors.toList());
        if (isEmpty(contasBuscadas)) {
            return null;
        }
        return contasBuscadas.get(0);
    }

    @Override
    public List<Conta> filtrarCumulativas(List<Conta> entities) {
        List<Conta> entitiesFiltradas = entities
                .stream()
                .filter(c -> (c.cumulativo()))
                .collect(Collectors.toList());
        if (isEmpty(entitiesFiltradas)) {
            return null;
        }
        return entitiesFiltradas;
    }

    @Override
    public void ordenarPorNome(List<Conta> entities) {
        entities.sort(Comparator.comparing(Conta::nome));
    }

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
                .append(getStringFromDouble(entity.valorDefault())).append(SEPARADOR)
                .append(getStringFromDouble(entity.saldo())).append(SEPARADOR)
                .append(getStringFromBoolean(entity.cumulativo())).append(SEPARADOR)
                .append(getStringFromDate(entity.dataAtualizacao()));
        return builder.toString();
    }

    @Override
    public Conta criarEntity(String linha) throws ParseException {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        int i = 0;
        return ContaFactory
                .begin()
                .withCodigo(registro.get(i++))
                .withNome(registro.get(i++))
                .withDescricao(registro.get(i++))
                .withValorDefault(registro.get(i++))
                .withSaldo(registro.get(i++))
                .withCumulativo(registro.get(i++))
                .withDataAtualizacao(registro.get(i++))
                .build();
    }
}
