package br.com.accounting.conta.business;

import br.com.accounting.commons.exception.*;
import br.com.accounting.commons.test.GenericTest;
import br.com.accounting.conta.dto.ContaDTO;
import br.com.accounting.conta.factory.ContaDTOMockFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.commons.util.Utils.getStringFromCurrentDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class ContaBusinessTest extends GenericTest {
    @Autowired
    private ContaBusiness business;

    @Test(expected = StoreException.class)
    public void criarUmaContaSemDiretorio() throws StoreException, BusinessException, GenericException, IOException {
        try {
            deletarDiretorioEArquivos();
            ContaDTO dto = ContaDTOMockFactory.contaSalario();
            business.criar(dto);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao salvar."));
            throw e;
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContaSemNome() throws StoreException, BusinessException, GenericException {
        try {
            ContaDTO dto = ContaDTOMockFactory.contaDTOSemNome();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "nome");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContaSemDescricao() throws StoreException, BusinessException, GenericException {
        try {
            ContaDTO dto = ContaDTOMockFactory.contaDTOSemDescricao();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "descrição");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContaSemValorDefault() throws StoreException, BusinessException, GenericException {
        try {
            ContaDTO dto = ContaDTOMockFactory.contaDTOSemValorDefault();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "valorDefault");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContaSemCumulativo() throws StoreException, BusinessException, GenericException {
        try {
            ContaDTO dto = ContaDTOMockFactory.contaDTOSemCumulativo();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "cumulativo");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaContaSemCamposObrigatorios() throws StoreException, BusinessException, GenericException {
        try {
            ContaDTO dto = ContaDTOMockFactory.contaDTOSemCamposObrigatorios();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "nome", "descrição", "valorDefault", "cumulativo");
        }
    }

    @Test
    public void criarUmaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaSalario();
        assertContaSalario(codigo);
    }

    @Test(expected = ValidationException.class)
    public void criarDuasContasComNomesIguais() throws StoreException, BusinessException, GenericException {
        try {
            criarContaSalario();
            criarContaSalario();
        }
        catch (ValidationException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e;
            assertThat(e1.getMessage(), equalTo("Conta duplicada."));
            throw e;
        }
    }

    @Test
    public void criarDuasContas() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaSalario();
        Long codigo2 = criarContaEnok();

        assertThat(codigo, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarContaEnok();
            ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);
            dtoBuscado.codigo(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "código");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemNome() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarContaEnok();
            ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);
            dtoBuscado.nome(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "nome");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDescricao() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarContaEnok();
            ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);
            dtoBuscado.descricao(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "descrição");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemValorDefault() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarContaEnok();
            ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);
            dtoBuscado.valorDefault(null);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "valorDefault");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCumulativo() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);
        dtoBuscado.cumulativo(null);

        business.atualizar(dtoBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDoCodigo() throws StoreException, BusinessException, GenericException {
        try {
            Long codigo = criarContaEnok();
            String codigoAnterior = String.valueOf(codigo);
            String codigoNovo = "10";
            ContaDTO dtoBuscado = business.buscarPorCodigo(Long.parseLong(codigoAnterior));
            assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
            dtoBuscado.codigo(codigoNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Registro inexistente."));
            throw e;
        }
    }

    @Test
    public void alterarNomeDaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.nome("Carol");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorCodigo(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Carol"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(dtoBuscado.valorDefault(), equalTo("500,00"));
        assertThat(dtoBuscado.saldo(), equalTo("500,00"));
        assertThat(dtoBuscado.cumulativo(), equalTo("S"));
    }

    @Test
    public void alterarDescricaoDaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.descricao("Valor separado para a Carol");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorCodigo(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Enok"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para a Carol"));
        assertThat(dtoBuscado.valorDefault(), equalTo("500,00"));
        assertThat(dtoBuscado.saldo(), equalTo("500,00"));
        assertThat(dtoBuscado.cumulativo(), equalTo("S"));
    }

    @Test
    public void alterarValorDefaultDaContaParaMais() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.valorDefault("600,00");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorCodigo(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Enok"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(dtoBuscado.valorDefault(), equalTo("600,00"));
        assertThat(dtoBuscado.saldo(), equalTo("600,00"));
        assertThat(dtoBuscado.cumulativo(), equalTo("S"));
    }

    @Test
    public void alterarCumulativoDaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.cumulativo("N");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorCodigo(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Enok"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(dtoBuscado.valorDefault(), equalTo("500,00"));
        assertThat(dtoBuscado.saldo(), equalTo("500,00"));
        assertThat(dtoBuscado.cumulativo(), equalTo("N"));
    }

    @Test
    public void alterarNomeDescricaoValorDefaultCumulativo() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaEnok();

        ContaDTO dtoBuscado = assertContaEnok(codigo);

        dtoBuscado.nome("Carol");
        dtoBuscado.descricao("Valor separado para a Carol");
        dtoBuscado.cumulativo("N");
        dtoBuscado.valorDefault("150,00");
        business.atualizar(dtoBuscado);

        dtoBuscado = business.buscarPorCodigo(codigo);
        assertThat(dtoBuscado.nome(), equalTo("Carol"));
        assertThat(dtoBuscado.descricao(), equalTo("Valor separado para a Carol"));
        assertThat(dtoBuscado.valorDefault(), equalTo("150,00"));
        assertThat(dtoBuscado.saldo(), equalTo("150,00"));
        assertThat(dtoBuscado.cumulativo(), equalTo("N"));
    }

    @Test(expected = StoreException.class)
    public void adicionarCreditoEmUmaContaSemDiretorio() throws StoreException, BusinessException, GenericException, IOException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);

        deletarDiretorioEArquivos();

        try {
            business.adicionarCredito(dtoBuscado, 500.00);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao adicionar crédito."));
            throw e;
        }
    }

    @Test
    public void adicionarCreditoEmUmaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);

        business.adicionarCredito(dtoBuscado, 500.00);
        dtoBuscado = business.buscarPorCodigo(codigo);

        assertThat(dtoBuscado.saldo(), equalTo("1.500,00"));
    }

    @Test
    public void adicionarVariosCreditosEmUmaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);

        business.adicionarCredito(dtoBuscado, 500.00);
        dtoBuscado = business.buscarPorCodigo(codigo);

        business.adicionarCredito(dtoBuscado, 500.00);
        dtoBuscado = business.buscarPorCodigo(codigo);

        assertThat(dtoBuscado.saldo(), equalTo("2.000,00"));
    }

    @Test(expected = StoreException.class)
    public void adicionarDebitoEmUmaContaSemDiretorio() throws StoreException, BusinessException, GenericException, IOException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);

        deletarDiretorioEArquivos();

        try {
            business.adicionarDebito(dtoBuscado, 100.00);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao adicionar débito."));
            throw e;
        }
    }

    @Test
    public void adicionarDebitoEmUmaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);

        business.adicionarDebito(dtoBuscado, 100.00);
        dtoBuscado = business.buscarPorCodigo(codigo);

        assertThat(dtoBuscado.saldo(), equalTo("900,00"));
    }

    @Test
    public void adicionarVariosDebitosEmUmaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);

        business.adicionarDebito(dtoBuscado, 100.00);
        dtoBuscado = business.buscarPorCodigo(codigo);

        business.adicionarDebito(dtoBuscado, 900.00);
        dtoBuscado = business.buscarPorCodigo(codigo);

        assertThat(dtoBuscado.saldo(), equalTo("0,00"));
    }

    @Test(expected = GenericException.class)
    public void transferirSaldoDeUmaContaParaOutraException() throws BusinessException, StoreException, GenericException {
        business.transferir(null, null, null);
    }

    @Test(expected = BusinessException.class)
    public void transferirSaldoDeUmaContaParaOutraSaldoInsuficiente() throws StoreException, BusinessException, GenericException {
        ContaDTO dto1 = ContaDTOMockFactory.contaSalario();
        Long codigo1 = business.criar(dto1).get(0);
        dto1 = business.buscarPorCodigo(codigo1);

        ContaDTO dto2 = ContaDTOMockFactory.contaEnok();
        Long codigo2 = business.criar(dto2).get(0);
        dto2 = business.buscarPorCodigo(codigo2);

        try {
            business.transferir(dto1, dto2, 1500.00);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Saldo insuficiente."));
            throw e;
        }
    }

    @Test
    public void transferirSaldoDeUmaContaParaOutra() throws StoreException, BusinessException, GenericException {
        ContaDTO dto1 = ContaDTOMockFactory.contaSalario();
        Long codigo1 = business.criar(dto1).get(0);
        dto1 = business.buscarPorCodigo(codigo1);
        business.adicionarCredito(dto1, 1000.00);
        dto1 = business.buscarPorCodigo(codigo1);

        ContaDTO dto2 = ContaDTOMockFactory.contaEnok();
        Long codigo2 = business.criar(dto2).get(0);
        dto2 = business.buscarPorCodigo(codigo2);

        business.transferir(dto1, dto2, 600.00);

        dto1 = business.buscarPorCodigo(codigo1);
        assertThat(dto1.saldo(), equalTo("1.400,00"));

        dto2 = business.buscarPorCodigo(codigo2);
        assertThat(dto2.saldo(), equalTo("1.100,00"));
    }

    @Test(expected = GenericException.class)
    public void excluirUmaContaException() throws BusinessException, StoreException, GenericException {
        business.excluir(null);
    }

    @Test(expected = StoreException.class)
    public void excluirStoreException() throws IOException, BusinessException, GenericException, StoreException {
        try {
            Long codigo = criarContaSalario();
            ContaDTO dto = business.buscarPorCodigo(codigo);
            deletarDiretorioEArquivos();
            business.excluir(dto);
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao excluir."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void excluirUmaConta() throws StoreException, BusinessException, GenericException {
        Long codigo = criarContaSalario();
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);

        business.excluir(dtoBuscado);

        try {
            business.buscarPorCodigo(codigo);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Registro inexistente."));
            throw e;
        }
    }

    @Test(expected = StoreException.class)
    public void buscarContaPorIdException() throws IOException, StoreException, BusinessException, GenericException {
        deletarDiretorioEArquivos();
        business.buscarPorCodigo(null);
    }

    @Test(expected = StoreException.class)
    public void buscarContasException() throws IOException, StoreException, GenericException {
        deletarDiretorioEArquivos();
        business.buscarTodas();
    }

    @Test
    public void buscarContas() throws StoreException, BusinessException, GenericException {
        criarContaSalario();
        criarContaEnok();

        List<ContaDTO> dtos = business.buscarTodas();
        assertThat(dtos.size(), equalTo(2));

        assertContaEnok(dtos.get(0), "500,00", "10/03/2018");
        assertContaSalario(dtos.get(1));
    }

    @Test(expected = StoreException.class)
    public void atualizarCumulativasSemDiretorio() throws IOException, StoreException, GenericException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            business.atualizarCumulativas();
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Erro de persistência ao atualizar as contas cumulativas."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void atualizarCumulativasSemCumulativos() throws StoreException, BusinessException, GenericException {
        criarContaSalario();
        try {
            business.atualizarCumulativas();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Registro inexistente."));
            throw e;
        }
    }

    @Test
    public void atualizarCumulativas() throws StoreException, BusinessException, GenericException {
        Long codigoEnok = criarContaEnok();
        Long codigoSalario = criarContaSalario();
        business.atualizarCumulativas();
        ContaDTO dtoEnok = business.buscarPorCodigo(codigoEnok);
        ContaDTO dtoSalario = business.buscarPorCodigo(codigoSalario);

        assertContaEnok(dtoEnok, "1.000,00", getStringFromCurrentDate());
        assertContaSalario(dtoSalario);
    }

    private Long criarContaSalario() throws StoreException, BusinessException, GenericException {
        ContaDTO dto = ContaDTOMockFactory.contaSalario();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private Long criarContaEnok() throws StoreException, BusinessException, GenericException {
        ContaDTO dto = ContaDTOMockFactory.contaEnok();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void assertContaSalario(Long codigo) throws StoreException, BusinessException, GenericException {
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);
        assertContaSalario(dtoBuscado);
    }

    private void assertContaSalario(ContaDTO dto) {
        assertThat(dto.nome(), equalTo("Salário"));
        assertThat(dto.descricao(), equalTo("Salário mensal recebido pela Sysmap"));
        assertThat(dto.valorDefault(), equalTo("1.000,00"));
        assertThat(dto.saldo(), equalTo("1.000,00"));
        assertThat(dto.cumulativo(), equalTo("N"));
        assertThat(dto.dataAtualizacao(), equalTo("15/03/2018"));
    }

    private ContaDTO assertContaEnok(Long codigo) throws StoreException, BusinessException, GenericException {
        ContaDTO dtoBuscado = business.buscarPorCodigo(codigo);
        return assertContaEnok(dtoBuscado, "500,00", "10/03/2018");
    }

    private ContaDTO assertContaEnok(ContaDTO dto, String saldo, String dataAtualizacao) {
        assertThat(dto.nome(), equalTo("Enok"));
        assertThat(dto.descricao(), equalTo("Valor separado para o Enok"));
        assertThat(dto.valorDefault(), equalTo("500,00"));
        assertThat(dto.saldo(), equalTo(saldo));
        assertThat(dto.cumulativo(), equalTo("S"));
        assertThat(dto.dataAtualizacao(), equalTo(dataAtualizacao));
        return dto;
    }
}
