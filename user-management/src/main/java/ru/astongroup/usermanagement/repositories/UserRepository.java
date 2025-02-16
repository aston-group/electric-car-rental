package ru.astongroup.usermanagement.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.astongroup.usermanagement.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    public Optional<UserModel> findByEmail(String email);

    //Требуется для реализации UserDetails
    //из комплекта Spring.Security
    public UserModel findByUsername(String email);
}
