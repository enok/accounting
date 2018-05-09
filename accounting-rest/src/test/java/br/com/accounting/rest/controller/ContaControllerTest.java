package br.com.accounting.rest.controller;

import br.com.accounting.core.util.Utils;
import br.com.accounting.rest.vo.ContaVO;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.accounting.core.util.Utils.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class ContaControllerTest extends GenericTest {
    @Autowired
    private ContaController controller;

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

        ContaVO dto = getVO();
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
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
    public void criarSemNome() throws Exception {
        ContaVO dto = getVO()
                .nome(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")));
    }

    @Test
    public void criarSemNomeEDescricao() throws Exception {
        ContaVO dto = getVO()
                .nome(null)
                .descricao(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")));
    }

    @Test
    public void criarSemNomeEDescricaoEValorDefault() throws Exception {
        ContaVO dto = getVO()
                .nome(null)
                .descricao(null)
                .valorDefault(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo valorDefault é obrigatório.")));
    }

    @Test
    public void criarSemCampos() throws Exception {
        ContaVO dto = getVO()
                .nome(null)
                .descricao(null)
                .valorDefault(null)
                .cumulativo(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo valorDefault é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo cumulativo é obrigatório.")));
    }

    @Test
    public void criarComValorDefaultIncorreto() throws Exception {
        ContaVO dto = getVO()
                .valorDefault("#500,00");
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"#500,00\"")));
    }

    @Test
    public void criarComRecorrenteIncorreto() throws Exception {
        ContaVO dto = getVO()
                .cumulativo("SN");
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo cumulativo é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void criarDuplicado() throws Exception {
        ContaVO dto = getVO();
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)));

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Conta duplicada.")));
    }

    @Test
    public void criar() throws Exception {
        criarConta();
    }

    @Test
    public void atualizarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContaVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(put("/conta")
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
    public void atualizarSemNome() throws Exception {
        ContaVO dto = getVO()
                .nome(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")));
    }

    @Test
    public void atualizarSemNomeEDescricao() throws Exception {
        ContaVO dto = getVO()
                .nome(null)
                .descricao(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")));
    }

    @Test
    public void atualizarSemNomeEDescricaoEValorDefault() throws Exception {
        ContaVO dto = getVO()
                .nome(null)
                .descricao(null)
                .valorDefault(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo valorDefault é obrigatório.")));
    }

    @Test
    public void atualizarSemCampos() throws Exception {
        ContaVO dto = getVO()
                .nome(null)
                .descricao(null)
                .valorDefault(null)
                .cumulativo(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo valorDefault é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo cumulativo é obrigatório.")));
    }

    @Test
    public void atualizarSemCodigo() throws Exception {
        ContaVO vo = getVO()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo código é obrigatório.")));
    }

    @Test
    public void atualizarSemSaldo() throws Exception {
        ContaVO dto = getVO()
                .codigo("0")
                .saldo(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo saldo é obrigatório.")));
    }

    @Test
    public void atualizarSemDataAtualizacao() throws Exception {
        ContaVO dto = getVO()
                .codigo("0")
                .dataAtualizacao(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataAtualização é obrigatório.")));
    }

    @Test
    public void atualizarComValorDefaultIncorreto() throws Exception {
        criarConta();

        ContaVO dto = getVO()
                .codigo("0")
                .valorDefault("#500,00");
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"#500,00\"")));
    }

    @Test
    public void atualizarComRecorrenteIncorreto() throws Exception {
        ContaVO dto = getVO()
                .cumulativo("SN");
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo cumulativo é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void atualizarProibidoAlterarCodigo() throws Exception {
        criarConta();

        ContaVO vo = getVO()
                .codigo("1");
        String json = gson.toJson(vo);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo código não pode ser alterado.")));
    }

    @Test
    public void atualizar() throws Exception {
        criarConta();

        ContaVO vo = getVO()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    private ContaVO getVO() {
        return new ContaVO()
                .nome("CAROL")
                .descricao("Valores passados para a Carol")
                .valorDefault("500,00")
                .saldo("500,00")
                .cumulativo("S")
                .dataAtualizacao(getStringFromCurrentDate());
    }

    private void criarConta() throws Exception {
        ContaVO dto = getVO();
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
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
}
