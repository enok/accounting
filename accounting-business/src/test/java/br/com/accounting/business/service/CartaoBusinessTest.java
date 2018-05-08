package br.com.accounting.business.service;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.factory.CartaoDTOMockFactory;
import br.com.accounting.core.exception.StoreException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class CartaoBusinessTest extends GenericTest {
    @Autowired
    private CartaoBusiness business;

    @Test(expected = StoreException.class)
    public void criarUmCartaoSemDiretorio() throws StoreException, ValidationException, BusinessException, GenericException, IOException {
        try {
            deletarDiretorioEArquivos();
            criarCartaoFisicoEnok();
        }
        catch (StoreException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível buscar os registros."));
            throw e;
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaCartaoSemNumero() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660SemNumero();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "número");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaCartaoSemVencimento() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660SemVencimento();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "vencimento");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaCartaoSemDiaMelhorCompra() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660SemDiaMelhorCompra();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "diaMelhorCompra");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaCartaoSemPortador() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660SemPortador();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "portador");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaCartaoSemTipo() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660SemTipo();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "tipo");
        }
    }

    @Test(expected = ValidationException.class)
    public void criarUmaCartaoComTipoErrado() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660TipoErrado();
            business.criar(dto);
        }
        catch (ValidationException e) {
            assertThat(e.getCause().getMessage(), equalTo("No enum constant br.com.accounting.core.entity.TipoCartao.OUTRO"));
            throw e;
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaCartaoSemLimite() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660SemLimite();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "limite");
        }
    }

    @Test(expected = MissingFieldException.class)
    public void criarUmaCartaoSemCamposObrigatorios() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660SemCamposObrigatorios();
            business.criar(dto);
        }
        catch (ValidationException e) {
            MissingFieldException e1 = (MissingFieldException) e;
            assertCreationAndMandatoryFields(e1, "número", "vencimento", "diaMelhorCompra", "portador", "tipo", "limite");
        }
    }

    @Test
    public void criarUmCartao() throws StoreException, ValidationException, BusinessException, GenericException {
        Long codigo = criarCartaoFisicoEnok();
        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);
        assertCartaoFisicoEnok(cartaoDTOBuscado);
    }

    @Test
    public void criarUmCartaoVirtual() throws StoreException, ValidationException, BusinessException, GenericException {
        CartaoDTO dto = CartaoDTOMockFactory.criarCartaoVirtual7339();

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

    @Test(expected = ValidationException.class)
    public void criarDoisCartoesComNumerosIguais() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660();
            business.criar(dto);
            business.criar(dto);
        }
        catch (ValidationException e) {
            DuplicatedRegistryException e1 = (DuplicatedRegistryException) e;
            assertThat(e1.getMessage(), equalTo("Cartão duplicado."));
            throw e;
        }
    }

    @Test
    public void criarDoisCartaos() throws StoreException, ValidationException, BusinessException, GenericException {
        Long codigo1 = criarCartaoFisicoEnok();

        CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico0744();

        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo2 = codigos.get(0);
        assertTrue(codigo2 >= 0);

        assertThat(codigo1, not(equalTo(codigo2)));
    }

    @Test(expected = BusinessException.class)
    public void alterarSemCodigo() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarSemNumero() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarSemVencimento() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarSemDiaMelhorCompra() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarSemPortador() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarSemTipo() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alteracaoNaoPermitidaDoCodigo() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarNumeroDoCartao() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarVencimentoDoCartao() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarDiaMelhorCompraDoCartao() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarPortadorDoCartao() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarTipoDoCartao() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void alterarLimiteDoCartao() throws StoreException, ValidationException, BusinessException, GenericException {
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
    public void excluirUmCartaoException() throws StoreException, ValidationException, BusinessException, GenericException {
        try {
            business.excluir(null);
        }
        catch (BusinessException e) {
            assertThat(e.getMessage(), equalTo("Não foi possível excluir."));
            throw e;
        }
    }

    @Test
    public void excluirUmCartao() throws StoreException, ValidationException, BusinessException, GenericException {
        Long codigo = criarCartaoFisicoEnok();
        CartaoDTO cartaoDTOBuscado = business.buscarPorId(codigo);

        business.excluir(cartaoDTOBuscado);

        cartaoDTOBuscado = business.buscarPorId(codigo);
        assertThat(cartaoDTOBuscado, nullValue());
    }

    @Test(expected = StoreException.class)
    public void buscarCartaoPorIdException() throws StoreException, BusinessException, IOException {
        deletarDiretorioEArquivos();
        CartaoDTO dto = business.buscarPorId(null);
        assertThat(dto, nullValue());
    }

    @Test(expected = StoreException.class)
    public void buscarCartoesException() throws StoreException, BusinessException, IOException {
        deletarDiretorioEArquivos();
        business.buscarTodas();
    }

    @Test
    public void buscarCartoes() throws StoreException, ValidationException, BusinessException, GenericException {
        criarCartaoFisicoEnok();
        criarCartaoFisicoCarol();

        List<CartaoDTO> cartoesDTO = business.buscarTodas();
        assertThat(cartoesDTO.size(), equalTo(2));

        assertCartaoFisicoCarol(cartoesDTO.get(0));
        assertCartaoFisicoEnok(cartoesDTO.get(1));
    }

    private Long criarCartaoFisicoEnok() throws StoreException, ValidationException, BusinessException, GenericException {
        CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico7660();
        List<Long> codigos = business.criar(dto);
        assertThat(codigos.size(), equalTo(1));
        Long codigo = codigos.get(0);
        assertTrue(codigo >= 0);
        return codigo;
    }

    private void criarCartaoFisicoCarol() throws StoreException, ValidationException, BusinessException, GenericException {
        CartaoDTO dto = CartaoDTOMockFactory.criarCartaoFisico0744();
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
