package br.com.accounting.business.service;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.TechnicalException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static br.com.accounting.business.factory.ContabilidadeDTOFactoryMock.*;
import static br.com.accounting.core.util.Utils.getNextMonth;
import static br.com.accounting.core.util.Utils.getStringFromCurrentDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContabilidadeBusinessTest extends GenericTest {

    @Autowired
    private ContabilidadeBusiness contabilidadeBusiness;

    @Test
    public void salvarContabilidade() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTO1();

        List<Long> codigos = contabilidadeBusiness.salvar(contabilidadeDTO);

        assertThat(codigos, notNullValue());
        assertThat(codigos.size(), equalTo(12));

        List<ContabilidadeDTO> contabilidades = contabilidadeBusiness.buscarTudo();

        assertThat(contabilidades, notNullValue());
        assertThat(contabilidades.size(), equalTo(12));

        String vencimento = "27/12/2017";

        String codigoPai = null;

        for (int i = 0; i < contabilidades.size(); i++) {
            String parcela = String.valueOf(i + 1);

            assertThat(contabilidades.get(i).getCodigo(), notNullValue());
            assertThat(contabilidades.get(i).getDataLancamento(), equalTo(getStringFromCurrentDate()));
            assertThat(contabilidades.get(i).getVencimento(), equalTo(vencimento));
            assertThat(contabilidades.get(i).getTipoPagamento(), equalTo("CARTAO_CREDITO"));
            assertThat(contabilidades.get(i).getSubTipoPagamento(), equalTo("7660"));
            assertThat(contabilidades.get(i).getTipo(), equalTo("FIXO"));
            assertThat(contabilidades.get(i).getGrupo(), equalTo("MORADIA"));
            assertThat(contabilidades.get(i).getSubGrupo(), equalTo("ASSINATURA"));
            assertThat(contabilidades.get(i).getDescricao(), equalTo("spotify"));
            assertThat(contabilidades.get(i).getParcela(), equalTo(parcela));
            assertThat(contabilidades.get(i).getParcelas(), equalTo("12"));
            assertThat(contabilidades.get(i).getParcelaCodigoPai(), equalTo(codigoPai));
            assertThat(contabilidades.get(i).getCategoria(), equalTo("SAIDA"));
            assertThat(contabilidades.get(i).getValor(), equalTo("26,90"));
            assertThat(contabilidades.get(i).getStatus(), equalTo("PAGO"));

            if (i == 0) {
                codigoPai = contabilidades.get(i).getCodigo();
            }
            vencimento = getNextMonth(vencimento);
        }
    }

    @Test
    public void salvarContabilidade2() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTO2();

        List<Long> codigos = contabilidadeBusiness.salvar(contabilidadeDTO);

        assertThat(codigos, notNullValue());
        assertThat(codigos.size(), equalTo(1));

        List<ContabilidadeDTO> contabilidades = contabilidadeBusiness.buscarTudo();

        assertThat(contabilidades, notNullValue());
        assertThat(contabilidades.size(), equalTo(1));

        for (int i = 0; i < contabilidades.size(); i++) {
            assertThat(contabilidades.get(i).getCodigo(), notNullValue());
            assertThat(contabilidades.get(i).getDataLancamento(), equalTo(getStringFromCurrentDate()));
            assertThat(contabilidades.get(i).getVencimento(), equalTo("27/02/2018"));
            assertThat(contabilidades.get(i).getTipoPagamento(), equalTo("CARTAO_CREDITO"));
            assertThat(contabilidades.get(i).getSubTipoPagamento(), equalTo("7660"));
            assertThat(contabilidades.get(i).getTipo(), equalTo("VARIAVEL"));
            assertThat(contabilidades.get(i).getGrupo(), equalTo("MORADIA"));
            assertThat(contabilidades.get(i).getSubGrupo(), equalTo("ASSINATURA"));
            assertThat(contabilidades.get(i).getDescricao(), equalTo("internet"));
            assertThat(contabilidades.get(i).getParcela(), nullValue());
            assertThat(contabilidades.get(i).getParcelas(), nullValue());
            assertThat(contabilidades.get(i).getParcelaCodigoPai(), nullValue());
            assertThat(contabilidades.get(i).getCategoria(), equalTo("SAIDA"));
            assertThat(contabilidades.get(i).getValor(), equalTo("174,98"));
            assertThat(contabilidades.get(i).getStatus(), equalTo("PAGO"));
        }
    }

    @Test
    public void salvarContabilidade3() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTO3();

        List<Long> codigos = contabilidadeBusiness.salvar(contabilidadeDTO);

        assertThat(codigos, notNullValue());
        assertThat(codigos.size(), equalTo(1));

        List<ContabilidadeDTO> contabilidades = contabilidadeBusiness.buscarTudo();

        assertThat(contabilidades, notNullValue());
        assertThat(contabilidades.size(), equalTo(1));

        for (int i = 0; i < contabilidades.size(); i++) {
            assertThat(contabilidades.get(i).getCodigo(), notNullValue());
            assertThat(contabilidades.get(i).getDataLancamento(), equalTo(getStringFromCurrentDate()));
            assertThat(contabilidades.get(i).getVencimento(), equalTo("15/01/2018"));
            assertThat(contabilidades.get(i).getTipoPagamento(), equalTo("CARTAO_DEBITO"));
            assertThat(contabilidades.get(i).getSubTipoPagamento(), equalTo("744"));
            assertThat(contabilidades.get(i).getTipo(), equalTo("VARIAVEL"));
            assertThat(contabilidades.get(i).getGrupo(), equalTo("MERCADO"));
            assertThat(contabilidades.get(i).getSubGrupo(), equalTo("PADARIA"));
            assertThat(contabilidades.get(i).getDescricao(), equalTo("pão"));
            assertThat(contabilidades.get(i).getParcela(), nullValue());
            assertThat(contabilidades.get(i).getParcelas(), nullValue());
            assertThat(contabilidades.get(i).getParcelaCodigoPai(), nullValue());
            assertThat(contabilidades.get(i).getCategoria(), equalTo("SAIDA"));
            assertThat(contabilidades.get(i).getValor(), equalTo("3,90"));
            assertThat(contabilidades.get(i).getStatus(), equalTo("PAGO"));
        }
    }

    @Test
    public void salvarContabilidade4() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTO4();

        List<Long> codigos = contabilidadeBusiness.salvar(contabilidadeDTO);

        assertThat(codigos, notNullValue());
        assertThat(codigos.size(), equalTo(1));

        List<ContabilidadeDTO> contabilidades = contabilidadeBusiness.buscarTudo();

        assertThat(contabilidades, notNullValue());
        assertThat(contabilidades.size(), equalTo(1));

        for (int i = 0; i < contabilidades.size(); i++) {
            assertThat(contabilidades.get(i).getCodigo(), notNullValue());
            assertThat(contabilidades.get(i).getDataLancamento(), equalTo(getStringFromCurrentDate()));
            assertThat(contabilidades.get(i).getVencimento(), equalTo("25/02/2018"));
            assertThat(contabilidades.get(i).getTipoPagamento(), equalTo("DINHEIRO"));
            assertThat(contabilidades.get(i).getSubTipoPagamento(), equalTo("transferencia"));
            assertThat(contabilidades.get(i).getTipo(), equalTo("VARIAVEL"));
            assertThat(contabilidades.get(i).getGrupo(), equalTo("RENDIMENTO"));
            assertThat(contabilidades.get(i).getSubGrupo(), equalTo("SALARIO"));
            assertThat(contabilidades.get(i).getDescricao(), equalTo("sysmap"));
            assertThat(contabilidades.get(i).getParcela(), nullValue());
            assertThat(contabilidades.get(i).getParcelas(), nullValue());
            assertThat(contabilidades.get(i).getParcelaCodigoPai(), nullValue());
            assertThat(contabilidades.get(i).getCategoria(), equalTo("ENTRADA"));
            assertThat(contabilidades.get(i).getValor(), equalTo("3.000,00"));
            assertThat(contabilidades.get(i).getStatus(), equalTo("NAO_PAGO"));
        }
    }

    @Test
    public void salvarContabilidade5() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTO5();

        List<Long> codigos = contabilidadeBusiness.salvar(contabilidadeDTO);

        assertThat(codigos, notNullValue());
        assertThat(codigos.size(), equalTo(1));

        List<ContabilidadeDTO> contabilidades = contabilidadeBusiness.buscarTudo();

        assertThat(contabilidades, notNullValue());
        assertThat(contabilidades.size(), equalTo(1));

        for (int i = 0; i < contabilidades.size(); i++) {
            assertThat(contabilidades.get(i).getCodigo(), notNullValue());
            assertThat(contabilidades.get(i).getDataLancamento(), equalTo(getStringFromCurrentDate()));
            assertThat(contabilidades.get(i).getVencimento(), equalTo("28/02/2018"));
            assertThat(contabilidades.get(i).getTipoPagamento(), equalTo("DINHEIRO"));
            assertThat(contabilidades.get(i).getSubTipoPagamento(), nullValue());
            assertThat(contabilidades.get(i).getTipo(), equalTo("VARIAVEL"));
            assertThat(contabilidades.get(i).getGrupo(), equalTo("SAUDE"));
            assertThat(contabilidades.get(i).getSubGrupo(), equalTo("FARMACIA"));
            assertThat(contabilidades.get(i).getDescricao(), equalTo("puran t4"));
            assertThat(contabilidades.get(i).getParcela(), nullValue());
            assertThat(contabilidades.get(i).getParcelas(), nullValue());
            assertThat(contabilidades.get(i).getParcelaCodigoPai(), nullValue());
            assertThat(contabilidades.get(i).getCategoria(), equalTo("SAIDA"));
            assertThat(contabilidades.get(i).getValor(), equalTo("20,00"));
            assertThat(contabilidades.get(i).getStatus(), equalTo("PAGO"));
        }
    }

    @Test(expected = TechnicalException.class)
    public void salvarContabilidadeTechnicalException() throws IOException, TechnicalException, BusinessException {
        deletarDiretorioEArquivos();

        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTO5();

        contabilidadeBusiness.salvar(contabilidadeDTO);
    }

    @Test(expected = TechnicalException.class)
    public void buscarTudoContabilidadeTechnicalException() throws TechnicalException, BusinessException, IOException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTO5();

        List<Long> codigos = contabilidadeBusiness.salvar(contabilidadeDTO);

        assertThat(codigos, notNullValue());
        assertThat(codigos.size(), equalTo(1));

        deletarDiretorioEArquivos();

        contabilidadeBusiness.buscarTudo();
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemVencimento() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemVencimento();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: vencimento"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemTipoPagamento() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemTipoPagamento();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: tipoPagamento"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeComTipoPagamentoErrado() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOComTipoPagamentoErrado();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Enum TipoPagamento com valor incorreto: CARTAO_CREDITO_2"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemTipo() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemTipo();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: tipo"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeComTipoErrado() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOComTipoErrado();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Enum Tipo com valor incorreto: FIXO_2"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemGrupo() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemGrupo();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: grupo"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemSubGrupo() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemSubGrupo();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: subGrupo"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemDescricao() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemDescricao();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: descricao"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemCategoria() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemCategoria();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: categoria"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeComCategoriaErrado() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOComCategoriaErrada();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Enum Categoria com valor incorreto: SAIDA_2"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemValor() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemValor();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: valor"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSemStatus() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOSemStatus();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Campo obrigatorio faltando: status"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeComStatusErrado() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeDTOComStatusErrado();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Enum Status com valor incorreto: PAGO_2"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSeTipoPagamentoCartaoCreditoSubTipoPagamentoObrigatorio() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeCartaoDeCreditoSemSubTipoPagamento();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Quando o TipoPagamento é " + "CARTAO_CREDITO" + "o campo SubTipoPagamento é obrigatório"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSeTipoPagamentoCartaoDebitoSubTipoPagamentoObrigatorio() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeCartaoDeDebitoSemSubTipoPagamento();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
                assertThat(e.getCause().getMessage(), is("Quando o TipoPagamento é " + "CARTAO_DEBITO" + "o campo SubTipoPagamento é obrigatório"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSeCampoTipoFixoCampoParcelasObrigatorio() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeTipoFixoSemParcelas();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Quando o Tipo é FIXO o campo Parcelas é obrigatório"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeSeCampoTipoFixoCampoParcelasComValorErrado() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeTipoFixoSemParcelasDiferenteDe12();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("Quando o Tipo é FIXO o campo Parcelas deve ter o valor 12"));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void salvarContabilidadeValorNaoPodeSerNegativo() throws TechnicalException, BusinessException {
        ContabilidadeDTO contabilidadeDTO = createContabilidadeComValorNegativo();

        try {
            contabilidadeBusiness.salvar(contabilidadeDTO);
        }
        catch (BusinessException e) {
            assertThat(e.getCause().getMessage(), is("O valor não pode ser negativo"));
            throw e;
        }
    }
}
