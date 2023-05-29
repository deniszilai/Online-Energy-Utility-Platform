package Persistence.Hbm;

import Domain.Client;
import Domain.ClientDevice;
import Persistence.Generic.ClientDeviceRepo;
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
public class ClientDeviceHBMRepo implements ClientDeviceRepo {

    private SessionFactory sessionFactory;

    public ClientDeviceHBMRepo() {
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
    public ClientDevice Save(ClientDevice clientDevice) {
        initialize();
        ClientDevice added = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(clientDevice);
                added = session.createQuery("from ClientDevice", ClientDevice.class)
                        .getResultList().stream().max(Comparator.comparing(ClientDevice::getId)).get();
                tx.commit();
            } catch (RuntimeException re) {
                if (tx != null)
                    tx.rollback();
                else System.out.println("Error DB Hibernate " + re);
            }

        }
        close();
        return added;
    }

    @Override
    public ClientDevice Remove(Long aLong) {
        ClientDevice found = FindById(aLong);
        if (found == null)
            return null;
        initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.remove(found);
                tx.commit();
            } catch (RuntimeException re) {
                if (tx != null)
                    tx.rollback();
                else System.out.println("Error DB Hibernate " + re);
            }
        }
        close();
        return found;
    }

    @Override
    public ClientDevice Update(Long aLong, ClientDevice newer) {
        return null;
    }

    @Override
    public ClientDevice FindById(Long aLong) {
        initialize();
        ClientDevice searched = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                searched = session.createQuery("from ClientDevice where id=?", ClientDevice.class)
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
    public Iterable<ClientDevice> GetAll() {
        initialize();
        List<ClientDevice> clientDevices = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                clientDevices = session.createQuery("from ClientDevice",ClientDevice.class).getResultList();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.out.println("Error DB Hibernate " + re);
            }
        }
        close();
        return clientDevices;
    }

    @Override
    public Long Count() {
        return null;
    }
}
