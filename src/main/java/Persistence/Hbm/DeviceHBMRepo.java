package Persistence.Hbm;

import Domain.Client;
import Domain.Device;
import Persistence.Generic.DeviceRepo;
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
public class DeviceHBMRepo implements DeviceRepo {
    private SessionFactory sessionFactory;

    public DeviceHBMRepo() {
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
    public Device Save(Device device) {
        initialize();
        Device added = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(device);
                added = session.createQuery("from Device",Device.class)
                        .getResultList().stream().max(Comparator.comparing(Device::getId)).get();
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
    public Device Remove(Long aLong) {
        Device found = FindById(aLong);
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
    public Device Update(Long aLong, Device newer) {
        if (FindById(aLong) == null)
            return newer;
        initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Device older = session.getReference(Device.class, aLong);
                if(!newer.getDescription().equals(""))
                    older.setDescription(newer.getDescription());
                if(!newer.getAddress().equals(""))
                    older.setAddress(newer.getAddress());
                if(newer.getMaxHConsumption()!= -1)
                    older.setMaxHConsumption(newer.getMaxHConsumption());
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
    public Device FindById(Long aLong) {
        initialize();
        Device searched = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                searched = session.createQuery("from Device where id=?", Device.class)
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
    public Iterable<Device> GetAll() {
        initialize();
        List<Device> devices = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                devices = session.createQuery("from Device",Device.class).getResultList();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.out.println("Error DB Hibernate " + re);
            }
        }
        close();
        return devices;
    }

    @Override
    public Long Count() {
        return null;
    }
}
