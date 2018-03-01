package com.harmony2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmony2.dao.DispositionRecordDAO;
import com.harmony2.model.DispositionRecord;
import com.harmony2.service.DispositionRecordService;
/**
 * Service Impl class for DispositionRecord to perform CRUD operation.
 * @version 1.0
 * @since Feb 02, 2018
 */
@Service
public class DispositionRecordServiceImpl implements DispositionRecordService {

    @Autowired  
    private DispositionRecordDAO dispositionDAO;

    /**
     * Default Constructor
     */
    public DispositionRecordServiceImpl() {
        super();    
    }

    @Override   
    public DispositionRecord createDispositionRecord(DispositionRecord DispositionRecord) {     
        return dispositionDAO.createDispositionRecord(DispositionRecord);
    }

    @Override   
    public List<DispositionRecord> getDispositionRecord(String ssoid) {       
        return dispositionDAO.getDispositionRecord(ssoid);
    }

    @Override   
    public DispositionRecord updateDispositionRecord(DispositionRecord DispositionRecord) {     
        return dispositionDAO.updateDispositionRecord(DispositionRecord);
    }

    @Override   
    public void deleteDispositionRecord(String ssoid) {        
        dispositionDAO.deleteDispositionRecord(ssoid);
    }


}
