package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.factory.GrupoFactory;
import br.com.accounting.core.repository.GrupoRepository;
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
public class GrupoRepositoryImpl extends GenericRepository<Grupo> implements GrupoRepository {
    private static final Logger LOG = LoggerFactory.getLogger(GrupoRepositoryImpl.class);

    @Autowired
    private String diretorio;

    @Override
    public String getArquivoContagem() {
        return diretorio + "\\grupos-contagem.txt";
    }

    @Override
    public String getArquivo() {
        return diretorio + "\\grupos.csv";
    }

    @Override
    public String criarLinha(Grupo grupo) {
        LOG.info("[ criarLinha ]");
        LOG.debug("grupo: " + grupo);

        StringBuilder builder = new StringBuilder()
                .append(grupo.getCodigo()).append(SEPARADOR)
                .append(grupo.getDescricao()).append(SEPARADOR)
                .append(grupo.getSubGrupo().getCodigo()).append(SEPARADOR)
                .append(grupo.getSubGrupo().getDescricao());

        return builder.toString();
    }

    @Override
    public List<Grupo> criarRegistros(List<String> linhas) {
        LOG.info("[ criarRegistros ]");
        LOG.debug("linhas: " + linhas);

        List<Grupo> grupoList = new ArrayList<>();

        for (String linha : linhas) {
            Grupo grupo = criarGrupo(linha);
            LOG.debug("grupo: " + grupo);
            grupoList.add(grupo);
        }

        return grupoList;
    }

    private Grupo criarGrupo(String linha) {
        LOG.debug("[ criarGrupo ]");
        LOG.debug("linha: " + linha);

        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());

        return GrupoFactory
                .begin()
                .withCodigo(registro.get(0))
                .withDescricao(registro.get(1))
                .withSubGrupo(registro.get(2), registro.get(3))
                .build();
    }
}
