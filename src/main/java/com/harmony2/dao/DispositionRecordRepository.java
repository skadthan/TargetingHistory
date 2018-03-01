package com.harmony2.dao;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.harmony2.model.DispositionRecord;

@Repository
public interface DispositionRecordRepository extends CassandraRepository<DispositionRecord> {
	@Query("SELECT*FROM customer_disposition WHERE ssoid=?0")
    List<DispositionRecord> findAllBySsoid(String ssoid);
}