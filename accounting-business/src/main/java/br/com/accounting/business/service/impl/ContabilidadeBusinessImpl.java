package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.exception.UpdateException;
import br.com.accounting.business.factory.ContabilidadeDTOFactory;
import br.com.accounting.business.service.ContabilidadeBusiness;
import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.factory.ContabilidadeFactory;
import br.com.accounting.core.service.ContabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin.dom.exception.InvalidStateException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static br.com.accounting.core.util.Utils.getBooleanFromString;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ContabilidadeBusinessImpl extends GenericAbstractBusiness<ContabilidadeDTO, Contabilidade> implements ContabilidadeBusiness {
    private ContabilidadeService service;

    @Autowired
    public ContabilidadeBusinessImpl(final ContabilidadeService service) {
        super(service, ContabilidadeDTOFactory.create());
        this.service = service;
    }

    @History
    public List<Long> criar(final ContabilidadeDTO dto) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros);

            List<Contabilidade> entities = criarEntities(dto);
            List<Long> codigos = new ArrayList<>();
            Long codigoPai = null;

            Contabilidade entity = entities.get(0);
            if (entities.get(0).parcelado()) {
                for (int i = 0; i < entities.get(0).parcelamento().parcelas(); i++) {
                    entity.codigoPai(codigoPai);
                    entity.parcelamento().parcela(i + 1);

                    Long codigo = salvarEntity(codigos, entity);

                    if (codigoPai == null) {
                        codigoPai = codigo;
                    }
                }
            }
            else {
                salvarEntity(codigos, entity);
            }
            return codigos;
        }
        catch (Exception e) {
            String message = "Não foi possível criar.";
            throw new BusinessException(message, e);
        }
    }

    private Long salvarEntity(List<Long> codigos, Contabilidade entity) throws br.com.accounting.core.exception.ServiceException {
        Long codigo = service.salvar(entity);
        codigos.add(codigo);
        return codigo;
    }

    @Override
    public List<ContabilidadeDTO> buscarTodasAsParcelas(final Long codigoPai) throws BusinessException {
        List<ContabilidadeDTO> entitiesDTO;
        try {
            List<Contabilidade> entities = service.buscarTodas();
            List<Contabilidade> entitiesFiltradas = service.buscarTodasAsParcelas(entities, codigoPai);
            entitiesDTO = criarListaEntitiesDTO(dtoFactory, entitiesFiltradas);
        }
        catch (Exception e) {
            String message = "Não foi possível buscas todas as parcelas.";
            throw new BusinessException(message, e);
        }
        return entitiesDTO;
    }

    @Override
    public void validarEntrada(final ContabilidadeDTO dto, final List<String> erros) throws MissingFieldException {
        if (isBlank(dto.dataVencimento())) {
            erros.add(format(msg, "dataVencimento"));
        }
        if (isBlank(dto.recorrente())) {
            erros.add(format(msg, "recorrente"));
        }
        if (isBlank(dto.grupo())) {
            erros.add(format(msg, "grupo"));
        }
        if (isBlank(dto.subGrupo())) {
            erros.add(format(msg, "subGrupo"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        if (isBlank(dto.usouCartao())) {
            erros.add(format(msg, "usouCartão"));
        }
        if (isBlank(dto.parcelado())) {
            erros.add(format(msg, "parcelado"));
        }
        if (usouCartao(dto)) {
            if (temCartao(dto)) {
                if (ehParcelado(dto)) {
                    if (naoTemParcelas(dto)) {
                        erros.add(format(msg, "parcelas"));
                    }
                }
            }
            else {
                erros.add(format(msg, "cartão"));
            }
        }
        if (isBlank(dto.conta())) {
            erros.add(format(msg, "conta"));
        }
        if (isBlank(dto.tipo())) {
            erros.add(format(msg, "tipo"));
        }
        if (isBlank(dto.valor())) {
            erros.add(format(msg, "valor"));
        }
        conferirErros(erros);
    }

    @Override
    public void validarEntradaUpdate(final ContabilidadeDTO dto, final Contabilidade entity, final List<String> erros) throws MissingFieldException, UpdateException {
        conferirCodigo(dto, erros);
        if (isBlank(dto.dataLancamento())) {
            erros.add(format(msg, "dataLançamento"));
        }
        if (isBlank(dto.dataAtualizacao())) {
            erros.add(format(msg, "dataAtualização"));
        }

        if (usouCartao(dto) && temCartao(dto) && ehParcelado(dto) && naoTemParcela(dto)) {
            erros.add(format(msg, "parcela"));
        }

        if (ehParcelado(dto) && (entity != null) && naoEhPai(entity)) {
            if (isBlank(dto.codigoPai())) {
                erros.add(format(msg, "códigoPai"));
            }
        }
        conferirErros(erros);

        List<String> errosUpdate = new ArrayList<>();

        conferirCodigoAlterado(dto, entity, errosUpdate);
        conferirParcelaAlterada(dto, entity, errosUpdate);
        conferirCodigoPaiAlterado(dto, entity, errosUpdate);

        conferirErrosUpdate(errosUpdate);
    }

    private void conferirParcelaAlterada(ContabilidadeDTO dto, Contabilidade entity, List<String> errosUpdate) {
        if ((entity != null) && entity.parcelado() && parcelasDiferentes(dto, entity)) {
            errosUpdate.add("O campo parcela não pode ser alterado.");
        }
    }

    private void conferirCodigoPaiAlterado(ContabilidadeDTO dto, Contabilidade entity, List<String> errosUpdate) {
        if ((entity != null) && codigosPaiDiferentes(dto, entity)) {
            errosUpdate.add("O campo códigoPai não pode ser alterado.");
        }
    }

    private boolean parcelasDiferentes(ContabilidadeDTO dto, Contabilidade entity) {
        return !dto.parcela().equals(entity.parcelamento().parcela().toString());
    }

    private boolean codigosPaiDiferentes(ContabilidadeDTO dto, Contabilidade entity) {
        if ((dto.codigoPai() == null) && (entity.codigoPai() == null)) {
            return false;
        }
        if ((dto.codigoPai() == null) || (entity.codigoPai() == null)) {
            return true;
        }
        return !dto.codigoPai().equals(entity.codigoPai().toString());
    }

    private boolean naoEhPai(Contabilidade entity) {
        return temParcela(entity) && naoEhPrimeiraParcela(entity);
    }

    private boolean temParcela(Contabilidade entity) {
        return entity.parcelamento().parcela() != null;
    }

    private boolean naoEhPrimeiraParcela(Contabilidade entity) {
        return !entity.parcelamento().parcela().equals(1);
    }

    private boolean usouCartao(ContabilidadeDTO dto) {
        return !isBlank(dto.usouCartao()) && getBooleanFromString(dto.usouCartao());
    }

    private boolean temCartao(ContabilidadeDTO dto) {
        return !isBlank(dto.cartao());
    }

    private boolean ehParcelado(ContabilidadeDTO dto) {
        return !isBlank(dto.parcelado()) && getBooleanFromString(dto.parcelado());
    }

    private boolean naoTemParcela(ContabilidadeDTO dto) {
        return isBlank(dto.parcela());
    }

    private boolean naoTemParcelas(ContabilidadeDTO dto) {
        return isBlank(dto.parcelas());
    }

    @Override
    public void validaRegistroDuplicado(final Contabilidade contabilidade) {
        throw new InvalidStateException("Uma contabilidade não valida duplicidade de registro.");
    }

    @Override
    public List<Contabilidade> criarEntities(final ContabilidadeDTO dto) throws ParseException {
        return asList(ContabilidadeFactory
                .begin()
                .withCodigo(dto.codigo())
                .withDataVencimento(dto.dataVencimento())
                .withDataPagamento(dto.dataPagamento())
                .withRecorrente(dto.recorrente())
                .withGrupo(dto.grupo(), dto.subGrupo())
                .withDescricao(dto.descricao())
                .withUsouCartao(dto.usouCartao())
                .withCartao(dto.cartao())
                .withParcelado(dto.parcelado())
                .withParcelamento(dto.parcela(), dto.parcelas())
                .withConta(dto.conta())
                .withTipo(dto.tipo())
                .withValor(dto.valor())
                .withCodigoPai(dto.codigoPai())
                .build());
    }
}