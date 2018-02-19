package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.SubTipoPagamentoFactoryMock;
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
import java.util.List;

import static br.com.accounting.core.repository.impl.GenericRepository.DIRETORIO;
import static br.com.accounting.core.service.ServiceUtils.deletarArquivosDoDiretorio;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SubTipoPagamentoServiceTest {

    @Autowired
    private SubTipoPagamentoService subTipoPagamentoService;

    @Test
    public void salvarSubTipoPagamento() throws ServiceException {
        SubTipoPagamento subTipoPagamento = SubTipoPagamentoFactoryMock.create();

        assertThat(subTipoPagamento, notNullValue());
        assertThat(subTipoPagamento.getDescricao(), equalTo("744"));

        subTipoPagamentoService.salvar(subTipoPagamento);

        assertThat(subTipoPagamento.getCodigo(), notNullValue());
    }

    @Test(expected = ServiceException.class)
    public void salvarSubTipoPagamentoServiceException() throws ServiceException {
        subTipoPagamentoService.salvar(null);
    }

    @Test(expected = ServiceException.class)
    public void salvarSubTipoPagamentoRepositoryException() throws IOException, ServiceException {
        Path diretorio = Paths.get(DIRETORIO);
        Files.walk(diretorio, FileVisitOption.FOLLOW_LINKS)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .peek(System.out::println)
                .forEach(File::delete);

        SubTipoPagamento subTipoPagamento = SubTipoPagamentoFactoryMock.create();

        try {
            subTipoPagamentoService.salvar(subTipoPagamento);
        } catch (ServiceException e) {
            Files.createDirectory(Paths.get(DIRETORIO));
            throw e;
        }
    }

    @Test
    public void buscarRegistrosSubTipoPagamento() throws ServiceException, IOException {
        deletarArquivosDoDiretorio();

        SubTipoPagamento subTipoPagamento = SubTipoPagamentoFactoryMock.create();

        subTipoPagamentoService.salvar(subTipoPagamento);

        List<SubTipoPagamento> registros = subTipoPagamentoService.buscarRegistros();
        assertThat(registros, notNullValue());

        SubTipoPagamento subTipoPagamentoBuscado = registros.get(0);

        assertThat(subTipoPagamentoBuscado, notNullValue());
        assertThat(subTipoPagamentoBuscado.getDescricao(), equalTo("744"));
    }

    @Test(expected = ServiceException.class)
    public void buscarRegistrosSubTipoPagamentoServiceException() throws IOException, ServiceException {
        deletarArquivosDoDiretorio();

        subTipoPagamentoService.buscarRegistros();
    }
}
