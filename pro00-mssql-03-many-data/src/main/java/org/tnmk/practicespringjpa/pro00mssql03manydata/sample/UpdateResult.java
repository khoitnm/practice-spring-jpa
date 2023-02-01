package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class UpdateResult {
    private final List<SampleEntity> successItems = new ArrayList<>();
    private final Map<List<SampleEntity>, Exception> errorItems = new HashMap<>();

    public void addSuccessItems(List<SampleEntity> items) {
        this.successItems.addAll(items);
    }
    public void addErrorItems(List<SampleEntity> items, Exception ex) {
        this.errorItems.put(items, ex);
    }
}
