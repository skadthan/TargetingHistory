package com.harmony2.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.harmony2.model.DispositionRecord;

@Repository
public interface DispositionRecordRepository extends CassandraRepository<DispositionRecord> {
	@Query("SELECT*FROM customer_disposition WHERE ssoid=?0")
    List<DispositionRecord> findAllBySsoid(String ssoid);
	@Query("update customer_offer_count_by_date set dispositioncount = dispositioncount+1 where ssoid=?0 and offername=?1 and dispositiontype=?2 and dispositiondate=toDate(?3)")
	void insert_customer_offer_count_by_date(String ssoid, String offername, String dispositiontype, UUID uuid );
}