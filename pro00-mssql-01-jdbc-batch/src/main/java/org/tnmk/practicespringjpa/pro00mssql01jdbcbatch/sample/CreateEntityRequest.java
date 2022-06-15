package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample;

import lombok.Data;

@Data
public class CreateEntityRequest {
  private final String name;
  private final String entityCode;
}
