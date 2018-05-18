package br.com.accounting.business.service;

import br.com.accounting.business.dto.*;
import br.com.accounting.business.factory.*;
import br.com.accounting.cartao.business.CartaoBusiness;
import br.com.accounting.cartao.dto.CartaoDTO;
import br.com.accounting.cartao.factory.CartaoDTOFactory;
import br.com.accounting.commons.exception.*;
import br.com.accounting.commons.test.GenericTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static br.com.accounting.commons.util.Utils.*;
import static java.lang.Long.parseLong;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class ContabilidadeBusinessTest extends GenericTest {
    @Autowired
    private ContabilidadeBusiness business;
    @Autowired
    private GrupoBusiness grupoBusiness;
    @Autowired
    private SubGrupoBusiness subGrupoBusiness;
    @Autowired
    private LocalBusiness localBusiness;
    @Autowired
    private CartaoBusiness cartaoBusiness;
    @Autowired
    private ContaBusiness contaBusiness;

    @Before
    public void setUp() throws IOException, StoreException, BusinessException, GenericException {
        super.setUp();
        criarGrupoSaude();
        criarGrupoApartamento();
        criarGrupoUm();
        criarSubGrupoSuplementos();
        criarSubGrupoAluguel();
        criarSubGrupoUmSubGrupo();
        criarLocalSite();
        criarLocalOutroLocal();
        criarCartao0744();
        criarCartao1234();
        criarContaCarol();
        criarContaMoradia();
        criarContaOutra();
    }

    @Test(expected = StoreException.class)
    public void criarUmaContabilidadeSemDiretorio() throws StoreException, BusinessException, GenericException, IOException {
        try {
            deletarDiretorioEArquivos();
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTO();
            business.criar(dto);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao salvar."));
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
        ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigo);
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
        ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigo);
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
        ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigo);
        assertThat(dtoBuscado.usouCartao(), equalTo("N"));
    }

    @Test
    public void criarUmaContabilidadeNaoUsouCartao() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTONaoUsouCartao();
        Long codigo = business.criar(dto).get(0);
        ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigo);
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
    public void criarUmaContabilidadeNaoParcelada() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeNaoParcelada();

        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertNaoRecorrenteNaoParceladoNaoCartao(dto, "Aluguel mensal do apartamento");
    }

    @Test(expected = GenericException.class)
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
        dto = business.buscarPorCodigo(codigos.get(0));

        assertRecorrente(dto, "27/12/2018", "Aluguel mensal do apartamento", null);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws StoreException, BusinessException, GenericException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
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
        ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(0));
        dtoBuscado.codigoPai(null);
        business.atualizar(dtoBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigoPaiTestandoFilho() throws StoreException, BusinessException, GenericException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(codigos.get(1));
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

            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(parseLong(codigoAnterior));
            assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
            dtoBuscado.codigo(codigoNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Registro inexistente."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDaDataLancamento() throws StoreException, BusinessException, GenericException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigo = String.valueOf(codigos.get(0));
            String dataLancamentoNova = getStringNextMonth();

            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(parseLong(codigo));
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

            ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

            assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

            dto.recorrente("S");
            business.atualizar(dto);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Uma contabilidade não pode ser recorrente e parcelada."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDaParcelas() throws StoreException, BusinessException, GenericException {
        try {
            List<Long> codigos = criarContabilidades();
            String codigo = String.valueOf(codigos.get(1));
            String parcelasNova = "20";

            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(parseLong(codigo));
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

            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(parseLong(codigo));
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

            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(parseLong(codigo));
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

            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(parseLong(codigo));
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

            ContabilidadeDTO dtoBuscado = business.buscarPorCodigo(parseLong(codigo));
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

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.dataVencimento("27/05/2018");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/05/2018", null, "N", "Saúde",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarDataPagamentoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.dataPagamento(getStringFromCurrentDate());
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", getStringFromCurrentDate(), "N", "Saúde",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarGrupoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.grupo("Um grupo");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Um grupo",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarSubGrupoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.subGrupo("Um subGrupo");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Um subGrupo", "Site", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarLocalDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.local("Outro local");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Outro local", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarDescricaoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.descricao("Nova descrição");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Site", "Nova descrição", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarUsouCartaoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.usouCartao("N");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "N", null,
                "N", null, null, "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarCartaoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.cartao("1234");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "1234",
                "S", "1", "7", "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarContaDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.conta("OUTRA");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "OUTRA", "DEBITO", "24,04", null,
                null);
    }

    @Test
    public void alterarTipoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.tipo("CREDITO");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "CREDITO", "24,04", null,
                null);
    }

    @Test
    public void alterarValorDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.valor("1000,00");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                "S", "1", "7", "CAROL", "DEBITO", "1.000,00", null,
                null);
    }

    @Test
    public void alterarParceladoDaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.parcelado("N");
        business.atualizar(dto);

        dto = business.buscarPorCodigo(codigo);
        assertEntityDTO(dto, "27/04/2018", null, "N", "Saúde",
                "Suplementos", "Site", "Suplementos comprados pela Carol", "S", "0744",
                "N", null, null, "CAROL", "DEBITO", "24,04", null,
                null);
    }

    @Test(expected = GenericException.class)
    public void alterarContabilidadesSubsequentesException() throws StoreException, BusinessException, GenericException {
        business.atualizarRecursivamente(null);
    }

    @Test
    public void alterarContabilidadeSubsequentesParceladas() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/04/2018", "Suplementos comprados pela Carol", "1", null);

        dto.descricao("Outra descrição");
        business.atualizarRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarParcelasRelacionadas(codigo);

        int parcelas = Integer.parseInt(dto.parcelas());
        String codigoPai = null;
        String dataVencimento = dtos.get(0).dataVencimento();
        for (int i = 0; i < parcelas; i++) {
            dto = dtos.get(i);
            String parcela = String.valueOf(i + 1);

            assertParcelado(dto, dataVencimento, "Outra descrição", parcela, codigoPai);

            dataVencimento = getStringNextMonth(dataVencimento);
            if (i == 0) {
                codigoPai = dtos.get(0).codigo();
            }
        }
    }

    @Test
    public void alterarContabilidadeSubsequentesRecorrentes() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeRecorrente();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertRecorrente(dto, "27/04/2018", "Aluguel mensal do apartamento", "1");

        dto.descricao("Outra descrição");
        business.atualizarRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarRecorrentesRelacionadas(codigo);

        assertRecorrentes(dtos, "27/04/2018", "Outra descrição");
    }

    @Test
    public void alterarContabilidadeSubsequentesParceladasParciais() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(1);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertParcelado(dto, "27/05/2018", "Suplementos comprados pela Carol", "2", "0");

        dto.descricao("Outra descrição");
        business.atualizarRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarParcelasRelacionadas(parseLong(dto.codigoPai()));

        int parcelas = Integer.parseInt(dto.parcelas());
        String codigoPai = null;
        String dataVencimento = dtos.get(0).dataVencimento();
        for (int i = 0; i < parcelas; i++) {
            dto = dtos.get(i);
            String parcela = String.valueOf(i + 1);

            if (i == 0) {
                assertParcelado(dto, dataVencimento, "Suplementos comprados pela Carol", parcela, codigoPai);
            }
            else {
                assertParcelado(dto, dataVencimento, "Outra descrição", parcela, codigoPai);
            }

            dataVencimento = getStringNextMonth(dataVencimento);
            if (i == 0) {
                codigoPai = dtos.get(0).codigo();
            }
        }
    }

    @Test
    public void alterarContabilidadeSubsequentesRecorrentesParciais() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeRecorrente();
        Long codigo = codigos.get(1);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertRecorrente(dto, "27/05/2018", "Aluguel mensal do apartamento", "2");

        dto.descricao("Outra descrição");
        business.atualizarRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarRecorrentesRelacionadas(codigo);

        assertRecorrentes(dtos, "27/05/2018", "Outra descrição");
    }

    @Test
    public void alterarContabilidadeSubsequentesNaoParcelado() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeNaoParcelada();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        assertNaoRecorrenteNaoParceladoNaoCartao(dto, "Aluguel mensal do apartamento");

        dto.descricao("Outra descrição");
        business.atualizarRecursivamente(dto);

        dto = business.buscarPorCodigo(codigo);

        assertNaoRecorrenteNaoParceladoNaoCartao(dto, "Outra descrição");
    }

    @Test(expected = GenericException.class)
    public void excluirUmaContabilidadeException() throws BusinessException, StoreException, GenericException {
        business.excluir(null);
    }

    @Test(expected = ValidationException.class)
    public void excluirValidationException() throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = new ContabilidadeDTO()
                .codigo("a");
        business.excluir(dto);
    }

    @Test(expected = StoreException.class)
    public void excluirStoreException() throws GenericException, BusinessException, IOException, StoreException {
        try {
            Long codigo = criarContabilidades().get(0);
            ContabilidadeDTO dto = business.buscarPorCodigo(codigo);
            deletarDiretorioEArquivos();
            business.excluir(dto);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao excluir."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void excluirUmaContabilidade() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        business.excluir(dto);

        try {
            business.buscarPorCodigo(codigo);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Registro inexistente."));
            throw e;
        }
    }

    @Test(expected = ValidationException.class)
    public void incrementarContabilidadesRecorrentesComMenosDe1Ano() throws StoreException, GenericException, BusinessException {
        try {
            ContabilidadeDTO dto = ContabilidadeDTOMockFactory.contabilidadeDTORecorrente();
            business.criar(dto);

            business.incrementarRecorrentes(0);
        }
        catch (ValidationException e) {
            assertThat(e.getMessage(), equalTo("O valor de anos deve ser maior ou igual a 1."));
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

    @Test(expected = GenericException.class)
    public void excluirRecursivamenteException() throws BusinessException, StoreException, GenericException {
        business.excluirRecursivamente(null);
    }

    @Test
    public void excluirRecursivamenteNaoParcelada() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeNaoParcelada();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        business.excluirRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarTodas();
        assertThat(dtos.size(), equalTo(0));
    }

    @Test
    public void excluirRecursivamenteParceladas() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        business.excluirRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarParcelasRelacionadas(codigo);
        assertThat(dtos.size(), equalTo(0));
    }

    @Test
    public void excluirRecursivamenteParceladasParcial() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        ContabilidadeDTO dto = business.buscarPorCodigo(codigos.get(1));

        business.excluirRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarParcelasRelacionadas(codigos.get(0));
        assertThat(dtos.size(), equalTo(1));

        assertParcelado(dtos.get(0), "27/04/2018", "Suplementos comprados pela Carol", "1", null);
    }

    @Test
    public void excluirRecursivamenteRecorrentes() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeRecorrente();
        Long codigo = codigos.get(0);
        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

        business.excluirRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarRecorrentesRelacionadas(codigo);
        assertThat(dtos.size(), equalTo(0));
    }

    @Test
    public void excluirRecursivamenteRecorrentesParcial() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidadeRecorrente();
        ContabilidadeDTO dto = business.buscarPorCodigo(codigos.get(1));

        business.excluirRecursivamente(dto);

        List<ContabilidadeDTO> dtos = business.buscarRecorrentesRelacionadas(codigos.get(0));
        assertThat(dtos.size(), equalTo(1));

        assertRecorrente(dtos.get(0), "27/04/2018", "Aluguel mensal do apartamento", null);
    }

    @Test(expected = BusinessException.class)
    public void realizarPagamentoException() throws StoreException, BusinessException, GenericException {
        try {
            business.realizarPagamento(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Registro inexistente."));
            throw e;
        }
    }

    @Test
    public void realizarPagamento() throws StoreException, BusinessException, GenericException {
        List<Long> codigos = criarContabilidades();
        Long codigo = codigos.get(0);

        ContabilidadeDTO dto = business.buscarPorCodigo(codigo);
        assertThat(dto.dataPagamento(), nullValue());

        business.realizarPagamento(codigo);
        dto = business.buscarPorCodigo(codigo);
        assertThat(dto.dataPagamento(), equalTo(getStringFromCurrentDate()));
    }

    @Test(expected = StoreException.class)
    public void buscarContabilidadePorIdException() throws IOException, StoreException, BusinessException, GenericException {
        deletarDiretorioEArquivos();
        business.buscarPorCodigo(null);
    }

    @Test(expected = StoreException.class)
    public void buscarContabilidadesException() throws IOException, StoreException, GenericException {
        deletarDiretorioEArquivos();
        business.buscarTodas();
    }

    @Test
    public void buscarContabilidades() throws StoreException, BusinessException, GenericException {
        criarContabilidades();
        criarContabilidadeRecorrente();

        List<ContabilidadeDTO> dtos = business.buscarTodas();
        dtos.sort(Comparator
                .comparing((ContabilidadeDTO c) -> parseLong(c.codigo())));

        assertThat(dtos.size(), equalTo(16));

        assertEntitiesParceladas(dtos.subList(0, 7));
        assertEntitiesRecorrentes(dtos.subList(7, 16));
    }

    @Test(expected = StoreException.class)
    public void buscarParcelasRelacionadasException() throws IOException, ValidationException, GenericException, StoreException {
        deletarDiretorioEArquivos();
        try {
            business.buscarParcelasRelacionadas(null);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao buscar parcelas relacionadas."));
            throw e;
        }
    }

    @Test(expected = StoreException.class)
    public void buscarRecorrentesRelacionadasException() throws IOException, ValidationException, GenericException, StoreException {
        deletarDiretorioEArquivos();
        try {
            business.buscarRecorrentesRelacionadas(null);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao buscar recorrentes relacionadas."));
            throw e;
        }
    }

    @Test
    public void buscarTodasAsParcelas() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContabilidades().get(0);

        List<ContabilidadeDTO> dtos = business.buscarParcelasRelacionadas(codigo);
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

    private void assertCodigos(List<Long> codigos) throws StoreException, GenericException, ValidationException {
        List<ContabilidadeDTO> dtos = business.buscarParcelasRelacionadas(codigos.get(0));
        assertEntitiesParceladas(dtos);
    }

    private void assertEntitiesParceladas(List<ContabilidadeDTO> dtos) {
        String dataVencimento = dtos.get(0).dataVencimento();
        String codigoPai = null;
        for (int i = 0; i < dtos.size(); i++) {
            ContabilidadeDTO dto = dtos.get(i);

            assertParcelado(dto, dataVencimento, "Suplementos comprados pela Carol", String.valueOf(i + 1), codigoPai);

            dataVencimento = getStringNextMonth(dataVencimento);
            if (i == 0) {
                codigoPai = dto.codigo();
            }
        }
    }

    private void assertEntitiesRecorrentes(List<ContabilidadeDTO> dtos) throws StoreException, BusinessException, GenericException {
        List<Long> codigos = new ArrayList<>();
        for (ContabilidadeDTO dto : dtos) {
            long codigo = parseLong(dto.codigo());
            codigos.add(codigo);
        }
        assertCodigosRecorrentes(codigos);
    }

    private void assertRecorrentes(List<ContabilidadeDTO> dtos, String dataVencimento, String descricao) {
        String dataVencimentoLocal = dataVencimento;
        int teto = dtos.size();
        for (int i = 0; i < teto; i++) {
            ContabilidadeDTO dto = dtos.get(i);
            Long codigo = parseLong(dto.codigo());

            if (i == (teto - 1)) {
                assertRecorrente(dto, dataVencimentoLocal, descricao, null);
            }
            else {
                assertRecorrente(dto, dataVencimentoLocal, descricao, String.valueOf(codigo + 1));
            }
            dataVencimentoLocal = getStringNextMonth(dataVencimentoLocal);
        }
    }

    private void assertCodigosRecorrentes(List<Long> codigos) throws StoreException, BusinessException, GenericException {
        String dataVencimento = "27/04/2018";
        int teto = 9;
        for (int i = 0; i < teto; i++) {
            Long codigo = codigos.get(i);
            ContabilidadeDTO dto = business.buscarPorCodigo(codigo);

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
                                         int passoProximoRegistro) throws StoreException, BusinessException, GenericException {
        String dataVencimentoLocal = dataVencimento;
        ContabilidadeDTO dto;
        for (int i = 0; i < totalDeRegistros; i++) {
            Long codigo = codigosAtualizados.get(i);
            dto = business.buscarPorCodigo(codigo);
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

    private void assertParcelado(ContabilidadeDTO dto, String dataVencimento, String descricao, String parcela, String codigoPai) {
        assertEntityDTO(dto, dataVencimento, null, "N", "Saúde",
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

    private void criarGrupoSaude() throws StoreException, BusinessException, GenericException {
        GrupoDTO grupoDTO = GrupoDTOFactory
                .create()
                .withNome("Saúde")
                .withDescricao("Grupo que gere gastos com saúde")
                .withSubGrupo("Suplementos")
                .withSubGrupo("Um subGrupo")
                .build();
        grupoBusiness.criar(grupoDTO);
    }

    private void criarGrupoApartamento() throws StoreException, BusinessException, GenericException {
        GrupoDTO grupoDTO = GrupoDTOFactory
                .create()
                .withNome("Apartamento")
                .withDescricao("...")
                .withSubGrupo("Aluguel")
                .build();
        grupoBusiness.criar(grupoDTO);
    }

    private void criarGrupoUm() throws StoreException, BusinessException, GenericException {
        GrupoDTO grupoDTO = GrupoDTOFactory
                .create()
                .withNome("Um grupo")
                .withDescricao("...")
                .withSubGrupo("Um subgrupo")
                .withSubGrupo("Suplementos")
                .build();
        grupoBusiness.criar(grupoDTO);
    }

    private void criarSubGrupoSuplementos() throws StoreException, BusinessException, GenericException {
        SubGrupoDTO subGrupoDTO = SubGrupoDTOFactory
                .create()
                .withNome("Suplementos")
                .withDescricao("Subgrupo de suplementos")
                .build();
        subGrupoBusiness.criar(subGrupoDTO);
    }

    private void criarSubGrupoAluguel() throws StoreException, BusinessException, GenericException {
        SubGrupoDTO subGrupoDTO = SubGrupoDTOFactory
                .create()
                .withNome("Aluguel")
                .withDescricao("...")
                .build();
        subGrupoBusiness.criar(subGrupoDTO);
    }

    private void criarSubGrupoUmSubGrupo() throws StoreException, BusinessException, GenericException {
        SubGrupoDTO subGrupoDTO = SubGrupoDTOFactory
                .create()
                .withNome("Um subGrupo")
                .withDescricao("...")
                .build();
        subGrupoBusiness.criar(subGrupoDTO);
    }

    private void criarLocalSite() throws StoreException, BusinessException, GenericException {
        LocalDTO localDTO = LocalDTOFactory
                .create()
                .withNome("Site")
                .build();
        localBusiness.criar(localDTO);
    }

    private void criarLocalOutroLocal() throws StoreException, BusinessException, GenericException {
        LocalDTO localDTO = LocalDTOFactory
                .create()
                .withNome("Outro local")
                .build();
        localBusiness.criar(localDTO);
    }

    private void criarCartao0744() throws StoreException, BusinessException, GenericException {
        CartaoDTO cartaoDTO = CartaoDTOFactory
                .create()
                .withNumero("0744")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Carol")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
        cartaoBusiness.criar(cartaoDTO);
    }

    private void criarCartao1234() throws StoreException, BusinessException, GenericException {
        CartaoDTO cartaoDTO = CartaoDTOFactory
                .create()
                .withNumero("1234")
                .withVencimento("27/03/2018")
                .withDiaMelhorCompra("17/04/2018")
                .withPortador("Carol")
                .withTipo("FISICO")
                .withLimite("2.000,00")
                .build();
        cartaoBusiness.criar(cartaoDTO);
    }

    private void criarContaCarol() throws StoreException, BusinessException, GenericException {
        ContaDTO contaDTO = ContaDTOFactory
                .create()
                .begin()
                .withNome("CAROL")
                .withDescricao("Valor separado para a Carol")
                .withValorDefault("500,00")
                .withCumulativo("S")
                .build();
        contaBusiness.criar(contaDTO);
    }

    private void criarContaMoradia() throws StoreException, BusinessException, GenericException {
        ContaDTO contaDTO = ContaDTOFactory
                .create()
                .begin()
                .withNome("MORADIA")
                .withDescricao("...")
                .withValorDefault("1.000,00")
                .withCumulativo("S")
                .build();
        contaBusiness.criar(contaDTO);
    }

    private void criarContaOutra() throws StoreException, BusinessException, GenericException {
        ContaDTO contaDTO = ContaDTOFactory
                .create()
                .begin()
                .withNome("OUTRA")
                .withDescricao("...")
                .withValorDefault("1.000,00")
                .withCumulativo("S")
                .build();
        contaBusiness.criar(contaDTO);
    }
}
