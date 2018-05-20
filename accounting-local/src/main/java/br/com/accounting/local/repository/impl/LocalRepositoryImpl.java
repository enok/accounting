package br.com.accounting.local.repository.impl;

import br.com.accounting.commons.entity.Local;
import br.com.accounting.commons.repository.impl.GenericAbstractRepository;
import br.com.accounting.local.factory.LocalFactory;
import br.com.accounting.local.repository.LocalRepository;
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
public class LocalRepositoryImpl extends GenericAbstractRepository<Local> implements LocalRepository {
    @Autowired
    private String diretorio;

    @Override
    public Local filtrarPorNome(final List<Local> entities, final String nome) {
        List<Local> entitiesBuscadas = entities
                .stream()
                .filter(c -> (c.nome().equals(nome)))
                .collect(Collectors.toList());
        if (isEmpty(entitiesBuscadas)) {
            return null;
        }
        return entitiesBuscadas.get(0);
    }

    @Override
    public void ordenarPorNome(List<Local> entities) {
        entities.sort(Comparator.comparing(Local::nome));
    }

    @Override
    public String getArquivo() {
        return diretorio + File.separator + "locais.csv";
    }

    @Override
    public String getArquivoContagem() {
        return diretorio + File.separator + "locais-contagem.txt";
    }

    @Override
    public String criarLinha(final Local entity) {
        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(entity.nome());
        return builder.toString();
    }

    @Override
    public Local criarEntity(String linha) {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        return LocalFactory
                .begin()
                .withCodigo(registro.get(0))
                .withNome(registro.get(1))
                .build();
    }
}
