package com.harmony2.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.harmony2.model.CustomerIdentifier;
import com.harmony2.model.DispositionRecord;
import com.harmony2.service.DispositionRecordService;


/**
 * @author Suresh Kadthan
 * @version 1.0
 * @since Feb 02, 2018
 */
@RestController
public class DispositionController {
    
    @Autowired
    private DispositionRecordService DispositionService;
    
    public DispositionController() {
        System.out.println("EmployeeController()");
    }
         
    @RequestMapping(value = "/postdisposition", method = RequestMethod.POST)    
    DispositionRecord create(@RequestBody DispositionRecord dispositionRec) {        
        return DispositionService.createDispositionRecord(dispositionRec);
    }
 
    @RequestMapping(value = "/deldisposition", method = RequestMethod.POST)
    void delete(@RequestBody CustomerIdentifier customerId) {
        DispositionService.deleteDispositionRecord(customerId.getSsoid());
    }
 
    @RequestMapping(value = "/getdispositiondata", method = RequestMethod.POST)
    List<DispositionRecord> findById(@RequestBody DispositionRecord dispositionRec) {        
        return DispositionService.getDispositionRecord(dispositionRec.getSsoid());
    }
 
    @RequestMapping(value = "/updatedisposition", method = RequestMethod.PUT)
    DispositionRecord update(@RequestBody DispositionRecord dispositionRec) {
        return DispositionService.updateDispositionRecord(dispositionRec);
    }
}