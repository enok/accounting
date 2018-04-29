package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.CreateException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.factory.ContabilidadeDTOMockFactory;
import br.com.accounting.business.service.impl.ContabilidadeBusinessImpl;
import br.com.accounting.core.exception.StoreException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static br.com.accounting.core.util.Utils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class ContabilidadeBusinessTest extends GenericTest {
    @Autowired
    private ContabilidadeBusiness business;

    @Test(expected = StoreException.class)
    public void criarUmaContabilidadeSemDiretorio() throws StoreException, BusinessException, GenericException, IOException {
        try {
            deletarDiretorioEArquivos();
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTO();
            business.criar(dto);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível ler as linhas do arquivo: D:\\tmp\\arquivos\\contabilidades-contagem.txt"));
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void criarUmaContabilidadeNaoValidaRegistroDuplicado() {
        try {
            ((ContabilidadeBusinessImpl) business).validaRegistroDuplicado(null);
        }
        catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("Uma contabilidade não valida duplicidade de registro."));
            throw e;
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemDataVencimento() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemDataVencimento();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "dataVencimento");
        }
    }

    @Test
    public void criarUmaContabilidadeSemDataPagamento() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemDataPagamento();
        Long codigo = business.criar(dto).get(0);
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.dataPagamento(), nullValue());
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemRecorrente() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemRecorrente();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "recorrente");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemGrupo() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemGrupo();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "grupo");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemSubGrupo() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemSubGrupo();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "subGrupo");
        }
    }

    @Test
    public void criarUmaContabilidadeSemLocal() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemLocal();
        Long codigo = business.criar(dto).get(0);
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.local(), nullValue());
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemDescricao() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemDescricao();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "descrição");
        }
    }

    @Test
    public void criarUmaContabilidadeSemUsouCartao() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemUsouCartao();
        Long codigo = business.criar(dto).get(0);
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.usouCartao(), equalTo("N"));
    }

    @Test
    public void criarUmaContabilidadeNaoUsouCartao() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTONaoUsouCartao();
        Long codigo = business.criar(dto).get(0);
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
        assertNaoParcelado(dtoBuscado);
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemCartao() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemCartao();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "cartão");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemParcelado() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemParcelado();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "parcelado");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemParcelas() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemParcelas();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "parcelas");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemConta() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemConta();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "conta");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemTipo() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemTipo();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "tipo");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemValor() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemValor();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "valor");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContabilidadeSemCamposObrigatorios() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTOSemCamposObrigatorios();
            business.criar(dto);
        }
        catch (MissingFieldException e) {
            assertCreationAndMandatoryFields(e, "dataVencimento", "grupo", "subGrupo", "descrição", "conta",
                                             "tipo", "valor");
            throw e;
        }
    }

    @Test
    public void criarUmaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        assertCodigos(codigos);
    }

    @Test
    public void criarDuasContabilidades() throws StoreException, BusinessException, GenericException {
        List<Long> codigos1 = criarContabilidades();
        List<Long> codigos2 = criarContabilidades();

        for (int i = 0; i < codigos1.size(); i++) {
            Long codigo1 = codigos1.get(i);
            Long codigo2 = codigos2.get(i);
            assertThat(codigo1, not(equalTo(codigo2)));
        }
    }

    @Test
    public void criarUmaContabilidadeNaoParcelada() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeNaoParcelada();

        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertNaoRecorrenteNaoParceladoNaoCartao(dto, "Aluguel mensal do apartamento");
    }

    @Test(expected = BusinessException.class)
    public void criarContabilidadeRecorrenteComProximoLancamento() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTORecorrenteComProximoLancamento();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("A contabilidade já possui proximoLancamento."));
            throw e;
        }
    }

    @Test
    public void criarContabilidadeRecorrenteTest() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTORecorrente();
        List<Long> codigos = business.criar(dto);
        assertCodigos(codigos, 9);
        assertCodigosRecorrentes(codigos);
    }

    @Test
    public void criarContabilidadeRecorrenteUltimoMes() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTORecorrenteUltimoMes();

        List<Long> codigos = business.criar(dto);
        assertCodigos(codigos, 1);
        dto = business.buscarPorId(codigos.get(0));

        assertRecorrente(dto, "27/12/2018", "Aluguel mensal do apartamento", null);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemDataLancamento() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemDataVencimento() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemRecorrente() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemGrupo() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemSubGrupo() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemDescricao() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemUsouCartao() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemCartao() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemParcelado() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemParcela() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemParcelas() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemConta() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemTipo() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemValor() throws StoreException, BusinessException, GenericException {
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
    public void alterarSemCodigoPaiTestandoPai() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
        dtoBuscado.codigoPai(null);
        business.atualizar(dtoBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigoPaiTestandoFilho() throws StoreException, BusinessException, GenericException {
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
    public void alteracaoNaoPermitidaDoCodigo() throws StoreException, BusinessException, GenericException {
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
    public void alteracaoNaoPermitidaDaDataLancamento() throws StoreException, BusinessException, GenericException {
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
    public void alterarRecorrenteDaContabilidade() throws StoreException, BusinessException, GenericException {
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
    public void alteracaoNaoPermitidaDaParcelas() throws StoreException, BusinessException, GenericException {
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
    public void alteracaoNaoPermitidaDaParcelaPai() throws StoreException, BusinessException, GenericException {
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
    public void alteracaoNaoPermitidaDaParcelaFilho() throws StoreException, BusinessException, GenericException {
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
    public void alteracaoNaoPermitidaDoCodigoPaiUsandoPai() throws StoreException, BusinessException, GenericException {
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
    public void alteracaoNaoPermitidaDoCodigoPaiUsandoFilho() throws StoreException, BusinessException, GenericException {
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
    public void alterarDataVencimentoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.dataVencimento("27/05/2018");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/05/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                        "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarDataPagamentoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.dataPagamento(getStringFromCurrentDate());
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", getStringFromCurrentDate(), "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                        "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarGrupoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.grupo("Um grupo");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Um grupo",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                        "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarSubGrupoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.subGrupo("Um subGrupo");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Um subGrupo", "Site", "Suplementos comprados pela Carol", "S", "0744",
                        "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarLocalDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.local("Outro local");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Outro local", "Suplementos comprados pela Carol", "S", "0744",
                        "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarDescricaoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.descricao("Nova descrição");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Nova descrição", "S", "0744",
                        "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarUsouCartaoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.usouCartao("N");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "N", null,
                        "N", null, null, "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarCartaoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.cartao("1234");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "1234",
                        "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarContaDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.conta("OUTRA");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                        "S", "1", "7", "OUTRA", "DEBITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarTipoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.tipo("CREDITO");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                        "S", "1", "7", "CAROL", "CREDITO", "24,04", null,
                        null);
    }

    @Test
    public void alterarValorDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.valor("1000,00");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                        "S", "1", "7", "CAROL", "DEBITO", "1.000,00", null,
                        null);
    }

    @Test
    public void alterarParceladoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertParcelado(dto, "Suplementos comprados pela Carol", "1", null);

        dto.parcelado("N");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
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
    public void alterarContabilidadeSubsequentesParceladas() throws StoreException, BusinessException, GenericException {
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
    public void alterarContabilidadeSubsequentesRecorrentes() throws StoreException, BusinessException, GenericException {
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
    public void alterarContabilidadeSubsequentesParceladasParciais() throws StoreException, BusinessException, GenericException {
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
    public void alterarContabilidadeSubsequentesRecorrentesParciais() throws StoreException, BusinessException, GenericException {
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
    public void alterarContabilidadeSubsequentesNaoParcelado() throws StoreException, BusinessException, GenericException {
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
    public void excluirUmaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorId(codigo);

        business.excluir(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void incrementarContabilidadesRecorrentesComMenosDe1Ano() throws StoreException, BusinessException, GenericException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTORecorrente();
            business.criar(dto);
            ContabilidadeDTO dto2 = ContabilidadeDTOMockFactory.contabilidadeDTORecorrente2();
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
    public void incrementarContabilidadesRecorrentesComMaisDe1Ano() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTORecorrente();
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
    public void excluirSubsequentesParceladas() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorId(codigo);

        business.excluirSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigo);
        assertThat(dtos.size(), equalTo(0));
    }

    @Test
    public void excluirSubsequentesParceladasParcial() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        ContabilidadeDTO dto = business.buscarPorId(codigos.get(1));

        business.excluirSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigos.get(0));
        assertThat(dtos.size(), equalTo(1));

        assertParcelado(dtos.get(0), "Suplementos comprados pela Carol", "1", null);
    }

    @Test
    public void excluirSubsequentesRecorrentes() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeRecorrente();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorId(codigo);

        business.excluirSubsequentes(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsRecorrentes(codigo);
        assertThat(dtos.size(), equalTo(0));
    }

    @Test
    public void excluirSubsequentesRecorrentesParcial() throws StoreException, BusinessException, GenericException {
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
    public void realizarPagamento() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);
        assertThat(dto.dataPagamento(), nullValue());

        business.realizarPagamento(codigo);
        dto = business.buscarPorId(codigo);
        assertThat(dto.dataPagamento(), equalTo(getStringFromCurrentDate()));
    }

    @Test(expected = BusinessException.class)
    public void buscarContabilidadePorIdException() throws BusinessException, IOException {
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
    public void buscarContabilidadesException() throws BusinessException, IOException {
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
    public void buscarContabilidades() throws StoreException, BusinessException, GenericException {
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
    public void buscarTodasAsParcelasException() throws BusinessException, IOException {
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
    public void buscarTodasAsRecorrentesException() throws BusinessException, IOException {
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
    public void buscarTodasAsParcelas() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContabilidades().get(0);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigo);
        assertThat(dtos.size(), equalTo(7));
        assertEntitiesParceladas(dtos);
    }

    private List<Long> criarContabilidades() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTO();
        List<Long> codigos = business.criar(dto);
        return assertCodigos(codigos, 7);
    }

    private List<Long> criarContabilidadeNaoParcelada() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTONaoRecorrenteNaoParcelada();
        List<Long> codigos = business.criar(dto);
        return assertCodigos(codigos, 1);
    }

    private List<Long> criarContabilidadeRecorrente() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTORecorrente();
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

    private void assertEntitiesRecorrentes(List<ContabilidadeDTO> dtos) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = new ArrayList<>();
        for (ContabilidadeDTO dto : dtos) {
            long codigo = Long.parseLong(dto.codigo());
            codigos.add(codigo);
        }
        assertCodigosRecorrentes(codigos);
    }

    private void assertRecorrentes(List<ContabilidadeDTO> dtos, String dataVencimento, String descricao) {
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
                        "Aluguel", null, descricao, "N", null, "N", null, null,
                        "MORADIA", "DEBITO", "1.000,00", null, null);
    }

    private void assertNaoParcelado(ContabilidadeDTO dto) {
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", "Suplementos comprados pela Carol", "N", null,
                        "N", null, null, "CAROL", "DEBITO", "24,04", null,
                        null);
    }

    private void assertRecorrente(ContabilidadeDTO dto, String dataVencimento, String descricao, String proximoLancamento) {
        assertEntityDTO(dto, dataVencimento, null, "S", "Apartamento",
                        "Aluguel", null, descricao, "N", null,
                        "N", null, null, "MORADIA", "DEBITO", "1.000,00",
                        null, proximoLancamento);
    }

    private void assertParcelado(ContabilidadeDTO dto, String descricao, String parcela, String codigoPai) {
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                        "Suplementos", "Site", descricao, "S", "0744",
                        "S", parcela, "7", "CAROL", "DEBITO", "24,04", codigoPai,
                        null);
    }

    private void assertEntityDTO(ContabilidadeDTO dto, String dataVencimento, String dataPagamento, String recorrente,
                                 String grupo, String subGrupo, String local, String descricao, String usouCartao, String cartao,
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
        if (isBlankOrNull(local)) {
            assertThat(dto.local(), nullValue());
        }
        else {
            assertThat(dto.local(), equalTo(local));
        }
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
