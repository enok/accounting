package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import br.com.accounting.core.entity.GrupoEntity;
import br.com.accounting.core.entity.GrupoEntityFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GrupoServiceTest {

    @Autowired
    private GrupoService grupoService;

    @Test
    public void salvarGrupo() {
        GrupoEntity grupoEntity = GrupoEntityFactory.create();
        assertThat(grupoEntity, notNullValue());
        assertThat(grupoEntity.getDescricao(), equalTo("RENDA"));

        Long codigoCategoria = grupoService.salvarGrupo(grupoEntity);
        assertThat(codigoCategoria, equalTo(1L));
        assertThat(grupoEntity.getCodigo(), equalTo(1L));
    }
}
