package com.wolf.workflow.column.repository;

import com.wolf.workflow.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Columns,Long> {
}
