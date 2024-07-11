package com.wolf.workflow.column.adapter;

import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.column.repository.ColumnRepository;
import com.wolf.workflow.common.exception.NotFoundColumnException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ColumnAdapter {
    private final MessageSource messageSource;

    private final ColumnRepository columnRepository;

    public Columns findColumnsById(Long columnId) {
        return columnRepository.findById(columnId).orElseThrow(()->
                new NotFoundColumnException(messageSource.getMessage("not.find.column",null, Locale.getDefault()))
                );
    }
}
