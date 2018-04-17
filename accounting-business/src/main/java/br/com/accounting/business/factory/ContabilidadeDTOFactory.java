package br.com.accounting.business.factory;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.core.entity.*;

import java.time.LocalDate;

import static br.com.accounting.core.util.Utils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ContabilidadeDTOFactory extends GenericDTOFactory<ContabilidadeDTO, Contabilidade> {
    private static ContabilidadeDTOFactory factory;

    private ContabilidadeDTO dto;

    private ContabilidadeDTOFactory() {
        dto = new ContabilidadeDTO();
    }

    public static ContabilidadeDTOFactory create() {
        return new ContabilidadeDTOFactory();
    }

    @Override
    public GenericDTOFactory begin() {
        factory = new ContabilidadeDTOFactory();
        return factory;
    }

    @Override
    public GenericDTOFactory preencherCampos(Contabilidade entity) {
        if (entity == null) {
            dto = null;
            return this;
        }
        withCodigo(entity.codigo());
        withDataLancamento(entity.dataLancamento());
        withDataAtualizacao(entity.dataAtualizacao());
        withDataVencimento(entity.dataVencimento());
        withDataPagamento(entity.dataPagamento());
        withRecorrente(entity.recorrente());
        withGrupo(entity.grupo());
        withDescricao(entity.descricao());
        withUsouCartao(entity.usouCartao());
        withCartao(entity.cartao());
        withParcelado(entity.parcelado());
        withParcelamento(entity.parcelamento());
        withConta(entity.conta());
        withTipo(entity.tipo());
        withValor(entity.valor());
        withCodigoPai(entity.codigoPai());
        withProximoLancamento(entity.proximoLancamento());
        return this;
    }

    @Override
    public ContabilidadeDTO build() {
        if (dto == null) {
            return dto;
        }
        if (isBlank(dto.dataLancamento())) {
            dto.dataLancamento(getStringFromCurrentDate());
        }
        if (isBlank(dto.dataAtualizacao())) {
            dto.dataAtualizacao(getStringFromCurrentDate());
        }
        if (isBlank(dto.recorrente())) {
            dto.recorrente(getStringFromBoolean(false));
        }
        if (isBlank(dto.usouCartao())) {
            dto.usouCartao(getStringFromBoolean(false));
        }
        if (isBlank(dto.parcelado())) {
            dto.parcelado(getStringFromBoolean(false));
        }
        return dto;
    }

    public ContabilidadeDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            dto.codigo(codigo.toString());
        }
        return this;
    }

    public ContabilidadeDTOFactory withDataLancamento(LocalDate dataLancamento) {
        if (dataLancamento != null) {
            dto.dataLancamento(getStringFromDate(dataLancamento));
        }
        return this;
    }

    public ContabilidadeDTOFactory withDataAtualizacao(LocalDate dataAtualizacao) {
        if (dataAtualizacao != null) {
            dto.dataAtualizacao(getStringFromDate(dataAtualizacao));
        }
        return this;
    }

    public ContabilidadeDTOFactory withDataVencimento(String dataVencimento) {
        if (!isBlankOrNull(dataVencimento)) {
            dto.dataVencimento(dataVencimento);
        }
        return this;
    }

    public ContabilidadeDTOFactory withDataVencimento(LocalDate dataVencimento) {
        if (dataVencimento != null) {
            withDataVencimento(getStringFromDate(dataVencimento));
        }
        return this;
    }

    public ContabilidadeDTOFactory withDataPagamento(String dataPagamento) {
        if (!isBlankOrNull(dataPagamento)) {
            dto.dataPagamento(dataPagamento);
        }
        return this;
    }

    public ContabilidadeDTOFactory withDataPagamento(LocalDate dataPagamento) {
        if (dataPagamento != null) {
            withDataPagamento(getStringFromDate(dataPagamento));
        }
        return this;
    }

    public ContabilidadeDTOFactory withRecorrente(String recorrente) {
        if (!isBlankOrNull(recorrente)) {
            dto.recorrente(recorrente);
        }
        return this;
    }

    public ContabilidadeDTOFactory withRecorrente(Boolean recorrente) {
        if (recorrente != null) {
            withRecorrente(getStringFromBoolean(recorrente));
        }
        return this;
    }

    public ContabilidadeDTOFactory withGrupo(String grupo, String subGrupo) {
        if (!isBlankOrNull(grupo)) {
            dto.grupo(grupo);
        }
        if (!isBlankOrNull(subGrupo)) {
            dto.subGrupo(subGrupo);
        }
        return this;
    }

    public ContabilidadeDTOFactory withGrupo(Grupo grupo) {
        if (grupo != null) {
            withGrupo(grupo.nome(), grupo.subGrupos().get(0).nome());
        }
        return this;
    }

    public ContabilidadeDTOFactory withDescricao(String descricao) {
        if (!isBlankOrNull(descricao)) {
            dto.descricao(descricao);
        }
        return this;
    }

    public ContabilidadeDTOFactory withUsouCartao(String usouCartao) {
        if (!isBlankOrNull(usouCartao)) {
            dto.usouCartao(usouCartao);
        }
        return this;
    }

    public ContabilidadeDTOFactory withUsouCartao(Boolean usouCartao) {
        if (usouCartao != null) {
            withUsouCartao(getStringFromBoolean(usouCartao));
        }
        return this;
    }

    public ContabilidadeDTOFactory withCartao(String cartao) {
        if (!isBlankOrNull(cartao)) {
            dto.cartao(cartao);
        }
        return this;
    }

    public ContabilidadeDTOFactory withCartao(Cartao cartao) {
        if (cartao != null) {
            withCartao(cartao.numero());
        }
        return this;
    }

    public ContabilidadeDTOFactory withParcelado(String parcelado) {
        if (!isBlankOrNull(parcelado)) {
            dto.parcelado(parcelado);
        }
        return this;
    }

    public ContabilidadeDTOFactory withParcelado(Boolean parcelado) {
        if (parcelado != null) {
            withParcelado(getStringFromBoolean(parcelado));
        }
        return this;
    }

    public ContabilidadeDTOFactory withParcela(String parcela) {
        if (!isBlankOrNull(parcela)) {
            dto.parcela(parcela);
        }
        return this;
    }

    public ContabilidadeDTOFactory withParcelas(String parcelas) {
        if (!isBlankOrNull(parcelas)) {
            dto.parcelas(parcelas);
        }
        return this;
    }

    public ContabilidadeDTOFactory withParcelamento(Parcelamento parcelamento) {
        if (parcelamento != null) {
            withParcela(parcelamento.parcela().toString());
            withParcelas(parcelamento.parcelas().toString());
        }
        return this;
    }

    public ContabilidadeDTOFactory withConta(String conta) {
        if (!isBlankOrNull(conta)) {
            dto.conta(conta);
        }
        return this;
    }

    public ContabilidadeDTOFactory withConta(Conta conta) {
        if (conta != null) {
            withConta(conta.nome());
        }
        return this;
    }

    public ContabilidadeDTOFactory withTipo(String tipo) {
        if (!isBlankOrNull(tipo)) {
            dto.tipo(tipo);
        }
        return this;
    }

    public ContabilidadeDTOFactory withTipo(TipoContabilidade tipo) {
        if (tipo != null) {
            withTipo(tipo.toString());
        }
        return this;
    }

    public ContabilidadeDTOFactory withValor(String valor) {
        if (!isBlankOrNull(valor)) {
            dto.valor(valor);
        }
        return this;
    }

    public ContabilidadeDTOFactory withValor(Double valor) {
        if (valor != null) {
            withValor(getStringFromDouble(valor));
        }
        return this;
    }

    public ContabilidadeDTOFactory withCodigoPai(Long codigoPai) {
        if (codigoPai != null) {
            dto.codigoPai(codigoPai.toString());
        }
        return this;
    }

    public ContabilidadeDTOFactory withProximoLancamento(String proximoLancamento) {
        if (!isBlankOrNull(proximoLancamento)) {
            dto.proximoLancamento(proximoLancamento);
        }
        return this;
    }

    private ContabilidadeDTOFactory withProximoLancamento(Long proximoLancamento) {
        if (proximoLancamento != null) {
            withProximoLancamento(proximoLancamento.toString());
        }
        return this;
    }
}
