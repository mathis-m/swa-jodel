/*========================================================================*
 *                                                                        *
 * This software is governed by the GPL version 2.                        *
 *                                                                        *
 * Copyright: Joerg Friedrich, University of Applied Sciences Esslingen   *
 *                                                                        *
 * $Id:$
 *                                                                        *
 *========================================================================*/
package de.swa.infrastructure.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.swa.infrastructure.entities.UserEntity;
import org.jboss.logging.Logger;

import java.util.List;


@ApplicationScoped
public class UserDao {

    @Inject
    EntityManager em;

	private static final Logger LOGGER = Logger.getLogger(UserDao.class);
	
	public UserEntity getUser(Integer id) {
		return em.find(UserEntity.class, id);
	}

	public List<UserEntity> getAllUsers() {
		TypedQuery<UserEntity> q = em.createQuery("select c from UserEntity c", UserEntity.class);
		return q.getResultList();
	}

	@Transactional
	public UserEntity addUser(UserEntity user) {
		em.persist(user);
		return user;
	}

    @Transactional
    public UserEntity updateUser(UserEntity user) {
    	if (user.getId() != null) {
    		user = em.merge(user);
    	} else {
        	em.persist(user);
    	}
    	return user;
    }

	@Transactional
	public void deleteUser(Integer id) {
		UserEntity cm = em.find(UserEntity.class, id);
		if (cm != null) {
			em.remove(cm);
		}
	}
	
    @Transactional
    public void deleteAllUsers() {
    	try {

    	    Query del = em.createQuery("DELETE FROM UserEntity WHERE id >= 0");
    	    del.executeUpdate();

    	} catch (SecurityException | IllegalStateException  e) {
    	    e.printStackTrace();
    	}
    }
}
