package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.sample;

import lombok.Data;

@Data
public class CreateEntityResult {
  private final CreateEntityRequest request;
  private final long id;
}
