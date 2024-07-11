package com.wolf.workflow.column.adapter;

import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.column.repository.ColumnRepository;
import com.wolf.workflow.common.exception.NotFoundColumnException;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColumnAdapter {

    private final ColumnRepository columnRepository;

    public Columns findColumnsById(Long columnId) {
        return columnRepository.findById(columnId).orElseThrow(()->
                new NotFoundColumnException(MessageUtil.getMessage("not.find.column"))
                );
    }
}
