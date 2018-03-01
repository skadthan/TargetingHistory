package com.harmony2.service;

import java.util.List;

import com.harmony2.model.DispositionRecord;

/**
 * Service interface for DispositionRecord to perform CRUD operation.
 * @version 1.0
 * @since Feb 02, 2018
 */
public interface DispositionRecordService {
    /**
     * Used to Create the DispositionRecord Information
     * @param DispositionRecord
     * @return {@link DispositionRecord}
     */
    public DispositionRecord createDispositionRecord(DispositionRecord DispositionRecord);
    
    /**
     * Getting the DispositionRecord Information using Id
     * @param id
     * @return {@link DispositionRecord}
     */
    public List<DispositionRecord> getDispositionRecord(String ssoid);
    
    /**
     * Used to Update the DispositionRecord Information
     * @param DispositionRecord
     * @return {@link DispositionRecord}
     */
    
    public DispositionRecord updateDispositionRecord(DispositionRecord DispositionRecord);
    
    /**
     * Deleting the DispositionRecord Information using Id
     * @param id
     */
    public void deleteDispositionRecord(String ssoid);
    
    /**
     * Getting the All DispositionRecords information
     * @return
     */
    //public List<DispositionRecord> getAllDispositionRecords();
}
