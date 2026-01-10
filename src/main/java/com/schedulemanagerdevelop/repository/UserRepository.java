package com.schedulemanagerdevelop.repository;

import com.schedulemanagerdevelop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
