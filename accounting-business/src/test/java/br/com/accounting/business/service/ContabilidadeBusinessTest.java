package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.business.factory.ContabilidadeDTOMockFactory.*;
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
    public void alterarSemDataAtualizacao() throws BusinessException {
        try {
            List<Long> codigos = criarContabilidades();
            ContabilidadeDTO dtoBuscado = business.buscarPorId(codigos.get(0));
            dtoBuscado.dataAtualizacao(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "dataAtualização");
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
    public void alterarCodigo() throws BusinessException {
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

//    @Test
//    public void alterarDescricaoDaContabilidade() throws BusinessException {

//        List<Long> codigos = criarContabilidades();
//        String codigoAnterior = String.valueOf(codigos.get(0));
//        String codigoNovo = "10";
//
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigoAnterior));
//        assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
//            dtoBuscado.codigo(codigoNovo);
//            business.atualizar(dtoBuscado);
//
//        dtoBuscado = business.buscarPorId(Long.parseLong(codigoNovo));
//        assertThat(dtoBuscado.codigo(), equalTo(codigoNovo));


//        Long codigo = criarContabilidades();
//
//        ContabilidadeDTO dtoBuscado = assertContabilidadeEnok(codigo);
//
//        dtoBuscado.descricao("Valor separado para a Carol");
//        business.atualizar(dtoBuscado);
//
//        dtoBuscado = business.buscarPorId(codigo);
//        assertThat(dtoBuscado.nome(), equalTo("Enok"));
//        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para a Carol"));
//        assertThat(dtoBuscado.saldo(), equalTo("0.0"));
//        assertThat(dtoBuscado.cumulativo(), equalTo("S"));
//    }
//
//    @Test
//    public void alterarCumulativoDaContabilidade() throws BusinessException {
//        Long codigo = criarContabilidades();
//
//        ContabilidadeDTO dtoBuscado = assertContabilidadeEnok(codigo);
//
//        dtoBuscado.cumulativo("N");
//        business.atualizar(dtoBuscado);
//
//        dtoBuscado = business.buscarPorId(codigo);
//        assertThat(dtoBuscado.nome(), equalTo("Enok"));
//        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para o Enok"));
//        assertThat(dtoBuscado.saldo(), equalTo("0.0"));
//        assertThat(dtoBuscado.cumulativo(), equalTo("N"));
//    }
//
//    @Test
//    public void alterarNomeDescricaoCumulativo() throws BusinessException {
//        Long codigo = criarContabilidades();
//
//        ContabilidadeDTO dtoBuscado = assertContabilidadeEnok(codigo);
//
//        dtoBuscado.nome("Carol");
//        dtoBuscado.descricao("Valor separado para a Carol");
//        dtoBuscado.cumulativo("N");
//        business.atualizar(dtoBuscado);
//
//        dtoBuscado = business.buscarPorId(codigo);
//        assertThat(dtoBuscado.nome(), equalTo("Carol"));
//        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para a Carol"));
//        assertThat(dtoBuscado.saldo(), equalTo("0.0"));
//        assertThat(dtoBuscado.cumulativo(), equalTo("N"));
//    }
//
//    @Test(expected = BusinessException.class)
//    public void adicionarCreditoEmUmaContabilidadeSemDiretorio() throws BusinessException, IOException {
//        Long codigo = criarContabilidades();
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
//
//        deletarDiretorioEArquivos();
//
//        try {
//            business.adicionarCredito(dtoBuscado, "500,00");
//        }
//        catch (BusinessException e) {
//            assertThat(e.getMessage(), equalTo("Não foi possível adicionar crédito à contabilidade."));
//            throw e;
//        }
//    }
//
//    @Test
//    public void adicionarCreditoEmUmaContabilidade() throws BusinessException {
//        Long codigo = criarContabilidades();
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
//
//        business.adicionarCredito(dtoBuscado, "500,00");
//        dtoBuscado = business.buscarPorId(codigo);
//
//        assertThat(dtoBuscado.saldo(), equalTo("500.0"));
//    }
//
//    @Test
//    public void adicionarVariosCreditosEmUmaContabilidade() throws BusinessException {
//        Long codigo = criarContabilidades();
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
//
//        business.adicionarCredito(dtoBuscado, "500,00");
//        dtoBuscado = business.buscarPorId(codigo);
//
//        business.adicionarCredito(dtoBuscado, "500,00");
//        dtoBuscado = business.buscarPorId(codigo);
//
//        assertThat(dtoBuscado.saldo(), equalTo("1000.0"));
//    }
//
//    @Test(expected = BusinessException.class)
//    public void adicionarDebitoEmUmaContabilidadeSemDiretorio() throws BusinessException, IOException {
//        Long codigo = criarContabilidades();
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
//
//        deletarDiretorioEArquivos();
//
//        try {
//            business.adicionarDebito(dtoBuscado, "100,00");
//        }
//        catch (BusinessException e) {
//            assertThat(e.getMessage(), equalTo("Não foi possível adicionar débito à contabilidade."));
//            throw e;
//        }
//    }
//
//    @Test
//    public void adicionarDebitoEmUmaContabilidade() throws BusinessException {
//        Long codigo = criarContabilidades();
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
//
//        business.adicionarDebito(dtoBuscado, "100,00");
//        dtoBuscado = business.buscarPorId(codigo);
//
//        assertThat(dtoBuscado.saldo(), equalTo("-100.0"));
//    }
//
//    @Test
//    public void adicionarVariosDebitosEmUmaContabilidade() throws BusinessException {
//        Long codigo = criarContabilidades();
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
//
//        business.adicionarDebito(dtoBuscado, "100,00");
//        dtoBuscado = business.buscarPorId(codigo);
//
//        business.adicionarDebito(dtoBuscado, "100,00");
//        dtoBuscado = business.buscarPorId(codigo);
//
//        assertThat(dtoBuscado.saldo(), equalTo("-200.0"));
//    }
//
//    @Test(expected = BusinessException.class)
//    public void transferirSaldoDeUmaContabilidadeParaOutraException() throws BusinessException {
//        try {
//            business.transferir(null, null, null);
//        }
//        catch (BusinessException e) {
//            assertThat(e.getMessage(), equalTo("Não foi possível tranferir o valor entre as contabilidades."));
//            throw e;
//        }
//    }
//
//    @Test(expected = BusinessException.class)
//    public void transferirSaldoDeUmaContabilidadeParaOutraSaldoInsuficiente() throws BusinessException {
//        ContabilidadeDTO contabilidadeSalario = dto();
//        Long contabilidadeSalarioId = business.criar(contabilidadeSalario);
//        business.adicionarCredito(contabilidadeSalario, "500.0");
//        contabilidadeSalario = business.buscarPorId(contabilidadeSalarioId);
//
//        ContabilidadeDTO contabilidadeEnok = contabilidade2DTO();
//        Long contabilidadeEnokId = business.criar(contabilidadeEnok);
//        contabilidadeEnok = business.buscarPorId(contabilidadeEnokId);
//
//        try {
//            business.transferir(contabilidadeSalario, contabilidadeEnok, "600.0");
//        }
//        catch (BusinessException e) {
//            assertThat(e.getCause().getMessage(), equalTo("Saldo insuficiente."));
//            throw e;
//        }
//    }
//
//    @Test
//    public void transferirSaldoDeUmaContabilidadeParaOutra() throws BusinessException {
//        ContabilidadeDTO dto = dto();
//        Long contabilidadeSalarioId = business.criar(dto);
//        dto = business.buscarPorId(contabilidadeSalarioId);
//        business.adicionarCredito(dto, "1000.0");
//        dto = business.buscarPorId(contabilidadeSalarioId);
//
//        ContabilidadeDTO contabilidadeEnok = contabilidade2DTO();
//        Long contabilidadeEnokId = business.criar(contabilidadeEnok);
//        contabilidadeEnok = business.buscarPorId(contabilidadeEnokId);
//
//        business.transferir(dto, contabilidadeEnok, "600.0");
//
//        dto = business.buscarPorId(contabilidadeSalarioId);
//        assertThat(dto.saldo(), equalTo("400.0"));
//
//        contabilidadeEnok = business.buscarPorId(contabilidadeEnokId);
//        assertThat(contabilidadeEnok.saldo(), equalTo("600.0"));
//    }
//
//    @Test(expected = BusinessException.class)
//    public void excluirUmaContabilidadeException() throws BusinessException {
//        try {
//            business.excluir(null);
//        }
//        catch (BusinessException e) {
//            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
//            throw e;
//        }
//    }
//
//    @Test
//    public void excluirUmaContabilidade() throws BusinessException {
//        Long codigo = criarContabilidades();
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
//
//        business.excluir(dtoBuscado);
//
//        dtoBuscado = business.buscarPorId(codigo);
//        assertThat(dtoBuscado, nullValue());
//    }
//
//    @Test(expected = BusinessException.class)
//    public void buscarContabilidadePorIdException() throws IOException, BusinessException {
//        deletarDiretorioEArquivos();
//        try {
//            business.buscarPorId(null);
//        }
//        catch (BusinessException e) {
//            assertThat(e.getMessage(), equalTo("Não foi possível buscar por id."));
//            throw e;
//        }
//    }
//
//    @Test(expected = BusinessException.class)
//    public void buscarContabilidadesException() throws IOException, BusinessException {
//        deletarDiretorioEArquivos();
//        try {
//            business.buscarTodas();
//        }
//        catch (BusinessException e) {
//            assertThat(e.getMessage(), equalTo("Não foi possível buscar todas."));
//            throw e;
//        }
//    }
//
//    @Test
//    public void buscarContabilidades() throws BusinessException {
//        criarContabilidades();
//        criarContabilidades();
//
//        List<ContabilidadeDTO> cartoesDTO = business.buscarTodas();
//        assertThat(cartoesDTO.size(), equalTo(2));
//
//        assertContabilidadeEnok(cartoesDTO.get(0));
//        assertEntitiesDTOS(cartoesDTO.get(1));
//    }

//    private Long criarContabilidades() throws BusinessException {
//        ContabilidadeDTO contabilidade2DTO = contabilidade2DTO();
//        Long codigoContabilidade2 = business.criar(contabilidade2DTO);
//        assertTrue(codigoContabilidade2 >= 0);
//        return codigoContabilidade2;
//    }

//
//    private ContabilidadeDTO assertContabilidadeEnok(Long codigo) throws BusinessException {
//        ContabilidadeDTO dtoBuscado = business.buscarPorId(codigo);
//        return assertContabilidadeEnok(dtoBuscado);
//    }
//
//    private ContabilidadeDTO assertContabilidadeEnok(ContabilidadeDTO dto) {
//        assertThat(dto.nome(), equalTo("Enok"));
//        assertThat(dto.descricao(), equalTo("Valor separado para o Enok"));
//        assertThat(dto.saldo(), equalTo("0.0"));
//        assertThat(dto.cumulativo(), equalTo("S"));
//        return dto;
//    }

    private List<Long> criarContabilidades() throws BusinessException {
        ContabilidadeDTO dto = contabilidadeDTO();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(7));
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

    private void assertEntityDTO(ContabilidadeDTO dto) {
        assertEntityDTO(dto, null, "N", null, null);
    }

    private void assertEntityDTO(ContabilidadeDTO dto, String codigoPai, String parcelado, String parcela, String parcelas) {
        assertThat(dto.codigo(), not(nullValue()));
        assertThat(dto.dataLancamento(), not(nullValue()));
        assertThat(dto.dataAtualizacao(), not(nullValue()));
        assertThat(dto.dataVencimento(), equalTo("27/04/2018"));
        assertThat(dto.dataPagamento(), equalTo("27/04/2018"));
        assertThat(dto.recorrente(), equalTo("N"));
        assertThat(dto.grupo(), equalTo("Saúde"));
        assertThat(dto.subGrupo(), equalTo("Suplementos"));
        assertThat(dto.descricao(), equalTo("Suplementos comprados pela Carol"));
        assertThat(dto.usouCartao(), equalTo("S"));
        assertThat(dto.cartao(), equalTo("0744"));
        assertThat(dto.conta(), equalTo("CAROL"));
        assertThat(dto.tipo(), equalTo("DEBITO"));
        assertThat(dto.valor(), equalTo("24,04"));
        assertThat(dto.parcelado(), equalTo(parcelado));
        assertThat(dto.parcela(), equalTo(parcela));
        assertThat(dto.parcelas(), equalTo(parcelas));
        assertThat(dto.codigoPai(), equalTo(codigoPai));
    }

}
