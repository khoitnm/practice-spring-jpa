package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class UpdateResult {
    private final List<SampleEntity> successItems = new ArrayList<>();
    private final List<ErrorItemsGroup> errorItemGroups = new ArrayList<>();

    public void addSuccessItems(List<SampleEntity> items) {
        this.successItems.addAll(items);
    }
    public void addErrorItems(List<SampleEntity> items, Exception ex) {
        this.errorItemGroups.add(new ErrorItemsGroup(items, ex));
    }
}
