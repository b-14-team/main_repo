package com.wolf.workflow.column.adapter;

import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.column.repository.ColumnRepository;
import com.wolf.workflow.common.exception.DuplicatedColumnException;
import com.wolf.workflow.common.exception.NotFoundColumnException;
import com.wolf.workflow.common.util.MessageUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColumnAdapter {

    private final ColumnRepository columnRepository;

    public Columns createColumn(Columns columns) {
        if (columnRepository. existsByColumnsStatus(columns.getColumnsStatus())) {
            throw new DuplicatedColumnException(MessageUtil.getMessage("already.exist.column"));
        }
        return columnRepository.save(columns);
    }

    public List<Columns> getAllColumns() {
        return columnRepository.findAll();
    }

    public Columns findColumnsById(Long columnId) {
        return columnRepository.findById(columnId)
            .orElseThrow(() -> new NotFoundColumnException(MessageUtil.getMessage("already.exist.column")));
    }

    public void deleteColumn(Columns columns) {
        columnRepository.delete(columns);
    }

    public void updateColumn(Columns columns) {
        columnRepository.save(columns);
    }

}
