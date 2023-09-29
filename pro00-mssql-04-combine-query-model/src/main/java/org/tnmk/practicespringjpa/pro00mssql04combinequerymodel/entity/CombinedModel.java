package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;


 @AllArgsConstructor
 @NoArgsConstructor
@Getter
@Setter
@ToString
public class CombinedModel {
    @Value("#{target.id}")
    private Long id;

    @Value("#{target.name}")
    private String name;

    @Value("#{target.parent.id}")
    private Long parentId;

    @Value("#{target.parent.name}")
    private String parentName;

    @Value("#{target.startingDateTime}")
    private Instant startingDateTime;
}
