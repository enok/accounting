package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Historico;
import br.com.accounting.core.factory.HistoricoFactory;
import br.com.accounting.core.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.core.util.Utils.SEPARADOR;
import static br.com.accounting.core.util.Utils.removeLast;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class HistoricoRepositoryImpl extends GenericAbstractRepository<Historico> implements HistoricoRepository {
    @Autowired
    private String diretorio;

    @Override
    public String getArquivo() {
        return diretorio + File.separator + "historico.csv";
    }

    @Override
    public String getArquivoContagem() {
        return diretorio + File.separator + "historico-contagem.txt";
    }

    @Override
    public String criarLinha(final Historico entity) {
        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(entity.metodo()).append(SEPARADOR);

        if (!isEmpty(entity.parametros())) {
            for (Map.Entry<String, Object> map : entity.parametros().entrySet()) {
                builder.append(map.getKey()).append(SEPARADOR)
                        .append(map.getValue()).append(SEPARADOR);
            }
        }

        return removeLast(builder, SEPARADOR);
    }

    @Override
    public Historico criarEntity(final String linha) {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());

        HistoricoFactory historicoFactory = HistoricoFactory
                .begin()
                .withCodigo(registro.get(0))
                .withMetodo(registro.get(1));

        if (registro.size() > 2) {
            for (int i = 2; i < registro.size(); i += 2) {
                historicoFactory.withParametro(registro.get(i), registro.get(i + 1));
            }
        }

        return historicoFactory.build();
    }
}
