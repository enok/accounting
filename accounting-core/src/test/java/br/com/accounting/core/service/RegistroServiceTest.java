package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import br.com.accounting.core.entity.Registro;
import br.com.accounting.core.entity.RegistroFactory;
import br.com.accounting.core.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static br.com.accounting.core.entity.Categoria.ENTRADA;
import static br.com.accounting.core.entity.Categoria.SAIDA;
import static br.com.accounting.core.entity.Status.NAO_PAGO;
import static br.com.accounting.core.entity.Status.PAGO;
import static br.com.accounting.core.entity.Tipo.FIXO;
import static br.com.accounting.core.entity.Tipo.VARIAVEL;
import static br.com.accounting.core.entity.TipoPagamento.*;
import static br.com.accounting.core.repository.impl.GenericRepository.DIRETORIO;
import static java.nio.file.StandardOpenOption.CREATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RegistroServiceTest {

    @Autowired
    private RegistroService registroService;

    @Test
    public void salvarRegistroCartaoCredito() throws ServiceException {
        Registro registro = RegistroFactory.createCartaoCredito();

        assertThat(registro, notNullValue());
        assertThat(registro.getVencimentoFormatado(), equalTo("27/01/2018"));
        assertThat(registro.getTipoPagamento(), equalTo(CARTAO_CREDITO));
        assertThat(registro.getSubTipoPagamento().getDescricao(), equalTo("744"));
        assertThat(registro.getTipo(), equalTo(FIXO));
        assertThat(registro.getGrupo(), equalTo("MORADIA"));
        assertThat(registro.getSubGrupo(), equalTo("ASSINATURAS"));
        assertThat(registro.getDescricao(), equalTo("spotify"));
        assertThat(registro.getParcelamento().getParcela(), equalTo(1));
        assertThat(registro.getParcelamento().getParcelas(), equalTo(12));
        assertThat(registro.getCategoria(), equalTo(SAIDA));
        assertThat(registro.getValor(), equalTo(26.90));
        assertThat(registro.getStatus(), equalTo(PAGO));

        registroService.salvar(registro);

        assertThat(registro.getCodigo(), notNullValue());
    }

    @Test
    public void salvarRegistroCartaoDebito() throws ServiceException {
        Registro registro = RegistroFactory.createCartaoDebito();

        assertThat(registro, notNullValue());
        assertThat(registro.getVencimentoFormatado(), equalTo("15/01/2018"));
        assertThat(registro.getTipoPagamento(), equalTo(CARTAO_DEBITO));
        assertThat(registro.getSubTipoPagamento().getDescricao(), equalTo("7660"));
        assertThat(registro.getTipo(), equalTo(VARIAVEL));
        assertThat(registro.getGrupo(), equalTo("MERCADO"));
        assertThat(registro.getSubGrupo(), equalTo("PADARIA"));
        assertThat(registro.getDescricao(), equalTo("pão"));
        assertThat(registro.getParcelamento(), nullValue());
        assertThat(registro.getCategoria(), equalTo(SAIDA));
        assertThat(registro.getValor(), equalTo(18.0));
        assertThat(registro.getStatus(), equalTo(PAGO));

        registroService.salvar(registro);

        assertThat(registro.getCodigo(), notNullValue());
    }

    @Test
    public void salvarRegistroDinheiro() throws ServiceException {
        Registro registro = RegistroFactory.createDinheiro();

        assertThat(registro, notNullValue());
        assertThat(registro.getVencimentoFormatado(), equalTo("25/01/2018"));
        assertThat(registro.getTipoPagamento(), equalTo(DINHEIRO));
        assertThat(registro.getSubTipoPagamento(), nullValue());
        assertThat(registro.getTipo(), equalTo(FIXO));
        assertThat(registro.getGrupo(), equalTo("RENDIMENTOS"));
        assertThat(registro.getSubGrupo(), equalTo("SALARIO"));
        assertThat(registro.getDescricao(), equalTo("sysmap"));
        assertThat(registro.getParcelamento(), nullValue());
        assertThat(registro.getCategoria(), equalTo(ENTRADA));
        assertThat(registro.getValor(), equalTo(5000.0));
        assertThat(registro.getStatus(), equalTo(NAO_PAGO));

        registroService.salvar(registro);

        assertThat(registro.getCodigo(), notNullValue());
    }

    @Test(expected = ServiceException.class)
    public void salvarRegistroServiceException() throws ServiceException {
        registroService.salvar(null);
    }

    @Test(expected = ServiceException.class)
    public void salvarRegistroRepositoryException() throws IOException, ServiceException {
        Path diretorio = Paths.get(DIRETORIO);
        Files.walk(diretorio, FileVisitOption.FOLLOW_LINKS)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .peek(System.out::println)
                .forEach(File::delete);

        Registro registro = RegistroFactory.createDinheiro();

        try {
            registroService.salvar(registro);
        } catch (ServiceException e) {
            Files.createDirectory(Paths.get(DIRETORIO));
            throw e;
        }
    }
}
