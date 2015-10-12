package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.administrative.Role;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface UserRepository extends JpaRepository<User,Long>{
    Page<User> findByUserNameContains(String name, Pageable pager);
    List<User> findByUserNameContains(String name);
    //@Query("select r from User u join u.userRoles r where u.userID=:userID")
    @Query("select r from Role r,User u where u.userID=:userID and (r MEMBER u.userRoles)")
    Page<Role> getUserRoles(@Param("userID")long userId, Pageable pager);
    @Query("select r from User u join u.userRoles r where u.userID=:userID")
    List<Role> getUserRoles(@Param("userID")long userId);
    @Query("select r from User u,Role r where u.userID=:userID and (r NOT MEMBER u.userRoles)")
    List<Role> getAvaibleUserRoles(@Param("userID")long userId);
}
