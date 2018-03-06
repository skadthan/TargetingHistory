package com.harmony2.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.dse.DseSession;
import com.harmony2.dao.DispositionRecordDAO;
import com.harmony2.dao.DispositionRecordRepository;
import com.harmony2.dao.EmployeeDAO;
import com.harmony2.model.CustomerIdentifier;
import com.harmony2.model.DispositionRecord;
import com.harmony2.model.Employee;
import com.harmony2.util.CassandraDAOTemplate;
import com.harmony2.util.CassandraUtil;

/**
 * DAOImpl class for Employee to perform CRUD operation.
 * @version 1.0
 * @since Feb 02, 2018
 */
@Repository
public class DispositionDAOImpl implements DispositionRecordDAO {
    
    @Autowired
    private CassandraDAOTemplate cassandraDAOTemplate;
    
    @Autowired
    private DispositionRecordRepository dispositionRecordRepository;
    
    @Autowired
    private CassandraUtil cassandraUtil;
    

	@Override
	public DispositionRecord createDispositionRecord(DispositionRecord dispositionRec) {
		// TODO Auto-generated method stub
		//cassandraUtil.testClusterConnection(); //Takes lot of time to run
		Session session = cassandraUtil.getPoolSession();
		Row row=session.execute("select release_version from system.local").one();
		System.out.println("release_version--> "+row.getString("release_version"));
		dispositionRecordRepository.insert_customer_offer_count_by_date(dispositionRec.getSsoid(), dispositionRec.getOffername(), dispositionRec.getDisptype(), dispositionRec.getRecordtime());
		return cassandraDAOTemplate.create(dispositionRec);
	}
   

	@Override
	public DispositionRecord updateDispositionRecord(DispositionRecord dispositionRec) {
		// TODO Auto-generated method stub
		 return cassandraDAOTemplate.update(dispositionRec, DispositionRecord.class);
	}


	@Override
	public List<DispositionRecord> getDispositionRecord(String ssoid) {
		// TODO Auto-generated method stub
		//return cassandraDAOTemplate.findById(ssoid,DispositionRecord.class);
		return dispositionRecordRepository.findAllBySsoid(ssoid);
	}


	@Override
	public void deleteDispositionRecord(String ssoid) {
		cassandraDAOTemplate.deleteById(ssoid, DispositionRecord.class);
		
	}

}

