package org.tnmk.common.jpa.columnconverter.json

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired

class FlywayTest extends BaseSpecification_withActualDb {

  @Autowired
  Flyway flyway;

  def 'Can run flyway successfully'(){
    given:
    flyway.clean();

    when:
    flyway.migrate();

    then:
    noExceptionThrown();
  }
}
