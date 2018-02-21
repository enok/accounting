package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.ContabilidadeFactoryMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;

import static br.com.accounting.core.entity.Categoria.ENTRADA;
import static br.com.accounting.core.entity.Categoria.SAIDA;
import static br.com.accounting.core.entity.Status.NAO_PAGO;
import static br.com.accounting.core.entity.Status.PAGO;
import static br.com.accounting.core.entity.Tipo.FIXO;
import static br.com.accounting.core.entity.Tipo.VARIAVEL;
import static br.com.accounting.core.entity.TipoPagamento.*;
import static br.com.accounting.core.service.ServiceUtils.criarDiretorio;
import static br.com.accounting.core.service.ServiceUtils.deletarDiretorioEArquivos;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ContabilidadeServiceSalvarTest {

    @Autowired
    private ContabilidadeService contabilidadeService;

    @Test
    public void salvarContabilidadeCartaoCredito() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito();

        assertThat(contabilidade, notNullValue());
        assertThat(contabilidade.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidade.getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(contabilidade.getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(contabilidade.getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(contabilidade.getTipo(), equalTo(FIXO));
        assertThat(contabilidade.getGrupo(), equalTo("MORADIA"));
        assertThat(contabilidade.getSubGrupo(), equalTo("ASSINATURAS"));
        assertThat(contabilidade.getDescricao(), equalTo("spotify"));
        assertThat(contabilidade.getParcelamento().getParcela(), equalTo(1));
        assertThat(contabilidade.getParcelamento().getParcelas(), equalTo(12));
        assertThat(contabilidade.getCategoria(), equalTo(SAIDA));
        assertThat(contabilidade.getValor(), equalTo(26.90));
        assertThat(contabilidade.getStatus(), equalTo(PAGO));

        contabilidadeService.salvar(contabilidade);

        assertThat(contabilidade.getCodigo(), notNullValue());
    }

    @Test
    public void salvarContabilidadeCartaoDebito() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoDebito();

        assertThat(contabilidade, notNullValue());
        assertThat(contabilidade.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidade.getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(contabilidade.getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(contabilidade.getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(contabilidade.getTipo(), equalTo(VARIAVEL));
        assertThat(contabilidade.getGrupo(), equalTo("MERCADO"));
        assertThat(contabilidade.getSubGrupo(), equalTo("PADARIA"));
        assertThat(contabilidade.getDescricao(), equalTo("pão"));
        assertThat(contabilidade.getParcelamento(), nullValue());
        assertThat(contabilidade.getCategoria(), equalTo(SAIDA));
        assertThat(contabilidade.getValor(), equalTo(18.0));
        assertThat(contabilidade.getStatus(), equalTo(PAGO));

        contabilidadeService.salvar(contabilidade);

        assertThat(contabilidade.getCodigo(), notNullValue());
    }

    @Test
    public void salvarContabilidadeDinheiro() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createDinheiro();

        assertThat(contabilidade, notNullValue());
        assertThat(contabilidade.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidade.getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(contabilidade.getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(contabilidade.getSubTipoPagamento(), nullValue());
        assertThat(contabilidade.getTipo(), equalTo(FIXO));
        assertThat(contabilidade.getGrupo(), equalTo("RENDIMENTOS"));
        assertThat(contabilidade.getSubGrupo(), equalTo("SALARIO"));
        assertThat(contabilidade.getDescricao(), equalTo("sysmap"));
        assertThat(contabilidade.getParcelamento(), nullValue());
        assertThat(contabilidade.getCategoria(), equalTo(ENTRADA));
        assertThat(contabilidade.getValor(), equalTo(5000.0));
        assertThat(contabilidade.getStatus(), equalTo(NAO_PAGO));

        contabilidadeService.salvar(contabilidade);

        assertThat(contabilidade.getCodigo(), notNullValue());
    }

    @Test(expected = ServiceException.class)
    public void salvarContabilidadeServiceException() throws IOException, ServiceException {
        deletarDiretorioEArquivos();

        Contabilidade registro = ContabilidadeFactoryMock.createDinheiro();

        try {
            contabilidadeService.salvar(registro);
        } catch (ServiceException e) {
            criarDiretorio();
            throw e;
        }
    }
}
