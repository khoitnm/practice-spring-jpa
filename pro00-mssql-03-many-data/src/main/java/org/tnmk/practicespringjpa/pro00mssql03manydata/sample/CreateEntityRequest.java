package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.Data;

@Data
public class CreateEntityRequest {
  private final String name;
  private final String entityCode;
}
