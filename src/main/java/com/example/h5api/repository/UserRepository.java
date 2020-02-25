package com.example.h5api.repository;

import com.example.h5api.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserApp, Integer> {
    @Query(value = "SELECT user FROM UserApp as user WHERE user.deleteAt is NULL")
    List<UserApp> findAllNotDeleted();

    @Query(value = "SELECT  * FROM USER_APP as user INNER JOIN Nomination as  nom ON nom.user_id=user.ID where nom.VALUE_ID =?1", nativeQuery = true)
    List<UserApp> findUserNameAndId(Integer valueid);

    @Query(value = "select * from USER_APP as user inner join (SELECT  nom.NOMINATOR_ID FROM NOMINATION as nom INNER JOIN USER_APP as  user ON nom.user_id=user.ID where nom.VALUE_ID =?1 and user.ID =?2) as nomid on user.ID= nomid.NOMINATOR_ID", nativeQuery = true)
    List<UserApp> findUserNameAndIdForNominator(Integer valueId, Integer userId);

    UserApp findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT user FROM UserApp as user WHERE user.deleteAt is NULL AND user.status = TRUE")
    List<UserApp> findAllAvailable();

}
