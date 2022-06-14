package org.tnmk.common.jpa.columnconverter.json

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.tnmk.practicespringjpa.pro04flyway.FlywayApplication
import spock.lang.Specification

@Ignore
@DirtiesContext
@SpringBootTest(classes = FlywayApplication.class)
abstract class BaseSpecification_withActualDb extends Specification {

}