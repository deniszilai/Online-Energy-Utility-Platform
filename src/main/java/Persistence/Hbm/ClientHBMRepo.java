package Persistence.Hbm;

import Domain.Client;
import Domain.Device;
import Persistence.Generic.ClientRepo;
import Persistence.Generic.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ClientHBMRepo implements ClientRepo {
    private SessionFactory sessionFactory;

    public ClientHBMRepo() {
    }

    private void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Error ocurred " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }

    private void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public Client Save(Client client) {
        initialize();
        Client added = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(client);
                added = session.createQuery("from Client",Client.class)
                        .getResultList().stream().max(Comparator.comparing(Client::getId)).get();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.out.println("Error DB Hibernate " + re);
            }

        }
        close();
        return added;
    }

    @Override
    public Client Remove(Long aLong) {
        Client found = FindById(aLong);
        if(found == null)
            return null;
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.remove(found);
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.out.println("Error DB Hibernate " + re);
            }
        }
        close();
        return found;
    }

    @Override
    public Client Update(Long aLong, Client newer) {
        if (FindById(aLong) == null)
            return newer;
        initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Client older = session.getReference(Client.class, aLong);
                if(!newer.getUsername().equals(""))
                    older.setUsername(newer.getUsername());
                if(!newer.getPassword().equals(""))
                    older.setPassword(newer.getPassword());
                session.merge(older);
                tx.commit();
            } catch (RuntimeException re) {
                if (tx != null)
                    tx.rollback();
                else System.err.println("Error DB Hibernate " + re);
            }
        }
        close();
        return null;
    }

    @Override
    public Client FindById(Long aLong) {
        initialize();
        Client searched = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                searched = session.createQuery("from Client where id=?", Client.class)
                        .setParameter(0, aLong).getSingleResult();
                tx.commit();
            } catch (RuntimeException re) {
                if (tx != null)
                    tx.rollback();
                else System.err.println("Error DB Hibernate " + re);
            }
        }
        close();
        return searched;
    }

    @Override
    public Iterable<Client> GetAll() {
        initialize();
        List<Client> clients = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                clients = session.createQuery("from Client",Client.class).getResultList();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.out.println("Error DB Hibernate " + re);
            }
        }
        close();
        return clients;
    }

    @Override
    public Long Count() {
        return null;
    }
}
