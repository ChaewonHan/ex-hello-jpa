package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        /*EntityManagerFactory는 하나만 생성해서 애플리케이션 전체에서 공유*/
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /*EntityManager는 쓰레드간에 공유X (사용하고 버려야 한다).
        * DB Connection 같은 거*/
        EntityManager em =  emf.createEntityManager();

        /* JPA의 모든 데이터 변경은 트랜잭션 안에서 실행*/
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜젝션 시작

        try {
            Member member1 = new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member1.setUsername("B");

            Member member3 = new Member();
            member1.setUsername("C");

            System.out.println("==================");

            em.persist(member1); // 1, 51번까지 시퀀스 호출
            em.persist(member2); // 메모리에서 호출
            em.persist(member3); // 메모리에서 호출

            System.out.println("member1 = " + member1.getId());
            System.out.println("member2 = " + member2.getId());
            System.out.println("member3 = " + member3.getId());

            System.out.println("==================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("롤백");
        } finally {
            em.close();
        }

        emf.close();





    }
}

