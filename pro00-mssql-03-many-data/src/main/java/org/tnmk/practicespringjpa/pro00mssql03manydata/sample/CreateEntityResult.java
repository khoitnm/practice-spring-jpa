package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.Data;

@Data
public class CreateEntityResult {
  private final CreateEntityRequest request;
  private final long id;
}
