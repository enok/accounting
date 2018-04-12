package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.factory.ContabilidadeFactory;
import br.com.accounting.core.repository.ContabilidadeRepository;
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

@Repository
public class ContabilidadeRepositoryImpl extends GenericAbstractRepository<Contabilidade> implements ContabilidadeRepository {
    @Autowired
    private String diretorio;

    @Override
    public void ordenarPorDataVencimentoGrupoSubGrupo(List<Contabilidade> entities) {
        entities.sort(Comparator
                .comparing((Contabilidade c) -> c.dataVencimento())
                .thenComparing(c -> c.grupo().nome())
                .thenComparing(c -> c.grupo().subGrupos().get(0).nome()));
    }

    @Override
    public List<Contabilidade> filtrarPorCodigoPai(List<Contabilidade> entities, Long codigoPai) {
        return entities
                .stream()
                .filter(c -> (
                        ((c.codigoPai() == null) && (c.codigo() == codigoPai)) ||
                                (c.codigoPai() == codigoPai))
                )
                .collect(Collectors.toList());
    }

    @Override
    public String getArquivo() {
        return diretorio + File.separator + "contabilidades.csv";
    }

    @Override
    public String getArquivoContagem() {
        return diretorio + File.separator + "contabilidades-contagem.txt";
    }

    @Override
    public String criarLinha(final Contabilidade entity) {
        String dataPagamento = null;
        if (entity.dataPagamento() != null) {
            dataPagamento = getStringFromDate(entity.dataPagamento());
        }
        String usouCartao = null;
        String numeroCartao = null;
        if (entity.usouCartao()) {
            usouCartao = getStringFromBoolean(entity.usouCartao());
            numeroCartao = entity.cartao().numero();
        }
        String parcelado = null;
        String parcela = null;
        String parcelas = null;
        if (entity.parcelado()) {
            parcelado = getStringFromBoolean(entity.parcelado());
            parcela = entity.parcelamento().parcela().toString();
            parcelas = entity.parcelamento().parcelas().toString();
        }

        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(getStringFromDate(entity.dataLancamento())).append(SEPARADOR)
                .append(getStringFromDate(entity.dataAtualizacao())).append(SEPARADOR)
                .append(getStringFromDate(entity.dataVencimento())).append(SEPARADOR)
                .append(dataPagamento).append(SEPARADOR)
                .append(getStringFromBoolean(entity.recorrente())).append(SEPARADOR)
                .append(entity.grupo().nome()).append(SEPARADOR)
                .append(entity.grupo().subGrupos().get(0).nome()).append(SEPARADOR)
                .append(entity.descricao()).append(SEPARADOR)
                .append(usouCartao).append(SEPARADOR)
                .append(numeroCartao).append(SEPARADOR)
                .append(parcelado).append(SEPARADOR)
                .append(parcela).append(SEPARADOR)
                .append(parcelas).append(SEPARADOR)
                .append(entity.conta().nome()).append(SEPARADOR)
                .append(entity.tipo()).append(SEPARADOR)
                .append(getStringFromDouble(entity.valor())).append(SEPARADOR)
                .append(entity.codigoPai());
        return builder.toString();
    }

    @Override
    public Contabilidade criarEntity(final String linha) throws ParseException {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        return ContabilidadeFactory
                .begin()
                .withCodigo(registro.get(0))
                .withDataLancamento(registro.get(1))
                .withDataAtualizacao(registro.get(2))
                .withDataVencimento(registro.get(3))
                .withDataPagamento(registro.get(4))
                .withRecorrente(registro.get(5))
                .withGrupo(registro.get(6), registro.get(7))
                .withDescricao(registro.get(8))
                .withUsouCartao(registro.get(9))
                .withCartao(registro.get(10))
                .withParcelado(registro.get(11))
                .withParcelamento(registro.get(12), registro.get(13))
                .withConta(registro.get(14))
                .withTipo(registro.get(15))
                .withValor(registro.get(16))
                .withCodigoPai(registro.get(17))
                .build();
    }
}
