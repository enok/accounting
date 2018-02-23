package br.com.accounting.core.service;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.ContabilidadeFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static br.com.accounting.core.entity.Categoria.ENTRADA;
import static br.com.accounting.core.entity.Categoria.SAIDA;
import static br.com.accounting.core.entity.Status.NAO_PAGO;
import static br.com.accounting.core.entity.Status.PAGO;
import static br.com.accounting.core.entity.Tipo.FIXO;
import static br.com.accounting.core.entity.Tipo.VARIAVEL;
import static br.com.accounting.core.entity.TipoPagamento.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContabilidadeServiceBuscarTest extends ContabilidadeGenericTest {
    @Autowired
    private ContabilidadeService contabilidadeService;

    @PostConstruct
    public void posConstrucao() {
        setContabilidadeService(contabilidadeService);
    }

    @Test
    public void buscarRegistrosContabilidade() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoCredito744();

        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();
        assertThat(registros, notNullValue());

        Contabilidade contabilidadeBuscada = registros.get(0);

        assertThat(contabilidadeBuscada, notNullValue());
        assertThat(contabilidadeBuscada.getCodigo(), notNullValue());
        assertThat(contabilidadeBuscada.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidadeBuscada.getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(contabilidadeBuscada.getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(contabilidadeBuscada.getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(contabilidadeBuscada.getTipo(), equalTo(FIXO));
        assertThat(contabilidadeBuscada.getGrupo().getDescricao(), equalTo("MORADIA"));
        assertThat(contabilidadeBuscada.getGrupo().getSubGrupo().getDescricao(), equalTo("ASSINATURA"));
        assertThat(contabilidadeBuscada.getDescricao(), equalTo("spotify"));
        assertThat(contabilidadeBuscada.getParcelamento().getParcela(), equalTo(1));
        assertThat(contabilidadeBuscada.getParcelamento().getParcelas(), equalTo(12));
        assertThat(contabilidadeBuscada.getCategoria(), equalTo(SAIDA));
        assertThat(contabilidadeBuscada.getValor(), equalTo(26.90));
        assertThat(contabilidadeBuscada.getStatus(), equalTo(PAGO));
    }

    @Test
    public void buscarRegistrosContabilidadeSemParcelamento() throws ServiceException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createCartaoDebito7660();

        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();
        assertThat(registros, notNullValue());

        Contabilidade contabilidadeBuscada = registros.get(0);

        assertThat(contabilidadeBuscada, notNullValue());
        assertThat(contabilidadeBuscada.getCodigo(), notNullValue());
        assertThat(contabilidadeBuscada.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidadeBuscada.getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(contabilidadeBuscada.getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(contabilidadeBuscada.getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(contabilidadeBuscada.getTipo(), equalTo(VARIAVEL));
        assertThat(contabilidadeBuscada.getGrupo().getDescricao(), equalTo("MERCADO"));
        assertThat(contabilidadeBuscada.getGrupo().getSubGrupo().getDescricao(), equalTo("PADARIA"));
        assertThat(contabilidadeBuscada.getDescricao(), equalTo("pão"));
        assertThat(contabilidadeBuscada.getParcelamento(), nullValue());
        assertThat(contabilidadeBuscada.getCategoria(), equalTo(SAIDA));
        assertThat(contabilidadeBuscada.getValor(), equalTo(18.0));
        assertThat(contabilidadeBuscada.getStatus(), equalTo(PAGO));
    }

    @Test
    public void buscarRegistrosContabilidadeSemSubTipoParcelamentoSemParcelamento() throws ServiceException, IOException {
        Contabilidade contabilidade = ContabilidadeFactoryMock.createDinheiro();

        contabilidadeService.salvar(contabilidade);

        List<Contabilidade> registros = contabilidadeService.buscarRegistros();
        assertThat(registros, notNullValue());

        Contabilidade contabilidadeBuscada = registros.get(0);

        assertThat(contabilidadeBuscada, notNullValue());
        assertThat(contabilidadeBuscada.getCodigo(), notNullValue());
        assertThat(contabilidadeBuscada.getDataLancamentoFormatada(), equalTo("01/01/2018"));
        assertThat(contabilidadeBuscada.getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(contabilidadeBuscada.getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(contabilidadeBuscada.getSubTipoPagamento(), nullValue());
        assertThat(contabilidadeBuscada.getTipo(), equalTo(FIXO));
        assertThat(contabilidadeBuscada.getGrupo().getDescricao(), equalTo("RENDIMENTOS"));
        assertThat(contabilidadeBuscada.getGrupo().getSubGrupo().getDescricao(), equalTo("SALARIO"));
        assertThat(contabilidadeBuscada.getDescricao(), equalTo("sysmap"));
        assertThat(contabilidadeBuscada.getParcelamento(), nullValue());
        assertThat(contabilidadeBuscada.getCategoria(), equalTo(ENTRADA));
        assertThat(contabilidadeBuscada.getValor(), equalTo(5000.0));
        assertThat(contabilidadeBuscada.getStatus(), equalTo(NAO_PAGO));
    }

    @Test(expected = ServiceException.class)
    public void buscarRegistrosContabilidadeServiceException() throws IOException, ServiceException {
        contabilidadeService.buscarRegistros();
    }
}
