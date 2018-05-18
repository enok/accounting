package br.com.accounting.core.repository.impl;

import br.com.accounting.commons.repository.impl.GenericAbstractRepository;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.factory.SubGrupoFactory;
import br.com.accounting.core.repository.SubGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.commons.util.Utils.SEPARADOR;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class SubGrupoRepositoryImpl extends GenericAbstractRepository<SubGrupo> implements SubGrupoRepository {
    @Autowired
    private String diretorio;

    @Override
    public SubGrupo filtrarPorNome(final List<SubGrupo> entities, final String nome) {
        List<SubGrupo> entitiesFiltradas = entities
                .stream()
                .filter(c -> (c.nome().equals(nome)))
                .collect(Collectors.toList());
        if (isEmpty(entitiesFiltradas)) {
            return null;
        }
        return entitiesFiltradas.get(0);
    }

    @Override
    public void ordenarPorNome(final List<SubGrupo> entities) {
        entities.sort(Comparator.comparing(SubGrupo::nome));
    }

    @Override
    public String getArquivo() {
        return diretorio + File.separator + "subGrupos.csv";
    }

    @Override
    public String getArquivoContagem() {
        return diretorio + File.separator + "subGrupos-contagem.txt";
    }

    @Override
    public String criarLinha(final SubGrupo entity) {
        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(entity.nome()).append(SEPARADOR)
                .append(entity.descricao());
        return builder.toString();
    }

    @Override
    public SubGrupo criarEntity(final String linha) {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        return SubGrupoFactory
                .begin()
                .withCodigo(registro.get(0))
                .withNome(registro.get(1))
                .withDescricao(registro.get(2))
                .build();
    }
}
