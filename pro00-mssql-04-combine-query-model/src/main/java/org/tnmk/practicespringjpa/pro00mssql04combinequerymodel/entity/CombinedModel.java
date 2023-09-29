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
    private Long id;

    private String name;

    private Long parentId;

    private String parentName;

    private Instant startingDateTime;
}
