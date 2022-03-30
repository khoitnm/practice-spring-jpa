package org.tnmk.practicespringjpa.pro10transactionsimple.sample_bussiness;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
