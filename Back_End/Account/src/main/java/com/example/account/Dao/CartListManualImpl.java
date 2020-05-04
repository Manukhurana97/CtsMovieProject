package com.example.account.Dao;

import com.example.account.Model.CartItems;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class CartListManualImpl implements CartLIstManualRepositry {

    @PersistenceContext()
    private EntityManager entityManager;




    public CartListManualImpl( EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public CartItems saveandFlush(CartItems cartItems) {
        Session session = entityManager.unwrap(Session.class);

        session.save(cartItems);
        entityManager.close();
        return cartItems;
    }

    @Override
    @Transactional
    public CartItems saveandFlushMerge(CartItems cartItems) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(cartItems);
        entityManager.close();
        return cartItems;
    }

    @Override
    @Transactional
    public void deletebycartid(int cartid) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(cartid);
        entityManager.close();
    }


    @Override
    @Transactional
    public CartItems saveandFlushUpdate(CartItems cartItems) {
        Session session = entityManager.unwrap(Session.class);
        session.update(cartItems);
        entityManager.close();
        return cartItems;
    }
}
