package com.tallerwebi.Repositorios;
import com.tallerwebi.dominio.modelo.Partido;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioPartidaTest  {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void sePuedeActualizarUnPartido() {
        Partido partido = new Partido();
        partido.setId(1L);
        partido.setPuntosPc(17);
        assertThat(partido.getPuntosPc(), equalTo(17));
        partido.setPuntosPc(19);
        sessionFactory.getCurrentSession().update(partido);
        assertThat(partido.getPuntosPc(), equalTo(19));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnPartido() {
        Partido partido = new Partido();
        partido.setId(1L);
        Long idGuardado = (Long)sessionFactory.getCurrentSession().save(partido);
        assertThat(idGuardado, equalTo(1L));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnPartido() {
        Partido partido = new Partido();
        partido.setId(1L);
        sessionFactory.getCurrentSession().save(partido);
        Partido partido2 = (Partido) sessionFactory.getCurrentSession().createCriteria(Partido.class).add(Restrictions.eq("id", partido.getId())).uniqueResult();
        assertThat(partido2, notNullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaListarTodos() {
        Partido partido = new Partido();
        partido.setId(1L);
        sessionFactory.getCurrentSession().save(partido);
        List partidos = sessionFactory.getCurrentSession().createCriteria(Partido.class).list();
        assertThat(partidos.size(), equalTo(1));
    }

}

