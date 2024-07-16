package org.kabbee.usermanagementservice.repository;

import org.kabbee.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
