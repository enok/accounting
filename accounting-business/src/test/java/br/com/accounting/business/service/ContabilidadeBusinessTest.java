package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.CreateException;
import br.com.accounting.business.service.impl.ContabilidadeBusinessImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static br.com.accounting.business.factory.ContabilidadeDTOMockFactory.*;
import static br.com.accounting.core.util.Utils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class ContabilidadeBusinessTest extends GenericTest {
    @Autowired
    private ContabilidadeBusiness business;

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemDiretorio() throws BusinessException, IOException {
        try {
            deletarDiretorioEArquivos();
            ContabilidadeDTO dto = contabilidadeDTO();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreation(e);
        }
    }

    @Test(expected = InvalidStateException.class)
    public void criarUmaContabilidadeNaoValidaRegistroDuplicado() {
        try {
            ((ContabilidadeBusinessImpl) business).validaRegistroDuplicado(null);
        }
        catch (InvalidStateException e) {
            assertThat(e.getMessage(), equalTo("Uma contabilidade não valida duplicidade de registro."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemDataVencimento() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemDataVencimento();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "dataVencimento");
        }
    }

    @Test
    public void criarUmaContabilidadeSemDataPagamento() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTOSemDataPagamento();
        Long codigo = business.criar(dto).get(0);
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.dataPagamento(), nullValue());
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemRecorrente() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemRecorrente();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "recorrente");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemGrupo() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemGrupo();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "grupo");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemSubGrupo() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemSubGrupo();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "subGrupo");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemDescricao() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemDescricao();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "descrição");
        }
    }

    @Test
    public void criarUmaContabilidadeSemUsouCartao() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTOSemUsouCartao();
        Long codigo = business.criar(dto).get(0);
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.usouCartao(), equalTo("N"));
    }

    @Test
    public void criarUmaContabilidadeNaoUsouCartao() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTONaoUsouCartao();
        Long codigo = business.criar(dto).get(0);
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
        assertNaoParcelado(dtoBuscado);
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemCartao() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemCartao();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "cartão");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemParcelado() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemParcelado();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "parcelado");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemParcelas() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemParcelas();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "parcelas");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemConta() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemConta();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "conta");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemTipo() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemTipo();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "tipo");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemValor() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemValor();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "valor");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContabilidadeSemCamposObrigatorios() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTOSemCamposObrigatorios();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "dataVencimento", "grupo", "subGrupo", "descrição", "conta",
                    "tipo", "valor");
            throw e;
        }
    }

    @Test
    public void criarUmaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        assertCodigos(codigos);
    }

    @Test
    public void criarDuasContabilidades() throws BusinessException {
        List<Long> codigos1 = criarContabilidades();
        List<Long> codigos2 = criarContabilidades();

        for (int i = 0; i < codigos1.size(); i++) {
            Long codigo1 = codigos1.get(i);
            Long codigo2 = codigos2.get(i);
            assertThat(codigo1, not(equalTo(codigo2)));
        }
    }

    @Test
    public void criarUmaContabilidadeNaoParcelada() throws BusinessException {
        List<Long> codigos = criarContabilidadeNaoParcelada();

        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertNaoRecorrenteNaoParceladoNaoCartao(dto, "Aluguel mensal do apartamento");
    }

    @Test(expected = BusinessException.class)
    public void criarContabilidadeRecorrenteComProximoLancamento() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTORecorrenteComProximoLancamento();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar."));
            assertThat(e.getCause().getMessage(), equalTo("A contabilidade já possui proximoLancamento."));
            throw e;
        }
    }

    @Test
    public void criarContabilidadeRecorrenteTest() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrente();

        List<Long> codigos = business.criar(dto);
        assertCodigos(codigos, 9);
        assertCodigosRecorrentes(codigos);
    }

    @Test
    public void criarContabilidadeRecorrenteUltimoMes() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrenteUltimoMes();

        List<Long> codigos = business.criar(dto);
        assertCodigos(codigos, 1);
        dto = business.buscarPorId(codigos.get(0));

        assertRecorrente(dto, "27/12/2018", "Aluguel mensal do apartamento", null);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.codigo(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "código");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDataLancamento() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.dataLancamento(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "dataLançamento");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDataVencimento() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.dataVencimento(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "dataVencimento");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemRecorrente() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.recorrente(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "recorrente");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemGrupo() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.grupo(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "grupo");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemSubGrupo() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.subGrupo(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "subGrupo");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDescricao() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.descricao(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "descrição");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemUsouCartao() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.usouCartao(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "usouCartão");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCartao() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.cartao(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "cartão");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemParcelado() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.parcelado(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "parcelado");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemParcela() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.parcela(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "parcela");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemParcelas() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.parcelas(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "parcelas");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemConta() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.conta(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "conta");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemTipo() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.tipo(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "tipo");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemValor() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.valor(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "valor");
        }
    }

    @Test
    public void alterarSemCodigoPaiTestandoPai() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
        dtoBuscado.codigoPai(null);
        business.atualizar(dtoBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigoPaiTestandoFilho() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(1));
            dtoBuscado.codigoPai(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "códigoPai");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDoCodigo() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigoAnterior = String.valueOf(codigos.get(0));
            String codigoNovo = "10";

            ContabilidadeDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigoAnterior));
            assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
            dtoBuscado.codigo(codigoNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "código");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDaDataLancamento() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigo = String.valueOf(codigos.get(0));
            String dataLancamentoNova = getStringNextMonth();

            ContabilidadeDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigo));
            dtoBuscado.dataLancamento(dataLancamentoNova);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "dataLançamento");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarRecorrenteDaContabilidade() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            Long codigo = codigos.get(0);

            ContabilidadeDTO dto = business.buscarPorId(codigo);

            assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

            dto.recorrente("S");
            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar."));
            CreateException e1 = (CreateException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Uma contabilidade não pode ser recorrente e parcelada."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDaParcelas() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigo = String.valueOf(codigos.get(1));
            String parcelasNova = "20";

            ContabilidadeDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigo));
            dtoBuscado.parcelas(parcelasNova);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "parcelas");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDaParcelaPai() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigo = String.valueOf(codigos.get(0));
            String parcelaNova = "20";

            ContabilidadeDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigo));
            dtoBuscado.parcela(parcelaNova);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "parcela");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDaParcelaFilho() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigo = String.valueOf(codigos.get(1));
            String parcelaNova = "20";

            ContabilidadeDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigo));
            dtoBuscado.parcela(parcelaNova);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "parcela");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDoCodigoPaiUsandoPai() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigo = String.valueOf(codigos.get(0));
            String codigoPaiNovo = "20";

            ContabilidadeDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigo));
            dtoBuscado.codigoPai(codigoPaiNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "códigoPai");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDoCodigoPaiUsandoFilho() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigo = String.valueOf(codigos.get(1));
            String codigoPaiNovo = "20";

            ContabilidadeDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigo));
            dtoBuscado.codigoPai(codigoPaiNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "códigoPai");
        }
    }

    @Test
    public void alterarDataVencimentoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.dataVencimento("27/05/2018");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/05/2018", null, "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarDataPagamentoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.dataPagamento(getStringFromCurrentDate());
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", getStringFromCurrentDate(), "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarGrupoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.grupo("Um grupo");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Um grupo",
                "Suplementos", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarSubGrupoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.subGrupo("Um subGrupo");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Um subGrupo", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarDescricaoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.descricao("Nova descrição");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Nova descrição", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarUsouCartaoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.usouCartao("N");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "N", null,
                "N", null, null, "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarCartaoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.cartao("1234");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "S", "1234",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarContaDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.conta("OUTRA");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "OUTRA", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarTipoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.tipo("CREDITO");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "CREDITO", "24,04", null,
                null);
    }

    @Test
    public void alterarValorDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.valor("1000,00");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "1.000,00", null,
                null);
    }

    @Test
    public void alterarParceladoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.parcelado("N");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "S", "0744",
                "N", null, null, "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test(expected = BusinessException.class)
    public void alterarContabilidadesSubsequentesException() throws BusinessException {
        try {
            business.atualizarSubsequentes(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar recursivamente."));
            throw e;
        }
    }

    @Test
    public void alterarContabilidadeSubsequentesParceladas() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.descricao("Outra descrição");
        business.atualizarSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigo);

        int parcelas = Integer.parseInt(dto.parcelas());
        String codigoPai = null;
        for (int i = 0; i < parcelas; i++) {
            dto = dtos.get(i);
            String parcela = String.valueOf(i + 1);

            assertParcelado(dto, "Outra descrição", parcela, codigoPai);

            if (i == 0) {
                codigoPai = dtos.get(0).codigo();
            }
        }
    }

    @Test
    public void alterarContabilidadeSubsequentesRecorrentes() throws BusinessException {
        List<Long> codigos = criarContabilidadeRecorrente();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertRecorrente(dto, "27/04/2018", "Aluguel mensal do apartamento", "1");

        dto.descricao("Outra descrição");
        business.atualizarSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsRecorrentes(codigo);

        assertRecorrentes(dtos, "27/04/2018", "Outra descrição");
    }

    @Test
    public void alterarContabilidadeSubsequentesParceladasParciais() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(1);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "2", "0");

        dto.descricao("Outra descrição");
        business.atualizarSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(Long.parseLong(dto.codigoPai()));

        int parcelas = Integer.parseInt(dto.parcelas());
        String codigoPai = null;
        for (int i = 0; i < parcelas; i++) {
            dto = dtos.get(i);
            String parcela = String.valueOf(i + 1);

            if (i == 0) {
                assertParcelado(dto, "Suplementos comprados pela Carol", parcela, codigoPai);
            }
            else {
                assertParcelado(dto, "Outra descrição", parcela, codigoPai);
            }

            if (i == 0) {
                codigoPai = dtos.get(0).codigo();
            }
        }
    }

    @Test
    public void alterarContabilidadeSubsequentesRecorrentesParciais() throws BusinessException {
        List<Long> codigos = criarContabilidadeRecorrente();
        Long codigo = codigos.get(1);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertRecorrente(dto, "27/05/2018", "Aluguel mensal do apartamento", "2");

        dto.descricao("Outra descrição");
        business.atualizarSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsRecorrentes(codigo);

        assertRecorrentes(dtos, "27/05/2018", "Outra descrição");
    }

    @Test
    public void alterarContabilidadeSubsequentesNaoParcelado() throws BusinessException {
        List<Long> codigos = criarContabilidadeNaoParcelada();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertNaoRecorrenteNaoParceladoNaoCartao(dto, "Aluguel mensal do apartamento");

        dto.descricao("Outra descrição");
        business.atualizarSubsequentes(dto);

        dto = business.buscarPorId(codigo);

        assertNaoRecorrenteNaoParceladoNaoCartao(dto, "Outra descrição");
    }

    @Test(expected = BusinessException.class)
    public void excluirUmaContabilidadeException() throws BusinessException {
        try {
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
            throw e;
        }
    }

    @Test
    public void excluirUmaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorId(codigo);

        business.excluir(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void incrementarContabilidadesRecorrentesComMenosDe1Ano() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTORecorrente();
            business.criar(dto);
            ContabilidadeDTO dto2 = contabilidadeDTORecorrente2();
            business.criar(dto2);

            business.incrementarRecorrentes(0);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar recorrentes."));
            assertThat(e.getCause().getMessage(), equalTo("O valor de anos deve ser maior ou igual a 1."));
            throw e;
        }
    }

    @Test
    public void incrementarContabilidadesRecorrentesComMaisDe1Ano() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrente();
        business.criar(dto);

        List<Long> codigosAtualizados = business.incrementarRecorrentes(2);

        assertRecorrentesTotais("27/12/2018", codigosAtualizados, 13, 1);
    }

    @Test(expected = BusinessException.class)
    public void excluirSubsequentesException() throws BusinessException {
        try {
            business.excluirSubsequentes(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir subsequentes."));
            throw e;
        }
    }

    @Test
    public void excluirSubsequentesParceladas() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorId(codigo);

        business.excluirSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigo);
        assertThat(dtos.size(), equalTo(0));
    }

    @Test
    public void excluirSubsequentesParceladasParcial() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        ContabilidadeDTO dto = business.buscarPorId(codigos.get(1));

        business.excluirSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigos.get(0));
        assertThat(dtos.size(), equalTo(1));

        assertParcelado(dtos.get(0), "Suplementos comprados pela Carol", "1", null);
    }

    @Test
    public void excluirSubsequentesRecorrentes() throws BusinessException {
        List<Long> codigos = criarContabilidadeRecorrente();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorId(codigo);

        business.excluirSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsRecorrentes(codigo);
        assertThat(dtos.size(), equalTo(0));
    }

    @Test
    public void excluirSubsequentesRecorrentesParcial() throws BusinessException {
        List<Long> codigos = criarContabilidadeRecorrente();
        ContabilidadeDTO dto = business.buscarPorId(codigos.get(1));

        business.excluirSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsRecorrentes(codigos.get(0));
        assertThat(dtos.size(), equalTo(1));

        assertRecorrente(dtos.get(0), "27/04/2018", "Aluguel mensal do apartamento", null);
    }

    @Test(expected = BusinessException.class)
    public void realizarPagamentoException() throws BusinessException {
        try {
            business.realizarPagamento(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível realizar o pagamento."));
            throw e;
        }
    }

    @Test
    public void realizarPagamento() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);
        assertThat(dto.dataPagamento(), nullValue());

        business.realizarPagamento(codigo);
        dto = business.buscarPorId(codigo);
        assertThat(dto.dataPagamento(), equalTo(getStringFromCurrentDate()));
    }

    @Test(expected = BusinessException.class)
    public void buscarContabilidadePorIdException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            business.buscarPorId(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar por id."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void buscarContabilidadesException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            business.buscarTodas();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar todas."));
            throw e;
        }
    }

    @Test
    public void buscarContabilidades() throws BusinessException {
        criarContabilidades();
        criarContabilidadeRecorrente();

        List<ContabilidadeDTO> dtos = business.buscarTodas();
        dtos.sort(Comparator
                .comparing((ContabilidadeDTO c) -> Long.parseLong(c.codigo())));

        assertThat(dtos.size(), equalTo(16));

        assertEntitiesParceladas(dtos.subList(0, 7));
        assertEntitiesRecorrentes(dtos.subList(7, 16));
    }

    @Test(expected = BusinessException.class)
    public void buscarTodasAsParcelasException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            business.buscarTodasAsParcelas(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscas todas as parcelas."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void buscarTodasAsRecorrentesException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            business.buscarTodasAsRecorrentes(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscas todas as recorrentes."));
            throw e;
        }
    }

    @Test
    public void buscarTodasAsParcelas() throws BusinessException {
        Long codigo = criarContabilidades().get(0);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigo);
        assertThat(dtos.size(), equalTo(7));
        assertEntitiesParceladas(dtos);
    }

    private List<Long> criarContabilidades() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTO();
        List<Long> codigos = business.criar(dto);
        return assertCodigos(codigos, 7);
    }

    private List<Long> criarContabilidadeNaoParcelada() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTONaoRecorrenteNaoParcelada();
        List<Long> codigos = business.criar(dto);
        return assertCodigos(codigos, 1);
    }

    private List<Long> criarContabilidadeRecorrente() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrente();
        List<Long> codigos = business.criar(dto);
        return assertCodigos(codigos, 9);
    }

    private List<Long> assertCodigos(List<Long> codigos, int size) {
        assertThat(codigos.size(), equalTo(size));
        for (Long codigo : codigos) {
            assertTrue(codigo >= 0);
        }
        return codigos;
    }

    private void assertCodigos(List<Long> codigos) throws BusinessException {
        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigos.get(0));
        assertEntitiesParceladas(dtos);
    }

    private void assertEntitiesParceladas(List<ContabilidadeDTO> dtos) {
        String codigoPai = null;
        for (int i = 0; i < dtos.size(); i++) {
            ContabilidadeDTO dto = dtos.get(i);
            assertParcelado(dto, "Suplementos comprados pela Carol", String.valueOf(i + 1), codigoPai);
            if (i == 0) {
                codigoPai = dto.codigo();
            }
        }
    }

    private void assertEntitiesRecorrentes(List<ContabilidadeDTO> dtos) throws BusinessException {
        List<Long> codigos = new ArrayList<>();
        for (ContabilidadeDTO dto : dtos) {
            long codigo = Long.parseLong(dto.codigo());
            codigos.add(codigo);
        }
        assertCodigosRecorrentes(codigos);
    }

    private void assertRecorrentes(List<ContabilidadeDTO> dtos, String dataVencimento, String descricao) throws BusinessException {
        String dataVencimentoLocal = dataVencimento;
        int teto = dtos.size();
        for (int i = 0; i < teto; i++) {
            ContabilidadeDTO dto = dtos.get(i);
            Long codigo = Long.parseLong(dto.codigo());

            if (i == (teto - 1)) {
                assertRecorrente(dto, dataVencimentoLocal, descricao, null);
            }
            else {
                assertRecorrente(dto, dataVencimentoLocal, descricao, String.valueOf(codigo + 1));
            }
            dataVencimentoLocal = getStringNextMonth(dataVencimentoLocal);
        }
    }

    private void assertCodigosRecorrentes(List<Long> codigos) throws BusinessException {
        String dataVencimento = "27/04/2018";
        int teto = 9;
        for (int i = 0; i < teto; i++) {
            Long codigo = codigos.get(i);
            ContabilidadeDTO dto = business.buscarPorId(codigo);

            if (i == (teto - 1)) {
                assertRecorrente(dto, dataVencimento, "Aluguel mensal do apartamento", null);
            }
            else {
                assertRecorrente(dto, dataVencimento, "Aluguel mensal do apartamento", String.valueOf(codigo + 1));
            }
            dataVencimento = getStringNextMonth(dataVencimento);
        }
    }

    private void assertRecorrentesTotais(String dataVencimento, List<Long> codigosAtualizados, int totalDeRegistros,
                                         int passoProximoRegistro) throws BusinessException {
        String dataVencimentoLocal = dataVencimento;
        ContabilidadeDTO dto;
        for (int i = 0; i < totalDeRegistros; i++) {
            Long codigo = codigosAtualizados.get(i);
            dto = business.buscarPorId(codigo);
            String proximoLancamento;
            if (i == (totalDeRegistros - 1)) {
                proximoLancamento = null;
            }
            else {
                proximoLancamento = String.valueOf(codigo + passoProximoRegistro);
            }

            assertRecorrente(dto, dataVencimentoLocal, "Aluguel mensal do apartamento", proximoLancamento);

            LocalDate localDate = getDateFromString(dataVencimentoLocal);
            localDate = localDate.plusMonths(1L);
            dataVencimentoLocal = getStringFromDate(localDate);
        }
    }

    private void assertNaoRecorrenteNaoParceladoNaoCartao(ContabilidadeDTO dto, String descricao) {
        assertEntityDTO(dto, "27/04/2018", null, "N", "Apartamento",
                "Aluguel", descricao, "N", null, "N", null, null,
                "MORADIA", "DEBITO", "1.000,00", null, null);
    }

    private void assertNaoParcelado(ContabilidadeDTO dto) {
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Suplementos comprados pela Carol", "N", null,
                "N", null, null, "CAROL", "DEBITO", "24,04", null,
                null);
    }

    private void assertRecorrente(ContabilidadeDTO dto, String dataVencimento, String descricao, String proximoLancamento) {
        assertEntityDTO(dto, dataVencimento, null, "S", "Apartamento",
                "Aluguel", descricao, "N", null,
                "N", null, null, "MORADIA", "DEBITO", "1.000,00",
                null, proximoLancamento);
    }

    private void assertParcelado(ContabilidadeDTO dto, String descricao, String parcela, String codigoPai) {
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", descricao, "S", "0744",
                "S", parcela, "7", "CAROL", "DEBITO", "24,04", codigoPai,
                null);
    }

    private void assertEntityDTO(ContabilidadeDTO dto, String dataVencimento, String dataPagamento, String recorrente,
                                 String grupo, String subGrupo, String descricao, String usouCartao, String cartao,
                                 String parcelado, String parcela, String parcelas, String conta, String tipo,
                                 String valor, String codigoPai, String proximoLancamento) {
        assertThat(dto.codigo(), not(nullValue()));
        assertThat(dto.dataLancamento(), not(nullValue()));
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo(dataVencimento));

        if (isBlankOrNull(dataPagamento)) {
            assertThat(dto.dataPagamento(), nullValue());
        }
        else {
            assertThat(dto.dataPagamento(), equalTo(dataPagamento));
        }

        assertThat(dto.recorrente(), equalTo(recorrente));
        assertThat(dto.grupo(), equalTo(grupo));
        assertThat(dto.subGrupo(), equalTo(subGrupo));
        assertThat(dto.descricao(), equalTo(descricao));
        assertThat(dto.usouCartao(), equalTo(usouCartao));

        if (isBlankOrNull(cartao)) {
            assertThat(dto.cartao(), nullValue());
        }
        else {
            assertThat(dto.cartao(), equalTo(cartao));
        }

        assertThat(dto.conta(), equalTo(conta));
        assertThat(dto.tipo(), equalTo(tipo));
        assertThat(dto.valor(), equalTo(valor));
        assertThat(dto.parcelado(), equalTo(parcelado));

        if (isBlankOrNull(parcela)) {
            assertThat(dto.parcela(), nullValue());
        }
        else {
            assertThat(dto.parcela(), equalTo(parcela));
        }

        if (isBlankOrNull(parcelas)) {
            assertThat(dto.parcelas(), nullValue());
        }
        else {
            assertThat(dto.parcelas(), equalTo(parcelas));
        }

        if (isBlankOrNull(codigoPai)) {
            assertThat(dto.codigoPai(), nullValue());
        }
        else {
            assertThat(dto.codigoPai(), equalTo(codigoPai));
        }

        if (isBlankOrNull(proximoLancamento)) {
            assertThat(dto.proximoLancamento(), nullValue());
        }
        else {
            assertThat(dto.proximoLancamento(), equalTo(proximoLancamento));
        }
    }
}
