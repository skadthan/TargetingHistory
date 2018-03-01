package com.harmony2.model;

import java.util.UUID;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;
import com.datastax.driver.core.utils.UUIDs;

/**
 * Disposition entity class.
 * @version 1.0
 * @since Feb 02, 2018
 */
@Table("customer_disposition")
public class DispositionRecord {
    
    public String getSsoid() {
		return ssoid;
	}

	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
	}

	public String getOffername() {
		return offername;
	}

	public void setOffername(String offername) {
		this.offername = offername;
	}

	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getDisptype() {
		return disptype;
	}

	public void setDisptype(String disptype) {
		this.disptype = disptype;
	}

	public UUID getRecordtime() {
		return recordtime;
	}

	public void setRecordtime(UUID timeBasedUuid) {
		this.recordtime = timeBasedUuid;
	}

	@PrimaryKeyColumn(name = "ssoid", ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private String ssoid;    
    
	@PrimaryKeyColumn(name = "offername", ordinal = 1,type = PrimaryKeyType.CLUSTERED)
    private String offername;    
    
    @Column("placement")
    private String placement;   
    
    @Column(value ="layout")
    private String layout;
    
    public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Column(value ="style")
    private String style;
	
	@PrimaryKeyColumn(name = "dispositiontype", ordinal = 2,type = PrimaryKeyType.CLUSTERED)
     private String disptype;
    
	@PrimaryKeyColumn(name = "recordtime", ordinal = 3,type = PrimaryKeyType.CLUSTERED)
    private UUID recordtime;
    
    /**
     * Default Constructor
     */
    public DispositionRecord() {
        super();    
        UUID timeBasedUuid = UUIDs.timeBased();
        this.recordtime=timeBasedUuid;
    }
    
    @Override
    public String toString() {
        return "DispositionRecord [ssoid=" + ssoid + ", offername=" + offername + ", placement=" + placement
                + ", style=" + style + ", dispositiontype=" + disptype +", recordtime=" + recordtime + "]";
    }  

}
