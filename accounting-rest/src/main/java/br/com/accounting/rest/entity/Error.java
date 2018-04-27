package br.com.accounting.rest.entity;

import br.com.accounting.business.exception.ValidationException;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Error {
    private Integer codigo;
    private List<String> mensagens = new ArrayList<>();

    public Error(Integer codigo, String mensagem) {
        this.codigo = codigo;
        mensagens.add(mensagem);
    }

    public Error(Integer codigo, Exception e) {
        this.codigo = codigo;
        handlerValidationException(e);
    }

    private void handlerValidationException(Exception e) {
        if (e instanceof ValidationException) {
            ValidationException e1 = (ValidationException) e;
            List<String> erros = e1.getErros();
            if (CollectionUtils.isEmpty(erros)) {
                mensagens.add(e.getMessage());
            }
            else {
                for (String erro : erros) {
                    mensagens.add(erro);
                }
            }
        }
    }

    public Integer getCodigo() {
        return codigo;
    }

    public List<String> getMensagens() {
        return mensagens;
    }
}
