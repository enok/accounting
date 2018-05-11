package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.service.CartaoBusiness;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.vo.CartaoVO;
import br.com.accounting.rest.vo.CodigosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/cartao")
public class CartaoController extends GenericController {
    @Autowired
    private CartaoBusiness business;

    @PostMapping
    @ResponseBody
    public ResponseEntity<CodigosVO> criar(@RequestBody CartaoVO vo) throws StoreException, BusinessException, GenericException {
        CartaoDTO dto = createDTO(vo);
        List<Long> codigos = business.criar(dto);
        CodigosVO codigosVO = new CodigosVO(codigos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(codigosVO);
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody CartaoVO vo) throws StoreException, BusinessException, GenericException {
        CartaoDTO dto = createDTO(vo);
        business.atualizar(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        CartaoDTO dto = createDTO(codigo);
        business.excluir(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity buscarPorCodigo(@PathVariable Long codigo) throws StoreException, BusinessException, GenericException {
        CartaoDTO dto = business.buscarPorCodigo(codigo);
        CartaoVO vo = createVO(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vo);
    }

    private CartaoDTO createDTO(CartaoVO vo) {
        return new CartaoDTO()
                .codigo(vo.codigo())
                .numero(vo.numero())
                .vencimento(vo.vencimento())
                .diaMelhorCompra(vo.diaMelhorCompra())
                .portador(vo.portador())
                .tipo(vo.tipo())
                .limite(vo.limite());
    }

    private CartaoDTO createDTO(Long codigo) {
        return new CartaoDTO()
                .codigo(valueOf(codigo));
    }

    private CartaoVO createVO(CartaoDTO dto) {
        return new CartaoVO()
                .codigo(dto.codigo())
                .numero(dto.numero())
                .vencimento(dto.vencimento())
                .diaMelhorCompra(dto.diaMelhorCompra())
                .portador(dto.portador())
                .tipo(dto.tipo())
                .limite(dto.limite());
    }
}
