package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.*;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em =  emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "1234"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("닭발");

            member.getAddressHistory().add(new Address("old1", "street", "1234"));
            member.getAddressHistory().add(new Address("old2", "street", "1234"));

            em.persist(member);
            em.flush();
            em.clear();

            System.out.println("==========================");
            Member findMember = em.find(Member.class, member.getId());

            // select
            List<Address> addressHistory = findMember.getAddressHistory();
            for(Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }

            // update
            findMember.getFavoriteFoods().remove("족발");
            findMember.getFavoriteFoods().add("떡볶이");

            //delete
            findMember.getAddressHistory().remove(new Address("old1", "street", "1234"));
            findMember.getAddressHistory().add(new Address("new1", "street", "1234"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("롤백");
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("member = " + member.getUsername());
    }

}

