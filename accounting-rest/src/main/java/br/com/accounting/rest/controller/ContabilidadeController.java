package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.ContabilidadeBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CodigosVO;
import br.com.accounting.rest.vo.ContabilidadeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.*;

@RestController
@RequestMapping("/contabilidade")
public class ContabilidadeController extends GenericController {
    @Autowired
    private ContabilidadeBusiness business;

    @PostMapping
    @ResponseBody
    public ResponseEntity criar(@RequestBody ContabilidadeVO vo) throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = createDTO(vo);
        List<Long> codigos = business.criar(dto);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(codigosVO);
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody ContabilidadeVO vo) throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = createDTO(vo);
        business.atualizar(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        ContabilidadeDTO dto = createDTO(codigo);
        business.excluir(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private ContabilidadeDTO createDTO(ContabilidadeVO vo) {
        return new ContabilidadeDTO()
                .codigo(vo.codigo())
                .dataLancamento(vo.dataLancamento())
                .dataVencimento(vo.dataVencimento())
                .recorrente(vo.recorrente())
                .grupo(vo.grupo())
                .subGrupo(vo.subGrupo())
                .local(vo.local())
                .descricao(vo.descricao())
                .usouCartao(vo.usouCartao())
                .cartao(vo.cartao())
                .parcelado(vo.parcelado())
                .parcela(vo.parcela())
                .parcelas(vo.parcelas())
                .conta(vo.conta())
                .tipo(vo.tipo())
                .valor(vo.valor())
                .codigoPai(vo.codigoPai());
    }

    private ContabilidadeDTO createDTO(Long codigo) {
        return new ContabilidadeDTO()
                .codigo(valueOf(codigo));
    }
}
