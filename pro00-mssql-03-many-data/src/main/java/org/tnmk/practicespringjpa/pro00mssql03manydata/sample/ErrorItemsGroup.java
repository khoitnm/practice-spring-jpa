package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.Data;

import java.util.List;

@Data
public class ErrorItemsGroup {
    private final List<SampleEntity> items;
    private final Exception ex;
}
