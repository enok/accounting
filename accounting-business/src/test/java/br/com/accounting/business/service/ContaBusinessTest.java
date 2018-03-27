package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
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
    private ContaBusiness contaBusiness;

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemDiretorio() throws BusinessException, IOException {
        try {
            deletarDiretorioEArquivos();
            ContaDTO contaDTO = contaDTO();
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar a conta."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemNome() throws BusinessException {
        try {
            ContaDTO contaDTO = contaDTOSemNome();
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar a conta."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemDescricao() throws BusinessException {
        try {
            ContaDTO contaDTO = contaDTOSemDescricao();
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar a conta."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo descrição é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaContaSemNomeEDescricao() throws BusinessException {
        try {
            ContaDTO contaDTO = contaDTOSemNomeEDescricao();
            contaBusiness.criar(contaDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar a conta."));

            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(2));
            assertThat(erros.get(0), equalTo("O campo nome é obrigatório."));
            assertThat(erros.get(1), equalTo("O campo descrição é obrigatório."));
            throw e;
        }
    }

    @Test
    public void criarUmaConta() throws BusinessException {
        Long codigoConta = criarContaSalario();
        assertContaSalario(codigoConta);
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
        Long codigoConta = criarContaSalario();
        Long codigoConta2 = criarContaEnok();

        assertThat(codigoConta, not(equalTo(codigoConta2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws BusinessException {
        Long codigoConta = criarContaEnok();

        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        contaDTOBuscada.codigo(null);

        contaBusiness.atualizar(contaDTOBuscada);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemNome() throws BusinessException {
        Long codigoConta = criarContaEnok();

        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        contaDTOBuscada.nome(null);

        contaBusiness.atualizar(contaDTOBuscada);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDescricao() throws BusinessException {
        Long codigoConta = criarContaEnok();

        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        contaDTOBuscada.descricao(null);

        contaBusiness.atualizar(contaDTOBuscada);
    }

    @Test
    public void alterarNomeDaConta() throws BusinessException {
        Long codigoConta = criarContaEnok();

        ContaDTO contaDTOBuscada = assertContaEnok(codigoConta);

        contaDTOBuscada.nome("Carol");
        contaBusiness.atualizar(contaDTOBuscada);

        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        assertThat(contaDTOBuscada.nome(), equalTo("Carol"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));
    }

    @Test
    public void alterarDescricaoDaConta() throws BusinessException {
        Long codigoConta = criarContaEnok();

        ContaDTO contaDTOBuscada = assertContaEnok(codigoConta);

        contaDTOBuscada.descricao("Valor separado para a Carol");
        contaBusiness.atualizar(contaDTOBuscada);

        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        assertThat(contaDTOBuscada.nome(), equalTo("Enok"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para a Carol"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));
    }

    @Test
    public void alterarNomeEDescricao() throws BusinessException {
        Long codigoConta = criarContaEnok();

        ContaDTO contaDTOBuscada = assertContaEnok(codigoConta);

        contaDTOBuscada.nome("Carol");
        contaDTOBuscada.descricao("Valor separado para a Carol");
        contaBusiness.atualizar(contaDTOBuscada);

        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        assertThat(contaDTOBuscada.nome(), equalTo("Carol"));
        assertThat(contaDTOBuscada.descricao(), equalTo("Valor separado para a Carol"));
        assertThat(contaDTOBuscada.saldo(), equalTo("0.0"));
    }

    @Test(expected = BusinessException.class)
    public void adicionarCreditoEmUmaContaSemDiretorio() throws BusinessException, IOException {
        Long codigoConta = criarContaSalario();
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        deletarDiretorioEArquivos();

        try {
            contaBusiness.adicionarCredito(contaDTOBuscada, "500,00");
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível adicionar crédito à conta."));
            throw e;
        }
    }

    @Test
    public void adicionarCreditoEmUmaConta() throws BusinessException {
        Long codigoConta = criarContaSalario();
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        contaBusiness.adicionarCredito(contaDTOBuscada, "500,00");
        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        assertThat(contaDTOBuscada.saldo(), equalTo("500.0"));
    }

    @Test
    public void adicionarVariosCreditosEmUmaConta() throws BusinessException {
        Long codigoConta = criarContaSalario();
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        contaBusiness.adicionarCredito(contaDTOBuscada, "500,00");
        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        contaBusiness.adicionarCredito(contaDTOBuscada, "500,00");
        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        assertThat(contaDTOBuscada.saldo(), equalTo("1000.0"));
    }

    @Test(expected = BusinessException.class)
    public void adicionarDebitoEmUmaContaSemDiretorio() throws BusinessException, IOException {
        Long codigoConta = criarContaSalario();
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        deletarDiretorioEArquivos();

        try {
            contaBusiness.adicionarDebito(contaDTOBuscada, "100,00");
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível adicionar débito à conta."));
            throw e;
        }
    }

    @Test
    public void adicionarDebitoEmUmaConta() throws BusinessException {
        Long codigoConta = criarContaSalario();
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        contaBusiness.adicionarDebito(contaDTOBuscada, "100,00");
        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        assertThat(contaDTOBuscada.saldo(), equalTo("-100.0"));
    }

    @Test
    public void adicionarVariosDebitosEmUmaConta() throws BusinessException {
        Long codigoConta = criarContaSalario();
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        contaBusiness.adicionarDebito(contaDTOBuscada, "100,00");
        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        contaBusiness.adicionarDebito(contaDTOBuscada, "100,00");
        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        assertThat(contaDTOBuscada.saldo(), equalTo("-200.0"));
    }

    @Test(expected = BusinessException.class)
    public void transferirSaldoDeUmaContaParaOutraException() throws BusinessException {
        try {
            contaBusiness.transferir(null, null, null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível tranferir o valor entre as contas."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void transferirSaldoDeUmaContaParaOutraSaldoInsuficiente() throws BusinessException {
        ContaDTO contaSalario = contaDTO();
        Long contaSalarioId = contaBusiness.criar(contaSalario);
        contaBusiness.adicionarCredito(contaSalario, "500.0");
        contaSalario = contaBusiness.buscarPorId(contaSalarioId);

        ContaDTO contaEnok = conta2DTO();
        Long contaEnokId = contaBusiness.criar(contaEnok);
        contaEnok = contaBusiness.buscarPorId(contaEnokId);

        try {
            contaBusiness.transferir(contaSalario, contaEnok, "600.0");
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), equalTo("Saldo insuficiente."));
            throw e;
        }
    }

    @Test
    public void transferirSaldoDeUmaContaParaOutra() throws BusinessException {
        ContaDTO contaSalario = contaDTO();
        Long contaSalarioId = contaBusiness.criar(contaSalario);
        contaBusiness.adicionarCredito(contaSalario, "1000.0");
        contaSalario = contaBusiness.buscarPorId(contaSalarioId);

        ContaDTO contaEnok = conta2DTO();
        Long contaEnokId = contaBusiness.criar(contaEnok);
        contaEnok = contaBusiness.buscarPorId(contaEnokId);

        contaBusiness.transferir(contaSalario, contaEnok, "600.0");

        contaSalario = contaBusiness.buscarPorId(contaSalarioId);
        assertThat(contaSalario.saldo(), equalTo("400.0"));

        contaEnok = contaBusiness.buscarPorId(contaEnokId);
        assertThat(contaEnok.saldo(), equalTo("600.0"));
    }

    @Test(expected = BusinessException.class)
    public void excluirUmaContaException() throws BusinessException {
        try {
            contaBusiness.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir a conta."));
            throw e;
        }
    }

    @Test
    public void excluirUmaConta() throws BusinessException {
        Long codigoConta = criarContaSalario();
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);

        contaBusiness.excluir(contaDTOBuscada);

        contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        assertThat(contaDTOBuscada, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void buscarContaPorIdException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            contaBusiness.buscarPorId(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar a conta por id."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void buscarContasException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            contaBusiness.buscarTodas();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar as contas."));
            throw e;
        }
    }

    @Test
    public void buscarContas() throws BusinessException {
        criarContaSalario();
        criarContaEnok();

        List<ContaDTO> cartoesDTO = contaBusiness.buscarTodas();
        assertThat(cartoesDTO.size(), equalTo(2));

        assertContaEnok(cartoesDTO.get(0));
        assertContaSalario(cartoesDTO.get(1));
    }

    private Long criarContaSalario() throws BusinessException {
        ContaDTO contaDTO = contaDTO();
        Long codigoConta = contaBusiness.criar(contaDTO);
        assertTrue(codigoConta >= 0);
        return codigoConta;
    }

    private Long criarContaEnok() throws BusinessException {
        ContaDTO conta2DTO = conta2DTO();
        Long codigoConta2 = contaBusiness.criar(conta2DTO);
        assertTrue(codigoConta2 >= 0);
        return codigoConta2;
    }

    private ContaDTO assertContaSalario(Long codigoConta) throws BusinessException {
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        return assertContaSalario(contaDTOBuscada);
    }

    private ContaDTO assertContaSalario(ContaDTO contaDTO) {
        assertThat(contaDTO.nome(), equalTo("Salário"));
        assertThat(contaDTO.descricao(), equalTo("Salário mensal recebido pela Sysmap"));
        assertThat(contaDTO.saldo(), equalTo("0.0"));
        return contaDTO;
    }

    private ContaDTO assertContaEnok(Long codigoConta) throws BusinessException {
        ContaDTO contaDTOBuscada = contaBusiness.buscarPorId(codigoConta);
        return assertContaEnok(contaDTOBuscada);
    }

    private ContaDTO assertContaEnok(ContaDTO contaDTO) {
        assertThat(contaDTO.nome(), equalTo("Enok"));
        assertThat(contaDTO.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(contaDTO.saldo(), equalTo("0.0"));
        return contaDTO;
    }
}
