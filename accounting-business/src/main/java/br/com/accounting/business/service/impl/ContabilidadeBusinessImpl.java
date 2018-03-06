package br.com.accounting.business.service.impl;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.service.ContabilidadeBusiness;
import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.ContabilidadeFactory;
import br.com.accounting.core.service.ContabilidadeService;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.accounting.business.validation.ValidacaoBusiness.validaBusiness;
import static br.com.accounting.business.validation.ValidacaoCampo.*;
import static br.com.accounting.core.util.Utils.getNextMonth;
import static br.com.accounting.core.util.Utils.getStringFromCurrentDate;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ContabilidadeBusinessImpl implements ContabilidadeBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(ContabilidadeBusinessImpl.class);

    @Autowired
    private ContabilidadeService contabilidadeService;

    @Override
    public List<Long> salvar(ContabilidadeDTO contabilidadeDTO) throws BusinessException, TechnicalException {
        LOG.info("[ salvar ]");
        LOG.info("contabilidadeDTO: " + contabilidadeDTO);

        List<Long> codigos = new ArrayList<>();
        LocalDate dataLancamento = LocalDate.now();
        String vencimento = contabilidadeDTO.getVencimento();
        String parcelasString = contabilidadeDTO.getParcelas();

        final String mensagem = "Nao foi possivel salvar a contabilidade";
        try {
            validaCampos(contabilidadeDTO);
            validaBusiness(contabilidadeDTO);

            ContabilidadeFactory contabilidadeFactory = buscarContabilidadeFactory(contabilidadeDTO, dataLancamento, vencimento);

            if (naoTemParcelamento(parcelasString)) {
                salvarContabilidade(codigos, contabilidadeFactory);
            }
            else {
                salvarContabilidades(codigos, vencimento, parcelasString, contabilidadeFactory);
            }
        }
        catch (MissingFieldException | WrongEnumValueException | InvalidBusinessException e) {
            LOG.error(mensagem, e);
            throw new BusinessException(mensagem, e);
        }
        catch (Exception e) {
            LOG.error(mensagem, e);
            throw new TechnicalException(mensagem, e);
        }

        LOG.debug("codigos: " + codigos);

        return codigos;
    }

    @Override
    public void atualizar(ContabilidadeDTO contabilidadeDTO) throws TechnicalException {
        LOG.info("[ atualizar ]");
        LOG.info("contabilidadeDTO: " + contabilidadeDTO);

        try {
            long codigo = Long.parseLong(contabilidadeDTO.getCodigo());
            String vencimento = contabilidadeDTO.getVencimento();
            String dataAtualizacao = getStringFromCurrentDate();

            List<Contabilidade> contabilidades = contabilidadeService.buscarRegistros();
            List<Contabilidade> contabilidadesAgrupadas = contabilidadeService.filtrarPorParcelamentoPai(codigo, contabilidades);
            List<Contabilidade> contabilidadesAcimaDoVencimento = contabilidadeService.filtrarPorValoresAcimaDoVencimento(contabilidadeDTO.getVencimento(), contabilidadesAgrupadas);

            List<Contabilidade> novaListaDeContabilidades = new ArrayList<>();

            for (Contabilidade contabilidade : contabilidadesAcimaDoVencimento) {
                Contabilidade contabilidadeNova = ContabilidadeFactory
                        .begin(SerializationUtils.clone(contabilidade))
                        .withDataAtualizacao(dataAtualizacao)
                        .withVencimento(vencimento)
                        .withTipoPagamento(contabilidadeDTO.getTipoPagamento())
                        .withSubTipoPagamento(contabilidadeDTO.getSubTipoPagamento())
                        .withTipo(contabilidadeDTO.getTipo())
                        .withGrupoSubGrupo(contabilidadeDTO.getGrupo(), contabilidadeDTO.getSubGrupo())
                        .withDescricao(contabilidadeDTO.getDescricao())
                        .withCategoria(contabilidadeDTO.getCategoria())
                        .withValor(contabilidadeDTO.getValor())
                        .withStatus(contabilidadeDTO.getStatus())
                        .build();

                vencimento = getNextMonth(vencimento);

                LOG.debug("contabilidadeNova: " + contabilidadeNova);
                novaListaDeContabilidades.add(contabilidadeNova);
            }

            contabilidadeService.atualizar(contabilidadesAcimaDoVencimento, novaListaDeContabilidades);
        }
        catch (Exception e) {
            String mensagem = "Nao foi possivel atualizar as contabilidades";
            LOG.error(mensagem, e);
            throw new TechnicalException(mensagem, e);
        }
    }

    @Override
    public List<ContabilidadeDTO> buscarTudo() throws TechnicalException {
        LOG.info("[ buscarTudo ]");

        List<ContabilidadeDTO> contabilidadeDTOS = new ArrayList<>();

        try {
            List<Contabilidade> contabilidades = contabilidadeService.buscarRegistros();

            for (Contabilidade contabilidade : contabilidades) {
                ContabilidadeDTO contabilidadeDTO = new ContabilidadeDTO()
                        .withCodigo(contabilidade.getCodigo().toString())
                        .withDataLancamento(contabilidade.getDataLancamentoFormatada())
                        .withVencimento(contabilidade.getVencimentoFormatado())
                        .withTipoPagamento(contabilidade.getTipoPagamentoValue())
                        .withSubTipoPagamento(contabilidade.getSubTipoPagamentoDescricao())
                        .withTipo(contabilidade.getTipoValue())
                        .withGrupo(contabilidade.getGrupoDescricao())
                        .withSubGrupo(contabilidade.getSubGrupoDescricao())
                        .withDescricao(contabilidade.getDescricao())
                        .withParcela(contabilidade.getParcela())
                        .withParcelas(contabilidade.getParcelas())
                        .withParcelaCodigoPai(contabilidade.getParcelaCodigoPai())
                        .withCategoria(contabilidade.getCategoriaValue())
                        .withValor(contabilidade.getValorFormatado())
                        .withStatus(contabilidade.getStatusValue());

                contabilidadeDTOS.add(contabilidadeDTO);
            }
        }
        catch (Exception e) {
            String mensagem = "Nao foi possivel buscar as contabilidades";
            LOG.error(mensagem, e);
            throw new TechnicalException(mensagem, e);
        }

        LOG.debug("contabilidadeDTOS: " + contabilidadeDTOS);

        return contabilidadeDTOS;
    }

    @Override
    public List<ContabilidadeDTO> buscarRelacionadasPorCodigoPai(Long codigo) throws TechnicalException {
        LOG.info("[ buscarTudo ]");

        List<ContabilidadeDTO> contabilidadeDTOS = new ArrayList<>();

        try {
            List<Contabilidade> contabilidades = contabilidadeService.buscarRegistros();
            contabilidades = contabilidadeService.filtrarPorParcelamentoPai(codigo, contabilidades);

            for (Contabilidade contabilidade : contabilidades) {
                ContabilidadeDTO contabilidadeDTO = new ContabilidadeDTO()
                        .withCodigo(contabilidade.getCodigo().toString())
                        .withDataLancamento(contabilidade.getDataLancamentoFormatada())
                        .withVencimento(contabilidade.getVencimentoFormatado())
                        .withTipoPagamento(contabilidade.getTipoPagamentoValue())
                        .withSubTipoPagamento(contabilidade.getSubTipoPagamentoDescricao())
                        .withTipo(contabilidade.getTipoValue())
                        .withGrupo(contabilidade.getGrupoDescricao())
                        .withSubGrupo(contabilidade.getSubGrupoDescricao())
                        .withDescricao(contabilidade.getDescricao())
                        .withParcela(contabilidade.getParcela())
                        .withParcelas(contabilidade.getParcelas())
                        .withParcelaCodigoPai(contabilidade.getParcelaCodigoPai())
                        .withCategoria(contabilidade.getCategoriaValue())
                        .withValor(contabilidade.getValorFormatado())
                        .withStatus(contabilidade.getStatusValue());

                contabilidadeDTOS.add(contabilidadeDTO);
            }
        }
        catch (Exception e) {
            String mensagem = "Nao foi possivel buscar as contabilidades relacionadas por codigo pai: " + codigo;
            LOG.error(mensagem, e);
            throw new TechnicalException(mensagem, e);
        }

        LOG.debug("contabilidadeDTOS: " + contabilidadeDTOS);

        return contabilidadeDTOS;
    }

    private void validaCampos(ContabilidadeDTO contabilidadeDTO) throws MissingFieldException, WrongEnumValueException {
        validaCampoObrigatorio(contabilidadeDTO.getVencimento(), "vencimento");
        validaTipoPagamento(contabilidadeDTO.getTipoPagamento());
        validaTipo(contabilidadeDTO.getTipo());
        validaCampoObrigatorio(contabilidadeDTO.getGrupo(), "grupo");
        validaCampoObrigatorio(contabilidadeDTO.getSubGrupo(), "subGrupo");
        validaCampoObrigatorio(contabilidadeDTO.getDescricao(), "descricao");
        validaCategoria(contabilidadeDTO.getCategoria());
        validaCampoObrigatorio(contabilidadeDTO.getValor(), "valor");
        validaStatus(contabilidadeDTO.getStatus());
    }

    private ContabilidadeFactory buscarContabilidadeFactory(ContabilidadeDTO contabilidadeDTO, LocalDate dataLancamento, String vencimento) throws ParseException {
        return ContabilidadeFactory
                .begin()
                .withDataLancamento(dataLancamento)
                .withDataAtualizacao(dataLancamento)
                .withVencimento(vencimento)
                .withTipoPagamento(contabilidadeDTO.getTipoPagamento())
                .withSubTipoPagamento(contabilidadeDTO.getSubTipoPagamento())
                .withTipo(contabilidadeDTO.getTipo())
                .withGrupoSubGrupo(contabilidadeDTO.getGrupo(), contabilidadeDTO.getSubGrupo())
                .withDescricao(contabilidadeDTO.getDescricao())
                .withCategoria(contabilidadeDTO.getCategoria())
                .withValor(contabilidadeDTO.getValor())
                .withStatus(contabilidadeDTO.getStatus());
    }

    private boolean naoTemParcelamento(String parcelasString) {
        return isBlank(parcelasString);
    }

    private void salvarContabilidade(List<Long> codigos, ContabilidadeFactory contabilidadeFactory) throws ServiceException {
        Contabilidade contabilidade = contabilidadeFactory
                .build();
        codigos.add(contabilidadeService.salvar(contabilidade));
    }

    private void salvarContabilidades(List<Long> codigos, String vencimento, String parcelasString, ContabilidadeFactory contabilidadeFactory) throws ServiceException {
        Integer parcelas = Integer.parseInt(parcelasString);
        Long codigoPai = null;
        String vencimentoLocal = vencimento;

        for (int parcela = 1; parcela <= parcelas; parcela++) {
            Long codigo = salvarContabilidade(codigos, contabilidadeFactory, parcelas, codigoPai, vencimentoLocal, parcela);

            codigoPai = buscarCodigoPai(parcela, codigoPai, codigo);
            vencimentoLocal = getNextMonth(vencimentoLocal);
        }
    }

    private Long salvarContabilidade(List<Long> codigos, ContabilidadeFactory contabilidadeFactory, Integer parcelas,
                                     Long codigoPai, String vencimento, int parcela) throws ServiceException {
        Contabilidade contabilidade = contabilidadeFactory
                .withVencimento(vencimento)
                .withParcelamento(parcela, parcelas, codigoPai)
                .build();

        Long codigo = contabilidadeService.salvar(contabilidade);
        codigos.add(codigo);

        return codigo;
    }

    private Long buscarCodigoPai(int parcela, Long codigoPai, Long codigo) {
        if (parcela == 1) {
            codigoPai = codigo;
        }
        return codigoPai;
    }
}
