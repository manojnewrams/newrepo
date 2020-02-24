package com.example.h5api.repository;

import com.example.h5api.entity.UserApp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserApp, Integer> {
    @Query(value = "SELECT user FROM UserApp as user WHERE user.deleteAt is NULL")
    public List<UserApp> findAllNotDeleted();

    @Query(value = "SELECT  * FROM USER_APP as user INNER JOIN Nomination as  nom ON nom.user_id=user.ID where nom.VALUE_ID =?1",nativeQuery = true)
    public List<UserApp> findUserNameAndId(Integer valueid);

    @Query(value = "select * from USER_APP as user inner join (SELECT  nom.NOMINATOR_ID FROM NOMINATION as nom INNER JOIN USER_APP as  user ON nom.user_id=user.ID where nom.VALUE_ID =?1 and user.ID =?2) as nomid on user.ID= nomid.NOMINATOR_ID",nativeQuery = true)
    public List<UserApp> findUserNameAndIdforNominator(Integer valueid,Integer userid);

    UserApp findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT MAX(user.id) as LastId FROM UserApp as user")
    Integer getLastId();

    @Query(value = "SELECT user FROM UserApp as user WHERE user.deleteAt is NULL AND user.status = TRUE")
    List<UserApp> findAllAvailable();

}
