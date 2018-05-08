package br.com.accounting.rest.vo;

import br.com.accounting.business.exception.ValidationException;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ErrorVO {
    private Integer codigo;
    private List<String> mensagens = new ArrayList<>();

    public ErrorVO(Integer codigo, String mensagem) {
        this.codigo = codigo;
        mensagens.add(mensagem);
    }

    public ErrorVO(Integer codigo, Exception e) {
        this.codigo = codigo;
        handlerValidationException(e);
    }

    private void handlerValidationException(Exception e) {
        if (e instanceof ValidationException) {
            ValidationException e1 = (ValidationException) e;
            List<String> erros = e1.getErros();
            if (CollectionUtils.isEmpty(erros)) {
                mensagens.add(getMessage(e.getMessage()));
            }
            else {
                for (String erro : erros) {
                    mensagens.add(getMessage(erro));
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

    private String getMessage(String message) {
        return message.replaceAll("br.com.accounting.core.entity.", "");
    }
}
