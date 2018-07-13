package org.tnmk.practicespringjpa;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.samplebusiness.story.SampleStory;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
//@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
@AutoConfigureTestDatabase(replace = NONE)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@DataJpaTest
@SpringBootTest(classes = {JsonColumnApplication.class})
public class JsonColumnAppTest {
//
//    @Bean
//    public DataSource dataSource(){
//        return EmbeddedDBStarter.embeddedPostgres.getPostgresDatabase();
//    }

    @Autowired
    private SampleStory sampleStory;

    @Test
    public void testStartContext(){
        sampleStory.createSample();
    }
}
