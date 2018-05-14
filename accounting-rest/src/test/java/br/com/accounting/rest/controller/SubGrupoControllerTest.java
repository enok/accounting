package br.com.accounting.rest.controller;

import br.com.accounting.rest.vo.SubGrupoVO;
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
public class SubGrupoControllerTest extends GenericTest {
    @Autowired
    private SubGrupoController controller;

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

        SubGrupoVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/subgrupo")
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
        SubGrupoVO vo = getVO()
                .nome(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/subgrupo")
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
        SubGrupoVO vo = getVO()
                .nome(null)
                .descricao(null);
        String json = gson.toJson(vo);

        mvc.perform(post("/subgrupo")
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
    public void criarDuplicado() throws Exception {
        SubGrupoVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/subgrupo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)));

        mvc.perform(post("/subgrupo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("SubGrupo duplicado.")));
    }

    @Test
    public void criar() throws Exception {
        criarSubGrupo();
    }

    @Test
    public void atualizarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        SubGrupoVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(put("/subgrupo")
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
        SubGrupoVO vo = getVO()
                .nome(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/subgrupo")
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
        SubGrupoVO vo = getVO()
                .nome(null)
                .descricao(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/subgrupo")
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
    public void atualizarSemCodigo() throws Exception {
        SubGrupoVO vo = getVO()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/subgrupo")
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
    public void atualizarProibidoAlterarCodigo() throws Exception {
        criarSubGrupo();

        SubGrupoVO vo = getVO()
                .codigo("1");
        String json = gson.toJson(vo);

        mvc.perform(put("/subgrupo")
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
        criarSubGrupo();

        SubGrupoVO vo = getVO()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(put("/subgrupo")
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

        mvc.perform(delete("/subgrupo/{codigo}", codigo)
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

        mvc.perform(delete("/subgrupo/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(delete("/subgrupo/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirInexistente() throws Exception {
        String codigo = "0";

        mvc.perform(delete("/subgrupo/{codigo}", codigo)
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
        criarSubGrupo();

        String codigo = "0";

        mvc.perform(delete("/subgrupo/{codigo}", codigo)
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

        mvc.perform(get("/subgrupo/{codigo}", codigo)
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

        mvc.perform(get("/subgrupo/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(get("/subgrupo/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoInexistente() throws Exception {
        String codigo = "0";

        mvc.perform(get("/subgrupo/{codigo}", codigo)
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
        criarSubGrupo();

        String codigo = "0";

        mvc.perform(get("/subgrupo/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("0")))
                .andExpect(jsonPath("$.nome", is("Suplementos")))
                .andExpect(jsonPath("$.descricao", is("Sumplementos comprados")));
    }

    @Test
    public void buscarTudoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        mvc.perform(get("/subgrupo")
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
        mvc.perform(get("/subgrupo")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarTudo() throws Exception {
        criarSubGrupo();

        mvc.perform(get("/subgrupo")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].codigo", is("0")))
                .andExpect(jsonPath("$[0].nome", is("Suplementos")))
                .andExpect(jsonPath("$[0].descricao", is("Sumplementos comprados")));
    }

    private SubGrupoVO getVO() {
        return new SubGrupoVO()
                .nome("Suplementos")
                .descricao("Sumplementos comprados");
    }

    private void criarSubGrupo() throws Exception {
        SubGrupoVO vo = getVO();
        String json = gson.toJson(vo);

        mvc.perform(post("/subgrupo")
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
