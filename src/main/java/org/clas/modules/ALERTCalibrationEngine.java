package org.clas.modules;

import org.jlab.detector.calib.tasks.CalibrationEngine;
import org.jlab.detector.calib.utils.CalibrationConstants;
import org.jlab.detector.calib.utils.CalibrationConstantsListener;
import org.jlab.detector.calib.utils.ConstantsManager;
import org.jlab.groot.group.DataGroup;
import org.jlab.utils.groups.IndexedList;
import org.jlab.groot.data.IDataSet;
import org.jlab.groot.data.TDirectory;
import java.util.Map;
import java.util.List;


public class ALERTCalibrationEngine extends CalibrationEngine implements CalibrationConstantsListener {
    private String                            moduleName = null;
    private ALERTDetector                                ALERT = null;
    ConstantsManager                                ccdb = null;
    private ALERTCalConstants                      costants = new ALERTCalConstants();
    private CalibrationConstants                   calib = null;
    private CalibrationConstants               prevCalib = null;
    private Map<String,CalibrationConstants> globalCalib = null;
    private final IndexedList<DataGroup>      dataGroups = new IndexedList<DataGroup>(3);

    public ALERTCalibrationModule(ALERTDetector d, String ModuleName, String Constants,int Precision, ConstantsManager ccdb, Map<String,CalibrationConstants> gConstants) {
        this.ALERT    = d;
        this.initModule(ModuleName,Constants,Precision, ccdb, gConstants);
        this.resetEventListener();
    }

    public ALERTCalConstants getConstants() {
        return costants;
    }

    public String getName() {
        return moduleName;
    }

    public void initModule(String name, String Constants, int Precision, ConstantsManager ccdb, Map<String,CalibrationConstants> gConstants) {
        this.moduleName = name;
        this.calib = new CalibrationConstants(3,Constants);
        this.calib.setName(name);
        this.prevCalib = new CalibrationConstants(3,Constants);
        this.prevCalib.setName(name);
        this.setCalibrationTablePrecision(Precision);
        this.ccdb        = ccdb;
        this.globalCalib = gConstants;
    }

    public void setCalibrationTablePrecision(int nDigits) {
        this.calib.setPrecision(nDigits);
        this.prevCalib.setPrecision(nDigits);
    }

    public void writeDataGroup(TDirectory dir) {
        String folder = "/" + this.getName();
        dir.mkdir(folder);
        dir.cd(folder);
        Map<Long, DataGroup> map = this.getDataGroup().getMap();
        for( Map.Entry<Long, DataGroup> entry : map.entrySet()) {
            DataGroup group = entry.getValue();
            int nrows = group.getRows();
            int ncols = group.getColumns();
            int nds   = nrows*ncols;
            for(int i = 0; i < nds; i++){
                List<IDataSet> dsList = group.getData(i);
                for(IDataSet ds : dsList){
                    System.out.println("\t --> " + ds.getName());
                    dir.addDataSet(ds);
                }
            }
        }
    }

}
