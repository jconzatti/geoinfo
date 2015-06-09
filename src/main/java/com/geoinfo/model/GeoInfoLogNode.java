package com.geoinfo.model;

import com.geoinfo.util.EGeoInfoLogType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GeoInfoLogNode {
    private Date dtLog;
    private EGeoInfoLogType idLog;
    private String dsMensagem;
    private Long cdLog;

    public GeoInfoLogNode(Date dtLog, EGeoInfoLogType idLog, String dsMensagem, Long cdLog) {
        this.dtLog = dtLog;
        this.idLog = idLog;
        this.dsMensagem = dsMensagem;
        this.cdLog = cdLog;
    }

    public GeoInfoLogNode(EGeoInfoLogType idLog, String dsMensagem, Long cdLog) {
        this(Calendar.getInstance().getTime(), idLog, dsMensagem, cdLog);
    }

    public GeoInfoLogNode(EGeoInfoLogType idLog, String dsMensagem) {
        this(idLog, dsMensagem, new Long(0));
    }

    public Date getDtLog() {
        return dtLog;
    }

    public void setDtLog(Date dtLog) {
        this.dtLog = dtLog;
    }
    
    public String getDtLogFormated(){
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(this.dtLog);
    }
    
    public EGeoInfoLogType getIdLog() {
        return idLog;
    }

    public void setIdLog(EGeoInfoLogType idLog) {
        this.idLog = idLog;
    }

    public String getDsMensagem() {
        return dsMensagem;
    }

    public void setDsMensagem(String dsMensagem) {
        this.dsMensagem = dsMensagem;
    }

    public Long getCdLog() {
        return cdLog;
    }

    public void setCdLog(Long cdLog) {
        this.cdLog = cdLog;
    }
    
    public String getDsLog(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return this.idLog.toString() + " - " + sdf.format(this.dtLog) + " - " + this.dsMensagem;
    }
    
}
