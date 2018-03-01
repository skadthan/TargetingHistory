package com.harmony2.dao;

import java.util.List;

//import java.util.List;

import com.harmony2.model.DispositionRecord;

/**
 * DAO interface for Employee to perform CRUD operation.
 * @version 1.0
 * @since Feb 02, 2018
 */
public interface DispositionRecordDAO {
    /**
     * Used to Create the DispositionRecord Information
     * @param dispositionRec
     * @return {@link DispositionRecord}
     */
    public DispositionRecord createDispositionRecord(DispositionRecord dispositionRec);
    
    /**
     * Getting the DispositionRecord Information using Id
     * @param id
     * @return {@link DispositionRecord}
     */
    public List<DispositionRecord> getDispositionRecord(String ssoid);
    
    /**
     * Used to Update the DispositionRecord Information
     * @param dispositionRec
     * @return {@link DispositionRecord}
     */
    
    public DispositionRecord updateDispositionRecord(DispositionRecord dispositionRec);
    
    /**
     * Deleting the DispositionRecord Information using Id
     * @param id
     */
    public void deleteDispositionRecord(String ssoid);
    
    /**
     * Getting the All DispositionRecord information
     * @return
     */
   // public List<DispositionRecord> getAllDispositionRecords();
}