package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.factory.SubGrupoFactory;
import br.com.accounting.core.repository.SubGrupoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.core.util.Utils.SEPARADOR;

@Repository
public class SubGrupoRepositoryImpl extends GenericRepository<SubGrupo> implements SubGrupoRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SubGrupoRepositoryImpl.class);

    @Autowired
    private String diretorio;

    @Override
    public String getArquivoContagem() {
        return diretorio + "\\subGrupos-contagem.txt";
    }

    @Override
    public String getArquivo() {
        return diretorio + "\\subGrupos.csv";
    }

    @Override
    public String criarLinha(SubGrupo subGrupo) {
        LOG.info("[ criarLinha ]");
        LOG.debug("subGrupo: " + subGrupo);

        StringBuilder builder = new StringBuilder()
                .append(subGrupo.getCodigo()).append(SEPARADOR)
                .append(subGrupo.getDescricao());

        return builder.toString();
    }

    @Override
    public List<SubGrupo> criarRegistros(List<String> linhas) {
        LOG.info("[ criarRegistros ]");
        LOG.debug("linhas: " + linhas);

        List<SubGrupo> subGrupoList = new ArrayList<>();

        for (String linha : linhas) {
            SubGrupo subGrupo = criarSubGrupo(linha);
            LOG.debug("subGrupo: " + subGrupo);
            subGrupoList.add(subGrupo);
        }

        return subGrupoList;
    }

    private SubGrupo criarSubGrupo(String linha) {
        LOG.debug("[ criarSubGrupo ]");
        LOG.debug("linha: " + linha);

        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());

        return SubGrupoFactory
                .begin()
                .withCodigo(registro.get(0))
                .withDescricao(registro.get(1))
                .build();
    }
}
