package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.CartaoDTO;
import br.com.accounting.business.exception.ValidationException;
import br.com.accounting.business.service.CartaoBusiness;
import br.com.accounting.core.exception.StoreException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CartaoController.class)
public class CartaoControllerTest extends GenericTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CartaoBusiness cartaoBusiness;

    @Test
    public void criarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        CartaoDTO dto = getDTO();
        String json = mapper.writeValueAsString(dto);

        given(cartaoBusiness.criar(dto))
                .willThrow(new StoreException("Não foi possível buscar os registros."));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens[0]", is("Não foi possível buscar os registros.")));
    }

    @Test
    public void criarSemNumero() throws Exception {
        CartaoDTO dto = getDTO()
                .numero(null);
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "O campo número é obrigatório.");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimento() throws Exception {
        CartaoDTO dto = getDTO()
                .numero(null)
                .vencimento(null);
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "O campo número é obrigatório.",
                "O campo vencimento é obrigatório.");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimentoEDiaMelhorCompra() throws Exception {
        CartaoDTO dto = getDTO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null);
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "O campo número é obrigatório.",
                "O campo vencimento é obrigatório.",
                "O campo diaMelhorCompra é obrigatório.");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimentoEDiaMelhorCompraEPortador() throws Exception {
        CartaoDTO dto = getDTO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null);
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "O campo número é obrigatório.",
                "O campo vencimento é obrigatório.",
                "O campo diaMelhorCompra é obrigatório.",
                "O campo portador é obrigatório.");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimentoEDiaMelhorCompraEPortadorETipo() throws Exception {
        CartaoDTO dto = getDTO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null)
                .tipo(null);
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "O campo número é obrigatório.",
                "O campo vencimento é obrigatório.",
                "O campo diaMelhorCompra é obrigatório.",
                "O campo portador é obrigatório.",
                "O campo tipo é obrigatório.");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo tipo é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimentoEDiaMelhorCompraEPortadorETipoELimite() throws Exception {
        CartaoDTO dto = getDTO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null)
                .tipo(null)
                .limite(null);
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "O campo número é obrigatório.",
                "O campo vencimento é obrigatório.",
                "O campo diaMelhorCompra é obrigatório.",
                "O campo portador é obrigatório.",
                "O campo tipo é obrigatório.",
                "O campo limite é obrigatório.");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo tipo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo limite é obrigatório.")));
    }

    @Test
    public void criarComVencimentoIncorreto() throws Exception {
        CartaoDTO dto = getDTO()
                .vencimento("27-03/2018");
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "java.time.format.DateTimeParseException: Text '27-03/2018' could not be parsed at index 2");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.time.format.DateTimeParseException: Text '27-03/2018' could not be parsed at index 2")));
    }

    @Test
    public void criarComDiaMelhorCompraIncorreta() throws Exception {
        CartaoDTO dto = getDTO()
                .diaMelhorCompra("17-04/2018");
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "java.time.format.DateTimeParseException: Text '17-04/2018' could not be parsed at index 2");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.time.format.DateTimeParseException: Text '17-04/2018' could not be parsed at index 2")));
    }

    @Test
    public void criarComTipoIncorreto() throws Exception {
        CartaoDTO dto = getDTO()
                .tipo("FISICO1");
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "java.lang.IllegalArgumentException: No enum constant br.com.accounting.core.entity.TipoCartao.FISICO1");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.lang.IllegalArgumentException: No enum constant br.com.accounting.core.entity.TipoCartao.FISICO1")));
    }

    @Test
    public void criarComLimiteIncorreto() throws Exception {
        CartaoDTO dto = getDTO()
                .limite("a2,000.00");
        String json = mapper.writeValueAsString(dto);

        List<String> mensagens = Arrays.asList(
                "java.text.ParseException: Unparseable number: \"a2.000,00\"");
        given(cartaoBusiness.criar(dto))
                .willThrow(new ValidationException(mensagens));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"a2.000,00\"")));
    }

    @Test
    public void criar() throws Exception {
        CartaoDTO dto = getDTO();
        String json = mapper.writeValueAsString(dto);

        List<Long> codigos = Arrays.asList(0L);

        given(cartaoBusiness.criar(dto))
                .willReturn(codigos);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)))
                .andDo(print());
    }

    private CartaoDTO getDTO() {
        return new CartaoDTO()
                .numero("7660")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .tipo("FISICO")
                .limite("2.000,00");
    }
}
