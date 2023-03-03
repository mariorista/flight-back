package mar.cod.flightback.entities.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mar.cod.flightback.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public Optional<User> findUserByPswAndUsr(String usr, String psw);

    // @Query("SELECT u.role FROM User u WHERE u.id=:id")
    @Query("SELECT u FROM User u WHERE u.id=?1")
    public Optional<User> isAdmin(long id);

    @Query("SELECT u.role FROM User u WHERE u.id=?1")
    public Optional<String> isAdmin2(long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.psw = ?1 WHERE u.id = ?2")
    void setUserPswById(String psw, long userId);
}
