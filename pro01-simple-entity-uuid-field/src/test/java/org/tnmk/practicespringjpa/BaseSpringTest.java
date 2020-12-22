package org.tnmk.practicespringjpa;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.common.embeddeddb.EmbeddedDBContextInitializer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Pro01SimpleEntityUuidFieldApplication.class})
@ContextConfiguration(initializers = EmbeddedDBContextInitializer.class)
@Ignore
public abstract class BaseSpringTest {
}
