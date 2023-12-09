package web.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

}