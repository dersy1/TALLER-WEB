package com.tallerwebi.dominio.repositorio.impl;

import com.tallerwebi.dominio.repositorio.RepositorioPartido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.tallerwebi.dominio.modelo.Partido;
import java.util.List;

@Repository("repositorioPartido")
public class RepositorioPartidoImpl implements RepositorioPartido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidoImpl(SessionFactory sessionFactory){this.sessionFactory = sessionFactory;}

    @Override
    public Partido buscar(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return(Partido) session.createCriteria(Partido.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public Long guardar(Partido partido) {
            return (Long) sessionFactory.getCurrentSession().save(partido);
    }
    @Override
    public void actualizar(Partido partido) {   sessionFactory.getCurrentSession().update(partido);
    }

    @Override
    public List<Partido> listAll() {
        return sessionFactory.getCurrentSession().createCriteria(Partido.class).list();
    }



}
