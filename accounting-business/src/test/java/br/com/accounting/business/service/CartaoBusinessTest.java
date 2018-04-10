package br.com.accounting.business.service;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
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
    private CartaoBusiness business;

    @Test(expected = BusinessException.class)
    public void criarUmCartaoSemDiretorio() throws IOException, BusinessException {
        try {
            deletarDiretorioEArquivos();
            criarCartaoFisicoEnok();
        }
        catch (BusinessException e) {
            assertCreation(e);
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemNumero() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660SemNumero();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "número");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemVencimento() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660SemVencimento();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "vencimento");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemDiaMelhorCompra() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660SemDiaMelhorCompra();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "diaMelhorCompra");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemPortador() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660SemPortador();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "portador");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemTipo() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660SemTipo();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "tipo");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoComTipoErrado() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660TipoErrado();
            business.criar(dto);
        }
        catch (BusinessException e) {
            IllegalArgumentException e1 = (IllegalArgumentException) e.getCause();
            assertThat(e1.getMessage(), equalTo("No enum constant br.com.accounting.core.entity.TipoCartao.OUTRO"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemLimite() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660SemLimite();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "limite");
        }
    }

    @Test(expected = BusinessException.class)
    public void criarUmaCartaoSemCamposObrigatorios() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660SemCamposObrigatorios();
            business.criar(dto);
        }
        catch (BusinessException e) {
            assertCreationAndMandatoryFields(e, "número", "vencimento", "diaMelhorCompra", "portador", "tipo", "limite");
        }
    }

    @Test
    public void criarUmCartao() throws BusinessException {
        Long codigo = criarCartaoFisicoEnok();
        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
        assertCartaoFisicoEnok(cartaoDTOBuscado);
    }

    @Test
    public void criarUmCartaoVirtual() throws BusinessException {
        CartaoDTO dto = criarCartaoVirtual7339();

        assertThat(dto.numero(), equalTo("7339"));
        assertThat(dto.vencimento(), equalTo("27/03/2018"));
        assertThat(dto.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(dto.portador(), equalTo("Enok"));
        assertThat(dto.tipo(), equalTo("DIGITAL"));
        assertThat(dto.limite(), equalTo("2.000,00"));

        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
    }

    @Test(expected = BusinessException.class)
    public void criarDoisCartoesComNumerosIguais() throws BusinessException {
        try {
            CartaoDTO dto = criarCartaoFisico7660();
            business.criar(dto);
            business.criar(dto);
        }
        catch (BusinessException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e.getCause();
            assertThat(e1.getMessage(), equalTo("Cartão duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDoisCartaos() throws BusinessException {
        Long codigo1 = criarCartaoFisicoEnok();

        CartaoDTO dto = criarCartaoFisico0744();

        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo2 = codigos.get(0);
        assertTrue(codigo2 >= 0);

        assertThat(codigo1, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws BusinessException {
        try {
            Long codigo = criarCartaoFisicoEnok();
            CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
            cartaoDTOBuscado.codigo(null);
            business.atualizar(cartaoDTOBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "código");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemNumero() throws BusinessException {
        try {
            Long codigo = criarCartaoFisicoEnok();
            CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
            cartaoDTOBuscado.numero(null);
            business.atualizar(cartaoDTOBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "número");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemVencimento() throws BusinessException {
        try {
            Long codigo = criarCartaoFisicoEnok();
            CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
            cartaoDTOBuscado.vencimento(null);
            business.atualizar(cartaoDTOBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "vencimento");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemDiaMelhorCompra() throws BusinessException {
        try {
            Long codigo = criarCartaoFisicoEnok();
            CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
            cartaoDTOBuscado.diaMelhorCompra(null);
            business.atualizar(cartaoDTOBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "diaMelhorCompra");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemPortador() throws BusinessException {
        try {
            Long codigo = criarCartaoFisicoEnok();
            CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
            cartaoDTOBuscado.portador(null);
            business.atualizar(cartaoDTOBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "portador");
        }
    }

    @Test(expected = BusinessException.class)
    public void alterarSemTipo() throws BusinessException {
        try {
            Long codigo = criarCartaoFisicoEnok();
            CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
            cartaoDTOBuscado.tipo(null);
            business.atualizar(cartaoDTOBuscado);
        }
        catch (BusinessException e) {
            assertUpdateAndMandatoryFields(e, "tipo");
        }
    }

    @Test(expected = BusinessException.class)
    public void alteracaoNaoPermitidaDoCodigo() throws BusinessException {
        try {
            Long codigo = criarCartaoFisicoEnok();
            String codigoAnterior = String.valueOf(codigo);
            String codigoNovo = "10";
            CartaoDTO dtoBuscado = business.buscarPorId(Long.parseLong(codigoAnterior));
            assertThat(dtoBuscado.codigo(), not(equalTo(codigoNovo)));
            dtoBuscado.codigo(codigoNovo);
            business.atualizar(dtoBuscado);
        }
        catch (BusinessException e) {
            assertUpdateNotModifiebleFields(e, "código");
        }
    }


    @Test
    public void alterarNumeroDoCartao() throws BusinessException {
        Long codigo = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.numero("0774");
        business.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = business.buscarPorId(codigo);
        assertThat(cartaoDTOBuscado.numero(), equalTo("0774"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarVencimentoDoCartao() throws BusinessException {
        Long codigo = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.vencimento("27/04/2018");
        business.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = business.buscarPorId(codigo);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/04/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarDiaMelhorCompraDoCartao() throws BusinessException {
        Long codigo = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.diaMelhorCompra("17/05/2018");
        business.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = business.buscarPorId(codigo);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/05/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarPortadorDoCartao() throws BusinessException {
        Long codigo = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.portador("Carol");
        business.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = business.buscarPorId(codigo);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Carol"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("FISICO"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarTipoDoCartao() throws BusinessException {
        Long codigo = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.tipo("DIGITAL");
        business.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = business.buscarPorId(codigo);
        assertThat(cartaoDTOBuscado.numero(), equalTo("7660"));
        assertThat(cartaoDTOBuscado.vencimento(), equalTo("27/03/2018"));
        assertThat(cartaoDTOBuscado.diaMelhorCompra(), equalTo("17/04/2018"));
        assertThat(cartaoDTOBuscado.portador(), equalTo("Enok"));
        assertThat(cartaoDTOBuscado.tipo(), equalTo("DIGITAL"));
        assertThat(cartaoDTOBuscado.limite(), equalTo("2.000,00"));
    }

    @Test
    public void alterarLimiteDoCartao() throws BusinessException {
        Long codigo = criarCartaoFisicoEnok();

        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
        assertCartaoFisicoEnok(cartaoDTOBuscado);

        cartaoDTOBuscado.limite("2.100,00");
        business.atualizar(cartaoDTOBuscado);

        cartaoDTOBuscado = business.buscarPorId(codigo);
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
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
            throw e;
        }
    }

    @Test
    public void excluirUmCartao() throws BusinessException {
        Long codigo = criarCartaoFisicoEnok();
        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);

        business.excluir(cartaoDTOBuscado);

        cartaoDTOBuscado = business.buscarPorId(codigo);
        assertThat(cartaoDTOBuscado, nullValue());
    }

    @Test(expected = BusinessException.class)
    public void buscarCartaoPorIdException() throws IOException, BusinessException {
        deletarDiretorioEArquivos();
        try {
            CartaoDTO dto = business.buscarPorId(null);
            assertThat(dto, nullValue());
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar por id."));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void buscarCartoesException() throws IOException, BusinessException {
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
    public void buscarCartoes() throws BusinessException {
        criarCartaoFisicoEnok();
        criarCartaoFisicoCarol();

        List<CartaoDTO> cartoesDTO = business.buscarTodas();
        assertThat(cartoesDTO.size(), equalTo(2));

        assertCartaoFisicoCarol(cartoesDTO.get(0));
        assertCartaoFisicoEnok(cartoesDTO.get(1));
    }

    private Long criarCartaoFisicoEnok() throws BusinessException {
        CartaoDTO dto = criarCartaoFisico7660();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void criarCartaoFisicoCarol() throws BusinessException {
        CartaoDTO dto = criarCartaoFisico0744();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
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
