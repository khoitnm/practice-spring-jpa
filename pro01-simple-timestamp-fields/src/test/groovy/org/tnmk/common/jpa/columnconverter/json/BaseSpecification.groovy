package org.tnmk.common.jpa.columnconverter.json

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.tnmk.common.jpa.embeddedMySQL.EmbeddedDBContextInitializer
import org.tnmk.practicespringjpa.pro01simpletimestampfields.Application
import spock.lang.Specification

@Ignore
@DirtiesContext
@SpringBootTest(classes = Application.class)
@ContextConfiguration(initializers = EmbeddedDBContextInitializer.class)
abstract class BaseSpecification extends Specification {

}