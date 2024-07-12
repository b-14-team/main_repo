package com.wolf.workflow.column.repository;

import com.wolf.workflow.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Columns, Long> {

//  이부분 레포지토리에서 못찾아서 주석 해 놓았습니다!!
//  boolean isExistsByColumnsStatus(String columnsStatus);

  boolean existsByColumnsStatus(String columnsStatus);
}
