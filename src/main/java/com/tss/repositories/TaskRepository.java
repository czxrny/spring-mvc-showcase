package com.tss.repositories;

import org.springframework.data.repository.CrudRepository;
import com.tss.entities.Task;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByOrderByNameAsc();
}
