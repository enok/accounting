package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.CreateException;
import br.com.accounting.business.service.impl.ContabilidadeBusinessImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.IOException;
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
        assertThat(dtoBuscado.usouCartao(), equalTo("N"));
        assertThat(dtoBuscado.cartao(), nullValue());
        assertThat(dtoBuscado.parcelado(), equalTo("N"));
        assertThat(dtoBuscado.parcela(), nullValue());
        assertThat(dtoBuscado.parcelas(), nullValue());
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
            assertCreationAndMandatoryFields(e, "dataVencimento", "grupo", "subGrupo", "descrição", "conta", "tipo", "valor");
            throw e;
        }
    }

    @Test
    public void criarUmaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        assertEntities(codigos);
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

        assertContabilidadeNaoParcelada(dto, "27/04/2018", null);
    }

    @Test(expected = BusinessException.class)
    public void criarContabilidadeRecorrenteNaoRecorrente() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTO();
            business.criarRecorrente(dto, 3);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar recorrente."));
            assertThat(e.getCause().getMessage(), equalTo("A contabilidade não é recorrente."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarContabilidadeRecorrenteComMesesMenorOuIgualA0() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParcelada();
            business.criarRecorrente(dto, 0);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar recorrente."));
            assertThat(e.getCause().getMessage(), equalTo("O valor de meses deve ser maior que 0."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarContabilidadeRecorrenteComProximoLancamento() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParceladaComProximoLancamento();
            business.criarRecorrente(dto, 3);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar recorrente."));
            assertThat(e.getCause().getMessage(), equalTo("A contabilidade já possui proximoLancamento."));
            throw e;
        }
    }

    @Test
    public void criarContabilidadeRecorrenteAtualizando() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParcelada();

        List<Long> codigos = business.criar(dto);
        assertCodigos(codigos, 1);
        dto = business.buscarPorId(codigos.get(0));

        codigos = business.criarRecorrente(dto, 3);
        assertCodigos(codigos, 3);
        assertCriarContabilidadeRecorrente(codigos);
    }

    @Test
    public void criarContabilidadeRecorrente() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParcelada();

        List<Long> codigos = business.criarRecorrente(dto, 3);
        assertCodigos(codigos, 3);
        assertCriarContabilidadeRecorrente(codigos);
    }

    @Test(expected = BusinessException.class)
    public void atualizarContabilidadesRecorrentesComAnosMenorQue1() throws BusinessException {
        try {
            ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParcelada();
            business.criar(dto);
            ContabilidadeDTO dto2 = contabilidadeDTORecorrenteNaoParcelada2();
            business.criar(dto2);

            business.atualizarRecorrentes(0);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar recorrentes."));
            assertThat(e.getCause().getMessage(), equalTo("O valor de anos deve ser maior ou igual a 1."));
            throw e;
        }
    }

    @Test
    public void atualizarContabilidadesRecorrentes() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParcelada();
        business.criar(dto);
        ContabilidadeDTO dto2 = contabilidadeDTORecorrenteNaoParcelada2();
        business.criar(dto2);

        List<Long> codigosAtualizados = business.atualizarRecorrentes(1);

        Long codigo0 = codigosAtualizados.get(0);
        ContabilidadeDTO dto0 = business.buscarPorId(codigo0);
        String dataVencimento = "27/04/2018";
        assertContabilidadeNaoParcelada(dto0, dataVencimento, "2");
        assertContabilidadeRecorrenteParcial(codigosAtualizados, dataVencimento, 1, 9, 8, 2);

        Long codigo1 = codigosAtualizados.get(9);
        ContabilidadeDTO dto1 = business.buscarPorId(codigo1);
        dataVencimento = "27/10/2018";
        assertContabilidadeNaoParcelada(dto1, dataVencimento, "10");
        assertContabilidadeRecorrenteParcial(codigosAtualizados, dataVencimento, 10, 12, 11, 1);
    }

    @Test
    public void atualizarContabilidadesRecorrentesSeguidos() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParcelada();
        business.criar(dto);

        List<Long> codigosAtualizados = business.atualizarRecorrentes(1);

        assertContabilidadeRecorrenteTotal("27/04/2018", codigosAtualizados, 9, 1);

        codigosAtualizados = business.atualizarRecorrentes(2);

        assertContabilidadeRecorrenteTotal("27/12/2018", codigosAtualizados, 13, 9);
    }

    @Test
    public void atualizarContabilidadesRecorrentesComMaisDe1Ano() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParcelada();
        business.criar(dto);

        List<Long> codigosAtualizados = business.atualizarRecorrentes(2);

        assertContabilidadeRecorrenteTotal("27/04/2018", codigosAtualizados, 21, 1);
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
            String dataLancamentoNova = getStringFromCurrentDateNextMonth();

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

            assertEntityDTO(dto, null, "S", "1", "7");

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

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.dataVencimento("27/05/2018");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        //
        assertThat(dto.dataVencimento(), equalTo("27/05/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarDataPagamentoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.dataPagamento(getStringFromCurrentDate());
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        //
        assertThat(dto.dataPagamento(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarGrupoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.grupo("Um grupo");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        //
        assertThat(dto.grupo(), equalTo("Um grupo"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarSubGrupoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.subGrupo("Um subGrupo");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        //
        assertThat(dto.subGrupo(), equalTo("Um subGrupo"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarDescricaoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.descricao("Nova descrição");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        //
        assertThat(dto.descricao(), equalTo("Nova descrição"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarUsouCartaoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.usouCartao("N");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        //
        assertThat(dto.usouCartao(), equalTo("N"));
        assertThat(dto.cartao(), nullValue());
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("N"));
        assertThat(dto.parcela(), nullValue());
        assertThat(dto.parcelas(), nullValue());
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarCartaoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.cartao("1234");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        //
        assertThat(dto.cartao(), equalTo("1234"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarContaDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.conta("OUTRA");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        //
        assertThat(dto.conta(), equalTo("OUTRA"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarTipoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.tipo("CREDITO");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        //
        assertThat(dto.tipo(), equalTo("CREDITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarValorDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.valor("1000,00");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        //
        assertThat(dto.valor(), equalTo("1.000,00"));
        assertThat(dto.parcelado(), equalTo("S"));
        assertThat(dto.parcela(), equalTo("1"));
        assertThat(dto.parcelas(), equalTo("7"));
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test
    public void alterarParceladoDaContabilidade() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.parcelado("N");
        business.atualizar(dto);

        dto = business.buscarPorId(codigo);
        assertThat(dto.dataAtualizacao(), equalTo(getStringFromCurrentDate()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        //
        assertThat(dto.parcelado(), equalTo("N"));
        assertThat(dto.parcela(), nullValue());
        assertThat(dto.parcelas(), nullValue());
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), nullValue());
    }

    @Test(expected = BusinessException.class)
    public void alterarContabilidadeRecursivamenteNaoParcelado() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidadeNaoParcelada();
            Long codigo = codigos.get(0);

            ContabilidadeDTO dto = business.buscarPorId(codigo);

            dto.descricao("Outra descrição");
            business.atualizarRecursivamente(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível atualizar recursivamente."));
            throw e;
        }
    }

    @Test
    public void alterarContabilidadeRecursivamente() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, null, "S", "1", "7");

        dto.descricao("Outra descrição");
        business.atualizarRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigo);

        int parcelas = Integer.parseInt(dto.parcelas());
        String codigoPai = null;
        for (int i = 0; i < parcelas; i++) {
            dto = dtos.get(i);
            String parcela = String.valueOf(i + 1);

            assertEntityDTO(dto, codigoPai, "Outra descrição", "S", parcela, "7");

            if (i == 0) {
                codigoPai = dtos.get(0).codigo();
            }
        }
    }

    @Test
    public void alterarContabilidadeRecursivamenteParcial() throws BusinessException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(1);

        ContabilidadeDTO dto = business.buscarPorId(codigo);

        assertEntityDTO(dto, "0", "S", "2", "7");

        dto.descricao("Outra descrição");
        business.atualizarRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(Long.parseLong(dto.codigoPai()));

        int parcelas = Integer.parseInt(dto.parcelas());
        String codigoPai = null;
        for (int i = 0; i < parcelas; i++) {
            dto = dtos.get(i);
            String parcela = String.valueOf(i + 1);

            if (i == 0) {
                assertEntityDTO(dto, codigoPai, "Suplementos comprados pela Carol", "S", parcela, "7");
            }
            else {
                assertEntityDTO(dto, codigoPai, "Outra descrição", "S", parcela, "7");
            }

            if (i == 0) {
                codigoPai = dtos.get(0).codigo();
            }
        }
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
        criarContabilidadeNaoParcelada();

        List<ContabilidadeDTO> dtos = business.buscarTodas();
        dtos.sort(Comparator
                .comparing(ContabilidadeDTO::codigo));
        assertThat(dtos.size(), equalTo(8));

        assertEntitiesDTOS(dtos.subList(0, 7));
        assertContabilidadeNaoParcelada(dtos.get(7), "27/04/2018", null);
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

    @Test
    public void buscarTodasAsParcelas() throws BusinessException {
        Long codigo = criarContabilidades().get(0);

        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigo);
        assertThat(dtos.size(), equalTo(7));
        assertEntitiesDTOS(dtos);
    }

    private List<Long> criarContabilidades() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTO();
        List<Long> codigos = business.criar(dto);
        return assertCodigos(codigos, 7);
    }

    private List<Long> criarContabilidadeNaoParcelada() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTORecorrenteNaoParcelada();
        List<Long> codigos = business.criar(dto);
        return assertCodigos(codigos, 1);
    }

    private List<Long> assertCodigos(List<Long> codigos, int size) {
        assertThat(codigos.size(), equalTo(size));
        for (Long codigo : codigos) {
            assertTrue(codigo >= 0);
        }
        return codigos;
    }

    private void assertEntities(List<Long> codigos) throws BusinessException {
        List<ContabilidadeDTO> dtos = business.buscarTodasAsParcelas(codigos.get(0));
        assertEntitiesDTOS(dtos);
    }

    private void assertEntitiesDTOS(List<ContabilidadeDTO> dtos) {
        String codigoPai = null;
        for (int i = 0; i < dtos.size(); i++) {
            ContabilidadeDTO dto = dtos.get(i);
            assertEntityDTO(dto, codigoPai, i);
            if (i == 0) {
                codigoPai = dto.codigo();
            }
        }
    }

    private void assertEntityDTO(ContabilidadeDTO dto, String codigoPai, int i) {
        String parcelado = "S";
        String parcela = String.valueOf(i + 1);
        String parcelas = "7";
        assertEntityDTO(dto, codigoPai, parcelado, parcela, parcelas);
    }

    private void assertEntityDTO(ContabilidadeDTO dto, String codigoPai, String parcelado, String parcela, String parcelas) {
        assertEntityDTO(dto, codigoPai, "Suplementos comprados pela Carol", parcelado, parcela, parcelas);
    }

    private void assertEntityDTO(ContabilidadeDTO dto, String codigoPai, String descricao, String parcelado, String parcela, String parcelas) {
        assertThat(dto.codigo(), not(nullValue()));
        assertThat(dto.dataLancamento(), not(nullValue()));
        assertThat(dto.dataAtualizacao(), not(nullValue()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo(descricao));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo(parcelado));
        assertThat(dto.parcela(), equalTo(parcela));
        assertThat(dto.parcelas(), equalTo(parcelas));
        assertThat(dto.codigoPai(), equalTo(codigoPai));
        assertThat(dto.proximoLancamento(), nullValue());
    }

    private void assertContabilidadeNaoParcelada(ContabilidadeDTO dto, String dataVencimento, String proximoLancamento) {
        assertThat(dto.codigo(), not(nullValue()));
        assertThat(dto.dataLancamento(), not(nullValue()));
        assertThat(dto.dataAtualizacao(), not(nullValue()));
        assertThat(dto.dataVencimento(), equalTo(dataVencimento));
        assertThat(dto.dataPagamento(), nullValue());
        assertThat(dto.recorrente(), equalTo("S"));
        assertThat(dto.grupo(), equalTo("Apartamento"));
        assertThat(dto.subGrupo(), equalTo("Aluguel"));
        assertThat(dto.descricao(), equalTo("Aluguel mensal do apartamento"));
        assertThat(dto.usouCartao(), equalTo("N"));
        assertThat(dto.cartao(), nullValue());
        assertThat(dto.conta(), equalTo("MORADIA"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("1.000,00"));
        assertThat(dto.parcelado(), equalTo("N"));
        assertThat(dto.parcela(), nullValue());
        assertThat(dto.parcelas(), nullValue());
        assertThat(dto.codigoPai(), nullValue());
        assertThat(dto.proximoLancamento(), equalTo(proximoLancamento));
    }

    private void assertCriarContabilidadeRecorrente(List<Long> codigos) throws BusinessException {
        Long codigo1 = codigos.get(0);
        ContabilidadeDTO dto1 = business.buscarPorId(codigo1);

        Long codigo2 = codigos.get(1);
        ContabilidadeDTO dto2 = business.buscarPorId(codigo2);

        Long codigo3 = codigos.get(2);
        ContabilidadeDTO dto3 = business.buscarPorId(codigo3);

        assertContabilidadeNaoParcelada(dto1, "27/04/2018", codigo2.toString());
        assertContabilidadeNaoParcelada(dto2, "27/05/2018", codigo3.toString());
        assertContabilidadeNaoParcelada(dto3, "27/06/2018", null);
    }

    private void assertContabilidadeRecorrenteParcial(List<Long> codigosAtualizados, String dataVencimento, int inicio,
                                                      int teto, int ultimoLancamento, int passoProximoLancamento) throws BusinessException {
        ContabilidadeDTO dto;
        for (; inicio < teto; inicio++) {
            Long codigo = codigosAtualizados.get(inicio);
            dto = business.buscarPorId(codigo);
            dataVencimento = getStringFromDateNextMonth(dataVencimento);
            String proximoLancamento;
            if (inicio == ultimoLancamento) {
                proximoLancamento = null;
            }
            else {
                proximoLancamento = String.valueOf(inicio + passoProximoLancamento);
            }
            assertContabilidadeNaoParcelada(dto, dataVencimento, proximoLancamento);
        }
    }

    private void assertContabilidadeRecorrenteTotal(String dataVencimento, List<Long> codigosAtualizados, int totalDeRegistros,
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
                proximoLancamento = String.valueOf(i + passoProximoRegistro);
            }
            assertContabilidadeNaoParcelada(dto, dataVencimentoLocal, proximoLancamento);
            dataVencimentoLocal = getStringFromDateNextMonth(dataVencimentoLocal);
        }
    }
}
