package io.wulfcodes.library.persistence.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.wulfcodes.library.model.po.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
