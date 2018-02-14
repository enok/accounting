package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Parcelamento;
import br.com.accounting.core.entity.Registro;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.repository.RegistroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Repository
public class RegistroRepositoryImpl implements RegistroRepository {
    private static final Logger LOG = LoggerFactory.getLogger(RegistroRepositoryImpl.class);

    private static final String SEPARADOR = ";";
    private static final String DIRETORIO = "arquivos";
    private static final String ARQUIVO = DIRETORIO + "\\registros.csv";

    @Override
    public void salvar(Registro registro) throws RepositoryException {
        LOG.info("[ salvar ] registro: " + registro);

        String linha = criarLinha(registro);
        try {
            String caminhoArquivo = ARQUIVO;
            Files.write(Paths.get(caminhoArquivo), linha.getBytes(), APPEND, CREATE);
        } catch (IOException e) {
            String message = "Nao foi possivel salvar a linha: " + linha;
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    private String criarLinha(Registro registro) {

        Parcelamento parcelamento = registro.getParcelamento();
        Integer parcela = null;
        Integer parcelas = null;

        if (parcelamento != null) {
            parcela = parcelamento.getParcela();
            parcelas = parcelamento.getParcelas();
        }

        StringBuilder builder = new StringBuilder()
                .append(registro.getCodigo()).append(SEPARADOR)
                .append(registro.getVencimentoFormatado()).append(SEPARADOR)
                .append(registro.getTipoPagamento()).append(SEPARADOR)
                .append(registro.getSubTipoPagamento()).append(SEPARADOR)
                .append(registro.getTipo()).append(SEPARADOR)
                .append(registro.getGrupo()).append(SEPARADOR)
                .append(registro.getSubGrupo()).append(SEPARADOR)
                .append(registro.getDescricao()).append(SEPARADOR)
                .append(parcela).append(SEPARADOR)
                .append(parcelas).append(SEPARADOR)
                .append(registro.getCategoria()).append(SEPARADOR)
                .append(registro.getValor()).append(SEPARADOR)
                .append(registro.getStatus()).append("\n");

        return builder.toString();
    }
}
