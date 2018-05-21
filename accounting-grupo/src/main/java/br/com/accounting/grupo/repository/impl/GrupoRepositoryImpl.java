package br.com.accounting.grupo.repository.impl;

import br.com.accounting.commons.entity.Grupo;
import br.com.accounting.commons.entity.SubGrupo;
import br.com.accounting.commons.repository.impl.GenericAbstractRepository;
import br.com.accounting.grupo.factory.GrupoFactory;
import br.com.accounting.grupo.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.commons.util.Utils.SEPARADOR;
import static br.com.accounting.commons.util.Utils.removeLast;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class GrupoRepositoryImpl extends GenericAbstractRepository<Grupo> implements GrupoRepository {
    @Autowired
    private String diretorio;

    @Override
    public Grupo filtrarPorNome(final List<Grupo> entities, final String nome) {
        List<Grupo> entitiesFiltradas = entities
                .stream()
                .filter(c -> (c.nome().equals(nome)))
                .collect(Collectors.toList());
        if (isEmpty(entitiesFiltradas)) {
            return null;
        }
        return entitiesFiltradas.get(0);
    }

    @Override
    public void ordenarPorNome(final List<Grupo> entities) {
        entities.sort(Comparator.comparing(Grupo::nome));
    }

    @Override
    public String getArquivo() {
        return diretorio + File.separator + "grupos.csv";
    }

    @Override
    public String getArquivoContagem() {
        return diretorio + File.separator + "grupos-contagem.txt";
    }

    @Override
    public String criarLinha(final Grupo entity) {
        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(entity.nome()).append(SEPARADOR)
                .append(entity.descricao()).append(SEPARADOR);

        for (SubGrupo subGrupo : entity.subGrupos()) {
            builder.append(subGrupo.nome()).append(SEPARADOR);
        }

        return removeLast(builder, SEPARADOR);
    }

    @Override
    public Grupo criarEntity(final String linha) {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        GrupoFactory factory = GrupoFactory
                .begin()
                .withCodigo(registro.get(0))
                .withNome(registro.get(1))
                .withDescricao(registro.get(2));
        if (registro.size() > 3) {
            for (int i = 3; i < registro.size(); i++) {
                factory.withSubGrupo(registro.get(i));
            }
        }
        return factory.build();
    }
}
