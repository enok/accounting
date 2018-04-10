package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.business.factory.ContaDTOMockFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class ContaBusinessTest extends GenericTest {
    @Autowired
    private ContaBusiness business;

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemDiretorio() throws BusinessException, IOException {
        try {
            deletarDiretorioEArquivos();
            ContaDTO dto = contaDTO();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreation(e);
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemNome() throws BusinessException {
        try {
            ContaDTO dto = contaDTOSemNome();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "nome");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemDescricao() throws BusinessException {
        try {
            ContaDTO dto = contaDTOSemDescricao();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "descrição");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemCumulativo() throws BusinessException {
        try {
            ContaDTO dto = contaDTOSemCumulativo();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "cumulativo");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemCamposObrigatorios() throws BusinessException {
        try {
            ContaDTO dto = contaDTOSemCamposObrigatorios();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "nome", "descrição", "cumulativo");
        }
    }

    @Test
    public void criarUmaConta() throws BusinessException {
        Long codigo = criarContaSalario();
        assertContaSalario(codigo);
    }

    @Test(expected = BusinessException.class)
    public void criarDuasContasComNomesIguais() throws BusinessException {
        try {
            criarContaSalario();
            criarContaSalario();
        }
        catch (BusinessException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Conta duplicada."));
            throw e;
        }
    }

    @Test
    public void criarDuasContas() throws BusinessException {
        Long codigo = criarContaSalario();
        Long codigo2 = criarContaEnok();

        assertThat(codigo, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws BusinessException {
        try {
            Long codigo = criarContaEnok();
            ContaDTO dtoBuscado = business.buscarPorId(codigo);
            dtoBuscado.codigo(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "código");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemNome() throws BusinessException {
        try {
            Long codigo = criarContaEnok();
            ContaDTO dtoBuscado = business.buscarPorId(codigo);
            dtoBuscado.nome(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "nome");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDescricao() throws BusinessException {
        try {
            Long codigo = criarContaEnok();
            ContaDTO dtoBuscado = business.buscarPorId(codigo);
            dtoBuscado.descricao(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "descrição");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCumulativo() throws BusinessException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = business.buscarPorId(codigo);
        dtoBuscado.cumulativo(null);

        business.atualizar(dtoBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDoCodigo() throws BusinessException {
        try {
            Long codigo = criarContaEnok();
            String codigoAnterior = String.valueOf(codigo);
            String codigoNovo = "10";
            ContaDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigoAnterior));
            assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
            dtoBuscado.codigo(codigoNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "código");
        }
    }

    @Test
    public void alterarNomeDaConta() throws BusinessException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.nome("Carol");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Carol"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(dtoBuscado.saldo(), equalTo("0.0"));
        assertThat(dtoBuscado.cumulativo(), equalTo("S"));
    }

    @Test
    public void alterarDescricaoDaConta() throws BusinessException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.descricao("Valor separado para a Carol");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Enok"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para a Carol"));
        assertThat(dtoBuscado.saldo(), equalTo("0.0"));
        assertThat(dtoBuscado.cumulativo(), equalTo("S"));
    }

    @Test
    public void alterarCumulativoDaConta() throws BusinessException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.cumulativo("N");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Enok"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(dtoBuscado.saldo(), equalTo("0.0"));
        assertThat(dtoBuscado.cumulativo(), equalTo("N"));
    }

    @Test
    public void alterarNomeDescricaoCumulativo() throws BusinessException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.nome("Carol");
        dtoBuscado.descricao("Valor separado para a Carol");
        dtoBuscado.cumulativo("N");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Carol"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para a Carol"));
        assertThat(dtoBuscado.saldo(), equalTo("0.0"));
        assertThat(dtoBuscado.cumulativo(), equalTo("N"));
    }

    @Test(expected = BusinessException.class)
    public void adicionarCreditoEmUmaContaSemDiretorio() throws BusinessException, IOException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorId(codigo);

        deletarDiretorioEArquivos();

        try {
            business.adicionarCredito(dtoBuscado, "500,00");
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível adicionar crédito à conta."));
            throw e;
        }
    }

    @Test
    public void adicionarCreditoEmUmaConta() throws BusinessException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorId(codigo);

        business.adicionarCredito(dtoBuscado, "500,00");
        dtoBuscado = business.buscarPorId(codigo);

        assertThat(dtoBuscado.saldo(), equalTo("500.0"));
    }

    @Test
    public void adicionarVariosCreditosEmUmaConta() throws BusinessException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorId(codigo);

        business.adicionarCredito(dtoBuscado, "500,00");
        dtoBuscado = business.buscarPorId(codigo);

        business.adicionarCredito(dtoBuscado, "500,00");
        dtoBuscado = business.buscarPorId(codigo);

        assertThat(dtoBuscado.saldo(), equalTo("1000.0"));
    }

    @Test(expected = BusinessException.class)
    public void adicionarDebitoEmUmaContaSemDiretorio() throws BusinessException, IOException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorId(codigo);

        deletarDiretorioEArquivos();

        try {
            business.adicionarDebito(dtoBuscado, "100,00");
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível adicionar débito à conta."));
            throw e;
        }
    }

    @Test
    public void adicionarDebitoEmUmaConta() throws BusinessException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorId(codigo);

        business.adicionarDebito(dtoBuscado, "100,00");
        dtoBuscado = business.buscarPorId(codigo);

        assertThat(dtoBuscado.saldo(), equalTo("-100.0"));
    }

    @Test
    public void adicionarVariosDebitosEmUmaConta() throws BusinessException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorId(codigo);

        business.adicionarDebito(dtoBuscado, "100,00");
        dtoBuscado = business.buscarPorId(codigo);

        business.adicionarDebito(dtoBuscado, "100,00");
        dtoBuscado = business.buscarPorId(codigo);

        assertThat(dtoBuscado.saldo(), equalTo("-200.0"));
    }

    @Test(expected = BusinessException.class)
    public void transferirSaldoDeUmaContaParaOutraException() throws BusinessException {
        try {
            business.transferir(null, null, null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível tranferir o valor entre as contas."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void transferirSaldoDeUmaContaParaOutraSaldoInsuficiente() throws BusinessException {
        ContaDTO dto1 = contaDTO();
        Long codigo1 = business.criar(dto1).get(0);
        business.adicionarCredito(dto1, "500.0");
        dto1 = business.buscarPorId(codigo1);

        ContaDTO dto2 = conta2DTO();
        Long codigo2 = business.criar(dto2).get(0);
        dto2 = business.buscarPorId(codigo2);

        try {
            business.transferir(dto1, dto2, "600.0");
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), equalTo("Saldo insuficiente."));
            throw e;
        }
    }

    @Test
    public void transferirSaldoDeUmaContaParaOutra() throws BusinessException {
        ContaDTO dto1 = contaDTO();
        Long codigo1 = business.criar(dto1).get(0);
        dto1 = business.buscarPorId(codigo1);
        business.adicionarCredito(dto1, "1000.0");
        dto1 = business.buscarPorId(codigo1);

        ContaDTO dto2 = conta2DTO();
        Long codigo2 = business.criar(dto2).get(0);
        dto2 = business.buscarPorId(codigo2);

        business.transferir(dto1, dto2, "600.0");

        dto1 = business.buscarPorId(codigo1);
        assertThat(dto1.saldo(), equalTo("400.0"));

        dto2 = business.buscarPorId(codigo2);
        assertThat(dto2.saldo(), equalTo("600.0"));
    }

    @Test(expected = BusinessException.class)
    public void excluirUmaContaException() throws BusinessException {
        try {
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
            throw e;
        }
    }

    @Test
    public void excluirUmaConta() throws BusinessException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorId(codigo);

        business.excluir(dtoBuscado);

        dtoBuscado = business.buscarPorId(codigo);
        assertThat(dtoBuscado, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void buscarContaPorIdException() throws IOException, BusinessException {
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
    public void buscarContasException() throws IOException, BusinessException {
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
    public void buscarContas() throws BusinessException {
        criarContaSalario();
        criarContaEnok();

        List<ContaDTO> dtos = business.buscarTodas();
        assertThat(dtos.size(), equalTo(2));

        assertContaEnok(dtos.get(0));
        assertContaSalario(dtos.get(1));
    }

    private Long criarContaSalario() throws BusinessException {
        ContaDTO dto = contaDTO();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarContaEnok() throws BusinessException {
        ContaDTO dto = conta2DTO();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertContaSalario(Long codigo) throws BusinessException {
        ContaDTO dtoBuscado = business.buscarPorId(codigo);
        assertContaSalario(dtoBuscado);
    }

    private void assertContaSalario(ContaDTO dto) {
        assertThat(dto.nome(), equalTo("Salário"));
        assertThat(dto.descricao(), equalTo("Salário mensal recebido pela Sysmap"));
        assertThat(dto.saldo(), equalTo("0.0"));
        assertThat(dto.cumulativo(), equalTo("N"));
    }

    private ContaDTO assertContaEnok(Long codigo) throws BusinessException {
        ContaDTO dtoBuscado = business.buscarPorId(codigo);
        return assertContaEnok(dtoBuscado);
    }

    private ContaDTO assertContaEnok(ContaDTO dto) {
        assertThat(dto.nome(), equalTo("Enok"));
        assertThat(dto.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(dto.saldo(), equalTo("0.0"));
        assertThat(dto.cumulativo(), equalTo("S"));
        return dto;
    }
}
