package br.com.accounting.business.service;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.business.factory.CartaoDTOMockFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class CartaoBusinessTest extends GenericTest {
    @Autowired
    private CartaoBusiness cartaoBusiness;

    @Test(expected = BusinessException.class)
    public void criarUmCartaoSemDiretorio() throws IOException, BusinessException {
        try {
            deletarDiretorioEArquivos();
            criarCartaoFisicoEnok();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível criar o cartão."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemNumero() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660SemNumero();

        try {
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo número é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemVencimento() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660SemVencimento();

        try {
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo vencimento é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemDiaMelhorCompra() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660SemDiaMelhorCompra();

        try {
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo diaMelhorCompra é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemPortador() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660SemPortador();

        try {
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo portador é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemTipo() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660SemTipo();

        try {
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo tipo é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoComTipoErrado() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660TipoErrado();

        try {
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            IllegalArgumentException e1 = (IllegalArgumentException) e.getCause();
            assertThat(e1.getMessage(), equalTo("No enum constant br.com.accounting.core.entity.Tipo.OUTRO"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemLimite() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660SemLimite();

        try {
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros.size(), equalTo(1));
            assertThat(erros.get(0), equalTo("O campo limite é obrigatório."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemNumeroVencimentoDiaMelhorCompraPortadorETipo() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660SemNumeroVencimentoDiaMelhorCompraPortadorTipoELimite();

        try {
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            MissingFieldException e1 = (MissingFieldException) e.getCause();
            List<String> erros = e1.getErros();

            assertThat(erros.size(), equalTo(6));
            assertThat(erros.get(0), equalTo("O campo número é obrigatório."));
            assertThat(erros.get(1), equalTo("O campo vencimento é obrigatório."));
            assertThat(erros.get(2), equalTo("O campo diaMelhorCompra é obrigatório."));
            assertThat(erros.get(3), equalTo("O campo portador é obrigatório."));
            assertThat(erros.get(4), equalTo("O campo tipo é obrigatório."));
            assertThat(erros.get(5), equalTo("O campo limite é obrigatório."));
            throw e;
        }
    }

    @Test
    public void criarUmCartao() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertCartaoFisicoEnok(cartaoDTOBuscado);
    }

    @Test
    public void criarUmCartaoVirtual() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoVirtual7339();

        assertThat(cartaoDTO.numero(), equalTo("7339"));
        assertThat(cartaoDTO.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTO.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTO.portador(), equalTo("Enok"));
        assertThat(cartaoDTO.tipo(), equalTo("DIGITAL"));
        assertThat(cartaoDTO.limite(), equalTo("2.000,00"));

        Long codigoCartao = cartaoBusiness.criar(cartaoDTO);
        assertTrue(codigoCartao >= 0);
    }

    @Test(expected = BusinessException.class)
    public void criarDoisCartoesComNumerosIguais() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660();

        try {
            cartaoBusiness.criar(cartaoDTO);
            cartaoBusiness.criar(cartaoDTO);
        }
        catch (BusinessException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Cartão duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDoisCartaos() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartao2DTO = criarCartaoFisico0744();
        Long codigoCartao2 = cartaoBusiness.criar(cartao2DTO);
        assertTrue(codigoCartao2 >= 0);

        assertThat(codigoCartao, not(equalTo(codigoCartao2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        cartaoDTOBuscado.codigo(null);

        cartaoBusiness.atualizar(cartaoDTOBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemNumero() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        cartaoDTOBuscado.numero(null);

        cartaoBusiness.atualizar(cartaoDTOBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemVencimento() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        cartaoDTOBuscado.vencimento(null);

        cartaoBusiness.atualizar(cartaoDTOBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDiaMelhorCompra() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        cartaoDTOBuscado.diaMelhorCompra(null);

        cartaoBusiness.atualizar(cartaoDTOBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemPortador() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        cartaoDTOBuscado.portador(null);

        cartaoBusiness.atualizar(cartaoDTOBuscado);
    }

    @Test(expected = BusinessException.class)
    public void alterarSemTipo() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        cartaoDTOBuscado.tipo(null);

        cartaoBusiness.atualizar(cartaoDTOBuscado);
    }

    @Test
    public void alterarNumeroDoCartao() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.numero("0774");
        cartaoBusiness.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertThat(cartaoDTOBuscado.numero(), equalTo("0774"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarVencimentoDoCartao() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.vencimento("27/04/2018");
        cartaoBusiness.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/04/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarDiaMelhorCompraDoCartao() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.diaMelhorCompra("17/05/2018");
        cartaoBusiness.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/05/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarPortadorDoCartao() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.portador("Carol");
        cartaoBusiness.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Carol"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarTipoDoCartao() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.tipo("DIGITAL");
        cartaoBusiness.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("DIGITAL"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarLimiteDoCartao() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.limite("2.100,00");
        cartaoBusiness.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.100,00"));
    }

    @Test(expected = BusinessException.class)
    public void excluirUmCartaoException() throws BusinessException {
        try {
            cartaoBusiness.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir o cartão."));
            throw e;
        }
    }

    @Test
    public void excluirUmCartao() throws BusinessException {
        Long codigoCartao = criarCartaoFisicoEnok();
        CartaoDTO cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);

        cartaoBusiness.excluir(cartaoDTOBuscado);

        cartaoDTOBuscado = cartaoBusiness.buscarCartaoPorId(codigoCartao);
        assertThat(cartaoDTOBuscado, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void buscarCartaoPorIdException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            CartaoDTO cartaoDTO = cartaoBusiness.buscarCartaoPorId(null);
            assertThat(cartaoDTO, nullValue());
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar o cartão por id."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void buscarCartoesException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            cartaoBusiness.buscarCartoes();
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar os cartões."));
            throw e;
        }
    }

    @Test
    public void buscarCartoes() throws BusinessException {
        criarCartaoFisicoEnok();
        criarCartaoFisicoCarol();

        List<CartaoDTO> cartoesDTO = cartaoBusiness.buscarCartoes();
        assertThat(cartoesDTO.size(), equalTo(2));

        assertCartaoFisicoCarol(cartoesDTO.get(0));
        assertCartaoFisicoEnok(cartoesDTO.get(1));
    }

    private Long criarCartaoFisicoEnok() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico7660();
        Long codigoCartao = cartaoBusiness.criar(cartaoDTO);
        assertTrue(codigoCartao >= 0);
        return codigoCartao;
    }

    private Long criarCartaoFisicoCarol() throws BusinessException {
        CartaoDTO cartaoDTO = criarCartaoFisico0744();
        Long codigoCartao = cartaoBusiness.criar(cartaoDTO);
        assertTrue(codigoCartao >= 0);
        return codigoCartao;
    }

    private void assertCartaoFisicoEnok(CartaoDTO cartaoDTOBuscado) {
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    private void assertCartaoFisicoCarol(CartaoDTO cartaoDTOBuscado) {
        assertThat(cartaoDTOBuscado.numero(), equalTo("0744"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Carol"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }
}
