package com.wolf.workflow.column.repository;

import com.wolf.workflow.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Columns, Long> {

  boolean isExistsByColumnsStatus(String columnsStatus);
}
