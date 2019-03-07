package org.tnmk.practicespringjpa.pro02simpleddlgenerator.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro02simpleddlgenerator.sample.entity.SampleEntityWithUrl;

import java.util.List;

@Repository
public class SampleJdbcRepostiory {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SampleEntityWithUrl> findSampleEntitiesWithUrl(){
        List<SampleEntityWithUrl>  sampleEntityWithUrls = jdbcTemplate.query("select entity.*, concat('http://somedomain/', entity.name) as url from sample_entity entity",
            new BeanPropertyRowMapper<>(SampleEntityWithUrl.class)
        );
        return sampleEntityWithUrls;
    }
}
