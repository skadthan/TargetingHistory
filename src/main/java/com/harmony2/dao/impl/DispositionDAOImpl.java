package com.harmony2.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.harmony2.dao.DispositionRecordDAO;
import com.harmony2.dao.DispositionRecordRepository;
import com.harmony2.dao.EmployeeDAO;
import com.harmony2.model.CustomerIdentifier;
import com.harmony2.model.DispositionRecord;
import com.harmony2.model.Employee;
import com.harmony2.util.CassandraDAOTemplate;

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
    

	@Override
	public DispositionRecord createDispositionRecord(DispositionRecord dispositionRec) {
		// TODO Auto-generated method stub
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

