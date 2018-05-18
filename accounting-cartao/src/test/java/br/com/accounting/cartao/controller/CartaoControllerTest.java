package br.com.accounting.cartao.controller;

import br.com.accounting.cartao.vo.CartaoVO;
import br.com.accounting.commons.test.GenericTest;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class CartaoControllerTest extends GenericTest {
    @Autowired
    private CartaoController controller;

    private MockMvc mvc;
    private Gson gson;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        gson = new Gson();
    }

    @Test
    public void criarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        CartaoVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao salvar.")));
    }

    @Test
    public void criarSemNumero() throws Exception {
        CartaoVO vo = getVO()
                .numero(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimento() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimentoEDiaMelhorCompra() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimentoEDiaMelhorCompraEPortador() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null)
                .portador(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")));
    }

    @Test
    public void criarSemNumeroEVencimentoEDiaMelhorCompraEPortadorETipo() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null)
                .portador(null)
                .tipo(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(5)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo tipo é obrigatório.")));
    }

    @Test
    public void criarSemCampos() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null)
                .portador(null)
                .tipo(null)
                .limite(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo tipo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo limite é obrigatório.")));
    }

    @Test
    public void criarComVencimentoIncorreto() throws Exception {
        CartaoVO vo = getVO()
                .vencimento("27-03/2018");
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.time.format.DateTimeParseException: Text '27-03/2018' could not be parsed at index 2")));
    }

    @Test
    public void criarComDiaMelhorCompraIncorreta() throws Exception {
        CartaoVO vo = getVO()
                .diaMelhorCompra("17-04/2018");
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.time.format.DateTimeParseException: Text '17-04/2018' could not be parsed at index 2")));
    }

    @Test
    public void criarComTipoIncorreto() throws Exception {
        CartaoVO vo = getVO()
                .tipo("FISICO1");
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.lang.IllegalArgumentException: No enum constant TipoCartao.FISICO1")));
    }

    @Test
    public void criarComLimiteIncorreto() throws Exception {
        CartaoVO vo = getVO()
                .limite("a2.000,00");
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"a2.000,00\"")));
    }

    @Test
    public void criarDuplicado() throws Exception {
        CartaoVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)));

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Cartão duplicado.")));
    }

    @Test
    public void criar() throws Exception {
        criarCartao();
    }

    @Test
    public void atualizarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        CartaoVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao atualizar.")));
    }

    @Test
    public void atualizarSemNumero() throws Exception {
        CartaoVO vo = getVO()
                .numero(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")));
    }

    @Test
    public void atualizarSemNumeroEVencimento() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")));
    }

    @Test
    public void atualizarSemNumeroEVencimentoEDiaMelhorCompra() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")));
    }

    @Test
    public void atualizarSemNumeroEVencimentoEDiaMelhorCompraEPortador() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null)
                .portador(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")));
    }

    @Test
    public void atualizarSemNumeroEVencimentoEDiaMelhorCompraEPortadorETipo() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null)
                .portador(null)
                .tipo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(5)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo tipo é obrigatório.")));
    }

    @Test
    public void atualizarSemCampos() throws Exception {
        CartaoVO vo = getVO()
                .numero(null)
                .vencimento(null)
                .diaMelhorCompra(null)
                .portador(null)
                .tipo(null)
                .limite(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(6)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo número é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo vencimento é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo diaMelhorCompra é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo portador é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[4]", is("O campo tipo é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[5]", is("O campo limite é obrigatório.")));
    }

    @Test
    public void atualizarSemCodigo() throws Exception {
        CartaoVO vo = getVO()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizarComVencimentoIncorreto() throws Exception {
        criarCartao();

        CartaoVO vo = getVO()
                .codigo("0")
                .vencimento("27-03/2018");
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.time.format.DateTimeParseException: Text '27-03/2018' could not be parsed at index 2")));
    }

    @Test
    public void atualizarComDiaMelhorCompraIncorreta() throws Exception {
        criarCartao();

        CartaoVO vo = getVO()
                .codigo("0")
                .diaMelhorCompra("17-04/2018");
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.time.format.DateTimeParseException: Text '17-04/2018' could not be parsed at index 2")));
    }

    @Test
    public void atualizarComTipoIncorreto() throws Exception {
        criarCartao();

        CartaoVO vo = getVO()
                .codigo("0")
                .tipo("FISICO1");
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.lang.IllegalArgumentException: No enum constant TipoCartao.FISICO1")));
    }

    @Test
    public void atualizarComLimiteIncorreto() throws Exception {
        criarCartao();

        CartaoVO vo = getVO()
                .codigo("0")
                .limite("a2.000,00");
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"a2.000,00\"")));
    }

    @Test
    public void atualizarProibidoAlterarCodigo() throws Exception {
        criarCartao();

        CartaoVO vo = getVO()
                .codigo("1");
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizar() throws Exception {
        criarCartao();

        CartaoVO vo = getVO()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(put("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        String codigo = "0";

        mvc.perform(delete("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao excluir.")));
    }

    @Test
    public void excluirSemCodigo() throws Exception {
        String codigo = null;

        mvc.perform(delete("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(delete("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirInexistente() throws Exception {
        String codigo = "0";

        mvc.perform(delete("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void excluir() throws Exception {
        criarCartao();

        String codigo = "0";

        mvc.perform(delete("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        String codigo = "0";

        mvc.perform(get("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao buscar por código.")));
    }

    @Test
    public void buscarPorCodigoSemCodigo() throws Exception {
        String codigo = null;

        mvc.perform(get("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(get("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoInexistente() throws Exception {
        String codigo = "0";

        mvc.perform(get("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void buscarPorCodigo() throws Exception {
        criarCartao();

        String codigo = "0";

        mvc.perform(get("/cartao/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("0")))
                .andExpect(jsonPath("$.numero", is("7660")))
                .andExpect(jsonPath("$.vencimento", is("27/03/2018")))
                .andExpect(jsonPath("$.diaMelhorCompra", is("17/04/2018")))
                .andExpect(jsonPath("$.portador", is("Enok")))
                .andExpect(jsonPath("$.tipo", is("FISICO")))
                .andExpect(jsonPath("$.limite", is("2.000,00")));
    }

    @Test
    public void buscarTudoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        mvc.perform(get("/cartao")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao buscar tudo.")));
    }

    @Test
    public void buscarTudoInexistente() throws Exception {
        mvc.perform(get("/cartao")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarTudo() throws Exception {
        criarCartao();

        mvc.perform(get("/cartao")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].codigo", is("0")))
                .andExpect(jsonPath("$[0].numero", is("7660")))
                .andExpect(jsonPath("$[0].vencimento", is("27/03/2018")))
                .andExpect(jsonPath("$[0].diaMelhorCompra", is("17/04/2018")))
                .andExpect(jsonPath("$[0].portador", is("Enok")))
                .andExpect(jsonPath("$[0].tipo", is("FISICO")))
                .andExpect(jsonPath("$[0].limite", is("2.000,00")));
    }

    private void criarCartao() throws Exception {
        CartaoVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/cartao")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)));
    }

    private CartaoVO getVO() {
        return new CartaoVO()
                .numero("7660")
                .vencimento("27/03/2018")
                .diaMelhorCompra("17/04/2018")
                .portador("Enok")
                .tipo("FISICO")
                .limite("2.000,00");
    }
}
