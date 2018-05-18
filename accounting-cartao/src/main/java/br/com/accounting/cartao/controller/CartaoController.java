package br.com.accounting.cartao.controller;

import br.com.accounting.cartao.business.CartaoBusiness;
import br.com.accounting.cartao.dto.CartaoDTO;
import br.com.accounting.cartao.vo.CartaoVO;
import br.com.accounting.commons.exception.AbstractExceptionHandler;
import br.com.accounting.commons.exception.BusinessException;
import br.com.accounting.commons.exception.GenericException;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.vo.CodigosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/cartao")
public class CartaoController extends AbstractExceptionHandler {
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

    @GetMapping
    public ResponseEntity buscarTudo() throws StoreException, GenericException {
        List<CartaoDTO> dtos = business.buscarTodas();
        List<CartaoVO> vos = createVOList(dtos);
        if (CollectionUtils.isEmpty(vos)) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(vos);
        }
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

    private List<CartaoVO> createVOList(List<CartaoDTO> dtos) {
        List<CartaoVO> vos = new ArrayList<>();
        for (CartaoDTO dto : dtos) {
            vos.add(createVO(dto));
        }
        return vos;
    }
}
