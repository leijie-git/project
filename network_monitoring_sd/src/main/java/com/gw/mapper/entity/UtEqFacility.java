package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_EQ_Facility")
public class UtEqFacility implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "EqName")
    private String eqname;

    @Column(name = "EqPosition")
    private String eqposition;

    @Column(name = "EqSystem_type")
    private String eqsystemType;

    @Column(name = "UseTime")
    private Date usetime;

    @Column(name = "DetectoNum")
    private Integer detectonum;

    @Column(name = "ControlerNum")
    private Integer controlernum;

    @Column(name = "ManulAlarmbuttonNum")
    private Integer manulalarmbuttonnum;

    @Column(name = "ElectricalControldeviceNum")
    private Integer electricalcontroldevicenum;

    @Column(name = "WaterSupplypipeType")
    private String watersupplypipetype;

    @Column(name = "InletPipeNum")
    private Integer inletpipenum;

    @Column(name = "InletPipeDiameter")
    private Double inletpipediameter;

    @Column(name = "PoolCapacity")
    private Double poolcapacity;

    @Column(name = "PoolPosition")
    private String poolposition;

    @Column(name = "TankCapacity")
    private Double tankcapacity;

    @Column(name = "TankPosition")
    private String tankposition;

    @Column(name = "OtherWaterSupply")
    private Double otherwatersupply;

    @Column(name = "OtherWaterStituation")
    private String otherwaterstituation;

    @Column(name = "PumproomPosition")
    private String pumproomposition;

    @Column(name = "PumpNum")
    private Integer pumpnum;

    @Column(name = "PumpFlow")
    private Double pumpflow;

    @Column(name = "PumpLift")
    private Double pumplift;

    @Column(name = "OutdoorHydrantPipe_type")
    private String outdoorhydrantpipeType;

    @Column(name = "OutdoorHydrantNum")
    private Integer outdoorhydrantnum;

    @Column(name = "OutdoorHydrantPipeDiameter")
    private Double outdoorhydrantpipediameter;

    @Column(name = "IndoorHydrantPipeType")
    private String indoorhydrantpipetype;

    @Column(name = "IndoorHydrantNum")
    private Integer indoorhydrantnum;

    @Column(name = "IndoorHydrantPipeDiameter")
    private Double indoorhydrantpipediameter;

    @Column(name = "PumpReceiverNum")
    private Integer pumpreceivernum;

    @Column(name = "PumpReceiverPosition")
    private String pumpreceiverposition;

    @Column(name = "PressurePumpNum")
    private Integer pressurepumpnum;

    @Column(name = "PressurePumpFlow")
    private Integer pressurepumpflow;

    @Column(name = "PressurePumpLift")
    private Double pressurepumplift;

    @Column(name = "PressureTankCapacity")
    private Double pressuretankcapacity;

    @Column(name = "FireHoseNum")
    private Integer firehosenum;

    @Column(name = "AlarmValveNum")
    private Integer alarmvalvenum;

    @Column(name = "AlarmValvePosition")
    private String alarmvalveposition;

    @Column(name = "FlowIndicatorNum")
    private Integer flowindicatornum;

    @Column(name = "FlowIndicatorPosition")
    private String flowindicatorposition;

    @Column(name = "NozzleNum")
    private Integer nozzlenum;

    @Column(name = "ReliefPressureValveNum")
    private Integer reliefpressurevalvenum;

    @Column(name = "ReliefPressureValvePosition")
    private String reliefpressurevalveposition;

    @Column(name = "VerticalBlockNum")
    private Integer verticalblocknum;

    @Column(name = "SprayPumpNum")
    private Integer spraypumpnum;

    @Column(name = "SprayPumpFlow")
    private Double spraypumpflow;

    @Column(name = "SprayPumpLift")
    private Double spraypumplift;

    @Column(name = "SprayPumpPosition")
    private String spraypumpposition;

    @Column(name = "RainValveNum")
    private Integer rainvalvenum;

    @Column(name = "RainValvePositon")
    private String rainvalvepositon;

    @Column(name = "SprayNozzleNum")
    private Integer spraynozzlenum;

    @Column(name = "SprayNozzlePosition")
    private String spraynozzleposition;

    @Column(name = "ProtectionZoneNum")
    private Integer protectionzonenum;

    @Column(name = "ProtectionZoneCapacity")
    private Double protectionzonecapacity;

    @Column(name = "ProtectionZoneName")
    private String protectionzonename;

    @Column(name = "ProtectionZonePosition")
    private String protectionzoneposition;

    @Column(name = "ExtinguisherType")
    private String extinguishertype;

    @Column(name = "HandDevicePosition")
    private String handdeviceposition;

    @Column(name = "FacilityActionMode")
    private String facilityactionmode;

    @Column(name = "BottleBasePosition")
    private String bottlebaseposition;

    @Column(name = "SteelCylinderPositon")
    private String steelcylinderpositon;

    @Column(name = "OneSteelCylinderCapacity")
    private Double onesteelcylindercapacity;

    @Column(name = "SteelCylinderSpace")
    private Double steelcylinderspace;

    @Column(name = "FoamPumpNum")
    private Integer foampumpnum;

    @Column(name = "FoamPumpFlow")
    private Double foampumpflow;

    @Column(name = "FoamPumpLift")
    private Double foampumplift;

    @Column(name = "FoamValue")
    private Double foamvalue;

    @Column(name = "PowderTankPosition")
    private String powdertankposition;

    @Column(name = "SmokePreventionZoneNum")
    private Integer smokepreventionzonenum;

    @Column(name = "SmokePreventionZonePosition")
    private String smokepreventionzoneposition;

    @Column(name = "FanNum")
    private Integer fannum;

    @Column(name = "FanPosition")
    private String fanposition;

    @Column(name = "FanAirVolume")
    private Double fanairvolume;

    @Column(name = "TuyereSettingPosition")
    private String tuyeresettingposition;

    @Column(name = "DampersNum")
    private Integer dampersnum;

    @Column(name = "SmokeEmissionValveNum")
    private Integer smokeemissionvalvenum;

    @Column(name = "AirSupplyValve")
    private Integer airsupplyvalve;

    @Column(name = "FireDoorNum")
    private Integer firedoornum;

    @Column(name = "FireCurtainNum")
    private Integer firecurtainnum;

    @Column(name = "FireDoorPosition")
    private String firedoorposition;

    @Column(name = "FireCurtainPosition")
    private String firecurtainposition;

    @Column(name = "AmplifierPower")
    private Double amplifierpower;

    @Column(name = "SpareAmplifierPower")
    private Double spareamplifierpower;

    @Column(name = "SpeakerNum")
    private Integer speakernum;

    @Column(name = "BroadcastPartitionNum")
    private Integer broadcastpartitionnum;

    @Column(name = "BroadcastPartitionPosition")
    private String broadcastpartitionposition;

    @Column(name = "FirePhoneNum")
    private Integer firephonenum;

    @Column(name = "FirePhonePosition")
    private String firephoneposition;

    @Column(name = "ELIndicateDeviceNum")
    private Integer elindicatedevicenum;

    @Column(name = "FirePowerPosition")
    private String firepowerposition;

    @Column(name = "MainPowerIndependCabinet")
    private Integer mainpowerindependcabinet;

    @Column(name = "SparePowerTyoe")
    private String sparepowertyoe;

    @Column(name = "ExtinguisherPosition")
    private String extinguisherposition;

    @Column(name = "ExtinguisherConfigureType")
    private String extinguisherconfiguretype;

    @Column(name = "ExtinguisherProductDate")
    private Date extinguisherproductdate;

    @Column(name = "ChangeExtinguisherDate")
    private Date changeextinguisherdate;

    @Column(name = "ExtinguisherNum")
    private Integer extinguishernum;

    @Column(name = "FacilityServiceStatus")
    private Integer facilityservicestatus;

    @Column(name = "ProductUnitName")
    private String productunitname;

    @Column(name = "ProductUnitPhone")
    private String productunitphone;

    @Column(name = "MaintenanceUnitName")
    private String maintenanceunitname;

    @Column(name = "MaintenanceUnitPhone")
    private String maintenanceunitphone;

    @Column(name = "FacilityStatus")
    private Integer facilitystatus;

    @Column(name = "StatusDescribe")
    private String statusdescribe;

    @Column(name = "StatusChangeDate")
    private Date statuschangedate;

    @Column(name = "FireAlarmSysGraph")
    private byte[] firealarmsysgraph;

    @Column(name = "SupplyWaterSysGraph")
    private byte[] supplywatersysgraph;

    @Column(name = "OutdoorHydrantGraph")
    private byte[] outdoorhydrantgraph;

    @Column(name = "IndoorHydrantGraph")
    private byte[] indoorhydrantgraph;

    @Column(name = "AutomaticSprinklerSysGraph")
    private byte[] automaticsprinklersysgraph;

    @Column(name = "WaterSprayExtinguishingGraph")
    private byte[] watersprayextinguishinggraph;

    @Column(name = "GasExtinguishingGraph")
    private byte[] gasextinguishinggraph;

    @Column(name = "FoamExtinguishingGraph")
    private byte[] foamextinguishinggraph;

    @Column(name = "PowerExtinguishingGraph")
    private byte[] powerextinguishinggraph;

    @Column(name = "SmokeSysGraph")
    private byte[] smokesysgraph;

    @Column(name = "FireBroadcastSysGraph")
    private byte[] firebroadcastsysgraph;

    @Column(name = "ELIndicateDeviceSysGraph")
    private byte[] elindicatedevicesysgraph;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return EqName
     */
    public String getEqname() {
        return eqname;
    }

    /**
     * @param eqname
     */
    public void setEqname(String eqname) {
        this.eqname = eqname;
    }

    /**
     * @return EqPosition
     */
    public String getEqposition() {
        return eqposition;
    }

    /**
     * @param eqposition
     */
    public void setEqposition(String eqposition) {
        this.eqposition = eqposition;
    }

    /**
     * @return EqSystem_type
     */
    public String getEqsystemType() {
        return eqsystemType;
    }

    /**
     * @param eqsystemType
     */
    public void setEqsystemType(String eqsystemType) {
        this.eqsystemType = eqsystemType;
    }

    /**
     * @return UseTime
     */
    public Date getUsetime() {
        return usetime;
    }

    /**
     * @param usetime
     */
    public void setUsetime(Date usetime) {
        this.usetime = usetime;
    }

    /**
     * @return DetectoNum
     */
    public Integer getDetectonum() {
        return detectonum;
    }

    /**
     * @param detectonum
     */
    public void setDetectonum(Integer detectonum) {
        this.detectonum = detectonum;
    }

    /**
     * @return ControlerNum
     */
    public Integer getControlernum() {
        return controlernum;
    }

    /**
     * @param controlernum
     */
    public void setControlernum(Integer controlernum) {
        this.controlernum = controlernum;
    }

    /**
     * @return ManulAlarmbuttonNum
     */
    public Integer getManulalarmbuttonnum() {
        return manulalarmbuttonnum;
    }

    /**
     * @param manulalarmbuttonnum
     */
    public void setManulalarmbuttonnum(Integer manulalarmbuttonnum) {
        this.manulalarmbuttonnum = manulalarmbuttonnum;
    }

    /**
     * @return ElectricalControldeviceNum
     */
    public Integer getElectricalcontroldevicenum() {
        return electricalcontroldevicenum;
    }

    /**
     * @param electricalcontroldevicenum
     */
    public void setElectricalcontroldevicenum(Integer electricalcontroldevicenum) {
        this.electricalcontroldevicenum = electricalcontroldevicenum;
    }

    /**
     * @return WaterSupplypipeType
     */
    public String getWatersupplypipetype() {
        return watersupplypipetype;
    }

    /**
     * @param watersupplypipetype
     */
    public void setWatersupplypipetype(String watersupplypipetype) {
        this.watersupplypipetype = watersupplypipetype;
    }

    /**
     * @return InletPipeNum
     */
    public Integer getInletpipenum() {
        return inletpipenum;
    }

    /**
     * @param inletpipenum
     */
    public void setInletpipenum(Integer inletpipenum) {
        this.inletpipenum = inletpipenum;
    }

    /**
     * @return InletPipeDiameter
     */
    public Double getInletpipediameter() {
        return inletpipediameter;
    }

    /**
     * @param inletpipediameter
     */
    public void setInletpipediameter(Double inletpipediameter) {
        this.inletpipediameter = inletpipediameter;
    }

    /**
     * @return PoolCapacity
     */
    public Double getPoolcapacity() {
        return poolcapacity;
    }

    /**
     * @param poolcapacity
     */
    public void setPoolcapacity(Double poolcapacity) {
        this.poolcapacity = poolcapacity;
    }

    /**
     * @return PoolPosition
     */
    public String getPoolposition() {
        return poolposition;
    }

    /**
     * @param poolposition
     */
    public void setPoolposition(String poolposition) {
        this.poolposition = poolposition;
    }

    /**
     * @return TankCapacity
     */
    public Double getTankcapacity() {
        return tankcapacity;
    }

    /**
     * @param tankcapacity
     */
    public void setTankcapacity(Double tankcapacity) {
        this.tankcapacity = tankcapacity;
    }

    /**
     * @return TankPosition
     */
    public String getTankposition() {
        return tankposition;
    }

    /**
     * @param tankposition
     */
    public void setTankposition(String tankposition) {
        this.tankposition = tankposition;
    }

    /**
     * @return OtherWaterSupply
     */
    public Double getOtherwatersupply() {
        return otherwatersupply;
    }

    /**
     * @param otherwatersupply
     */
    public void setOtherwatersupply(Double otherwatersupply) {
        this.otherwatersupply = otherwatersupply;
    }

    /**
     * @return OtherWaterStituation
     */
    public String getOtherwaterstituation() {
        return otherwaterstituation;
    }

    /**
     * @param otherwaterstituation
     */
    public void setOtherwaterstituation(String otherwaterstituation) {
        this.otherwaterstituation = otherwaterstituation;
    }

    /**
     * @return PumproomPosition
     */
    public String getPumproomposition() {
        return pumproomposition;
    }

    /**
     * @param pumproomposition
     */
    public void setPumproomposition(String pumproomposition) {
        this.pumproomposition = pumproomposition;
    }

    /**
     * @return PumpNum
     */
    public Integer getPumpnum() {
        return pumpnum;
    }

    /**
     * @param pumpnum
     */
    public void setPumpnum(Integer pumpnum) {
        this.pumpnum = pumpnum;
    }

    /**
     * @return PumpFlow
     */
    public Double getPumpflow() {
        return pumpflow;
    }

    /**
     * @param pumpflow
     */
    public void setPumpflow(Double pumpflow) {
        this.pumpflow = pumpflow;
    }

    /**
     * @return PumpLift
     */
    public Double getPumplift() {
        return pumplift;
    }

    /**
     * @param pumplift
     */
    public void setPumplift(Double pumplift) {
        this.pumplift = pumplift;
    }

    /**
     * @return OutdoorHydrantPipe_type
     */
    public String getOutdoorhydrantpipeType() {
        return outdoorhydrantpipeType;
    }

    /**
     * @param outdoorhydrantpipeType
     */
    public void setOutdoorhydrantpipeType(String outdoorhydrantpipeType) {
        this.outdoorhydrantpipeType = outdoorhydrantpipeType;
    }

    /**
     * @return OutdoorHydrantNum
     */
    public Integer getOutdoorhydrantnum() {
        return outdoorhydrantnum;
    }

    /**
     * @param outdoorhydrantnum
     */
    public void setOutdoorhydrantnum(Integer outdoorhydrantnum) {
        this.outdoorhydrantnum = outdoorhydrantnum;
    }

    /**
     * @return OutdoorHydrantPipeDiameter
     */
    public Double getOutdoorhydrantpipediameter() {
        return outdoorhydrantpipediameter;
    }

    /**
     * @param outdoorhydrantpipediameter
     */
    public void setOutdoorhydrantpipediameter(Double outdoorhydrantpipediameter) {
        this.outdoorhydrantpipediameter = outdoorhydrantpipediameter;
    }

    /**
     * @return IndoorHydrantPipeType
     */
    public String getIndoorhydrantpipetype() {
        return indoorhydrantpipetype;
    }

    /**
     * @param indoorhydrantpipetype
     */
    public void setIndoorhydrantpipetype(String indoorhydrantpipetype) {
        this.indoorhydrantpipetype = indoorhydrantpipetype;
    }

    /**
     * @return IndoorHydrantNum
     */
    public Integer getIndoorhydrantnum() {
        return indoorhydrantnum;
    }

    /**
     * @param indoorhydrantnum
     */
    public void setIndoorhydrantnum(Integer indoorhydrantnum) {
        this.indoorhydrantnum = indoorhydrantnum;
    }

    /**
     * @return IndoorHydrantPipeDiameter
     */
    public Double getIndoorhydrantpipediameter() {
        return indoorhydrantpipediameter;
    }

    /**
     * @param indoorhydrantpipediameter
     */
    public void setIndoorhydrantpipediameter(Double indoorhydrantpipediameter) {
        this.indoorhydrantpipediameter = indoorhydrantpipediameter;
    }

    /**
     * @return PumpReceiverNum
     */
    public Integer getPumpreceivernum() {
        return pumpreceivernum;
    }

    /**
     * @param pumpreceivernum
     */
    public void setPumpreceivernum(Integer pumpreceivernum) {
        this.pumpreceivernum = pumpreceivernum;
    }

    /**
     * @return PumpReceiverPosition
     */
    public String getPumpreceiverposition() {
        return pumpreceiverposition;
    }

    /**
     * @param pumpreceiverposition
     */
    public void setPumpreceiverposition(String pumpreceiverposition) {
        this.pumpreceiverposition = pumpreceiverposition;
    }

    /**
     * @return PressurePumpNum
     */
    public Integer getPressurepumpnum() {
        return pressurepumpnum;
    }

    /**
     * @param pressurepumpnum
     */
    public void setPressurepumpnum(Integer pressurepumpnum) {
        this.pressurepumpnum = pressurepumpnum;
    }

    /**
     * @return PressurePumpFlow
     */
    public Integer getPressurepumpflow() {
        return pressurepumpflow;
    }

    /**
     * @param pressurepumpflow
     */
    public void setPressurepumpflow(Integer pressurepumpflow) {
        this.pressurepumpflow = pressurepumpflow;
    }

    /**
     * @return PressurePumpLift
     */
    public Double getPressurepumplift() {
        return pressurepumplift;
    }

    /**
     * @param pressurepumplift
     */
    public void setPressurepumplift(Double pressurepumplift) {
        this.pressurepumplift = pressurepumplift;
    }

    /**
     * @return PressureTankCapacity
     */
    public Double getPressuretankcapacity() {
        return pressuretankcapacity;
    }

    /**
     * @param pressuretankcapacity
     */
    public void setPressuretankcapacity(Double pressuretankcapacity) {
        this.pressuretankcapacity = pressuretankcapacity;
    }

    /**
     * @return FireHoseNum
     */
    public Integer getFirehosenum() {
        return firehosenum;
    }

    /**
     * @param firehosenum
     */
    public void setFirehosenum(Integer firehosenum) {
        this.firehosenum = firehosenum;
    }

    /**
     * @return AlarmValveNum
     */
    public Integer getAlarmvalvenum() {
        return alarmvalvenum;
    }

    /**
     * @param alarmvalvenum
     */
    public void setAlarmvalvenum(Integer alarmvalvenum) {
        this.alarmvalvenum = alarmvalvenum;
    }

    /**
     * @return AlarmValvePosition
     */
    public String getAlarmvalveposition() {
        return alarmvalveposition;
    }

    /**
     * @param alarmvalveposition
     */
    public void setAlarmvalveposition(String alarmvalveposition) {
        this.alarmvalveposition = alarmvalveposition;
    }

    /**
     * @return FlowIndicatorNum
     */
    public Integer getFlowindicatornum() {
        return flowindicatornum;
    }

    /**
     * @param flowindicatornum
     */
    public void setFlowindicatornum(Integer flowindicatornum) {
        this.flowindicatornum = flowindicatornum;
    }

    /**
     * @return FlowIndicatorPosition
     */
    public String getFlowindicatorposition() {
        return flowindicatorposition;
    }

    /**
     * @param flowindicatorposition
     */
    public void setFlowindicatorposition(String flowindicatorposition) {
        this.flowindicatorposition = flowindicatorposition;
    }

    /**
     * @return NozzleNum
     */
    public Integer getNozzlenum() {
        return nozzlenum;
    }

    /**
     * @param nozzlenum
     */
    public void setNozzlenum(Integer nozzlenum) {
        this.nozzlenum = nozzlenum;
    }

    /**
     * @return ReliefPressureValveNum
     */
    public Integer getReliefpressurevalvenum() {
        return reliefpressurevalvenum;
    }

    /**
     * @param reliefpressurevalvenum
     */
    public void setReliefpressurevalvenum(Integer reliefpressurevalvenum) {
        this.reliefpressurevalvenum = reliefpressurevalvenum;
    }

    /**
     * @return ReliefPressureValvePosition
     */
    public String getReliefpressurevalveposition() {
        return reliefpressurevalveposition;
    }

    /**
     * @param reliefpressurevalveposition
     */
    public void setReliefpressurevalveposition(String reliefpressurevalveposition) {
        this.reliefpressurevalveposition = reliefpressurevalveposition;
    }

    /**
     * @return VerticalBlockNum
     */
    public Integer getVerticalblocknum() {
        return verticalblocknum;
    }

    /**
     * @param verticalblocknum
     */
    public void setVerticalblocknum(Integer verticalblocknum) {
        this.verticalblocknum = verticalblocknum;
    }

    /**
     * @return SprayPumpNum
     */
    public Integer getSpraypumpnum() {
        return spraypumpnum;
    }

    /**
     * @param spraypumpnum
     */
    public void setSpraypumpnum(Integer spraypumpnum) {
        this.spraypumpnum = spraypumpnum;
    }

    /**
     * @return SprayPumpFlow
     */
    public Double getSpraypumpflow() {
        return spraypumpflow;
    }

    /**
     * @param spraypumpflow
     */
    public void setSpraypumpflow(Double spraypumpflow) {
        this.spraypumpflow = spraypumpflow;
    }

    /**
     * @return SprayPumpLift
     */
    public Double getSpraypumplift() {
        return spraypumplift;
    }

    /**
     * @param spraypumplift
     */
    public void setSpraypumplift(Double spraypumplift) {
        this.spraypumplift = spraypumplift;
    }

    /**
     * @return SprayPumpPosition
     */
    public String getSpraypumpposition() {
        return spraypumpposition;
    }

    /**
     * @param spraypumpposition
     */
    public void setSpraypumpposition(String spraypumpposition) {
        this.spraypumpposition = spraypumpposition;
    }

    /**
     * @return RainValveNum
     */
    public Integer getRainvalvenum() {
        return rainvalvenum;
    }

    /**
     * @param rainvalvenum
     */
    public void setRainvalvenum(Integer rainvalvenum) {
        this.rainvalvenum = rainvalvenum;
    }

    /**
     * @return RainValvePositon
     */
    public String getRainvalvepositon() {
        return rainvalvepositon;
    }

    /**
     * @param rainvalvepositon
     */
    public void setRainvalvepositon(String rainvalvepositon) {
        this.rainvalvepositon = rainvalvepositon;
    }

    /**
     * @return SprayNozzleNum
     */
    public Integer getSpraynozzlenum() {
        return spraynozzlenum;
    }

    /**
     * @param spraynozzlenum
     */
    public void setSpraynozzlenum(Integer spraynozzlenum) {
        this.spraynozzlenum = spraynozzlenum;
    }

    /**
     * @return SprayNozzlePosition
     */
    public String getSpraynozzleposition() {
        return spraynozzleposition;
    }

    /**
     * @param spraynozzleposition
     */
    public void setSpraynozzleposition(String spraynozzleposition) {
        this.spraynozzleposition = spraynozzleposition;
    }

    /**
     * @return ProtectionZoneNum
     */
    public Integer getProtectionzonenum() {
        return protectionzonenum;
    }

    /**
     * @param protectionzonenum
     */
    public void setProtectionzonenum(Integer protectionzonenum) {
        this.protectionzonenum = protectionzonenum;
    }

    /**
     * @return ProtectionZoneCapacity
     */
    public Double getProtectionzonecapacity() {
        return protectionzonecapacity;
    }

    /**
     * @param protectionzonecapacity
     */
    public void setProtectionzonecapacity(Double protectionzonecapacity) {
        this.protectionzonecapacity = protectionzonecapacity;
    }

    /**
     * @return ProtectionZoneName
     */
    public String getProtectionzonename() {
        return protectionzonename;
    }

    /**
     * @param protectionzonename
     */
    public void setProtectionzonename(String protectionzonename) {
        this.protectionzonename = protectionzonename;
    }

    /**
     * @return ProtectionZonePosition
     */
    public String getProtectionzoneposition() {
        return protectionzoneposition;
    }

    /**
     * @param protectionzoneposition
     */
    public void setProtectionzoneposition(String protectionzoneposition) {
        this.protectionzoneposition = protectionzoneposition;
    }

    /**
     * @return ExtinguisherType
     */
    public String getExtinguishertype() {
        return extinguishertype;
    }

    /**
     * @param extinguishertype
     */
    public void setExtinguishertype(String extinguishertype) {
        this.extinguishertype = extinguishertype;
    }

    /**
     * @return HandDevicePosition
     */
    public String getHanddeviceposition() {
        return handdeviceposition;
    }

    /**
     * @param handdeviceposition
     */
    public void setHanddeviceposition(String handdeviceposition) {
        this.handdeviceposition = handdeviceposition;
    }

    /**
     * @return FacilityActionMode
     */
    public String getFacilityactionmode() {
        return facilityactionmode;
    }

    /**
     * @param facilityactionmode
     */
    public void setFacilityactionmode(String facilityactionmode) {
        this.facilityactionmode = facilityactionmode;
    }

    /**
     * @return BottleBasePosition
     */
    public String getBottlebaseposition() {
        return bottlebaseposition;
    }

    /**
     * @param bottlebaseposition
     */
    public void setBottlebaseposition(String bottlebaseposition) {
        this.bottlebaseposition = bottlebaseposition;
    }

    /**
     * @return SteelCylinderPositon
     */
    public String getSteelcylinderpositon() {
        return steelcylinderpositon;
    }

    /**
     * @param steelcylinderpositon
     */
    public void setSteelcylinderpositon(String steelcylinderpositon) {
        this.steelcylinderpositon = steelcylinderpositon;
    }

    /**
     * @return OneSteelCylinderCapacity
     */
    public Double getOnesteelcylindercapacity() {
        return onesteelcylindercapacity;
    }

    /**
     * @param onesteelcylindercapacity
     */
    public void setOnesteelcylindercapacity(Double onesteelcylindercapacity) {
        this.onesteelcylindercapacity = onesteelcylindercapacity;
    }

    /**
     * @return SteelCylinderSpace
     */
    public Double getSteelcylinderspace() {
        return steelcylinderspace;
    }

    /**
     * @param steelcylinderspace
     */
    public void setSteelcylinderspace(Double steelcylinderspace) {
        this.steelcylinderspace = steelcylinderspace;
    }

    /**
     * @return FoamPumpNum
     */
    public Integer getFoampumpnum() {
        return foampumpnum;
    }

    /**
     * @param foampumpnum
     */
    public void setFoampumpnum(Integer foampumpnum) {
        this.foampumpnum = foampumpnum;
    }

    /**
     * @return FoamPumpFlow
     */
    public Double getFoampumpflow() {
        return foampumpflow;
    }

    /**
     * @param foampumpflow
     */
    public void setFoampumpflow(Double foampumpflow) {
        this.foampumpflow = foampumpflow;
    }

    /**
     * @return FoamPumpLift
     */
    public Double getFoampumplift() {
        return foampumplift;
    }

    /**
     * @param foampumplift
     */
    public void setFoampumplift(Double foampumplift) {
        this.foampumplift = foampumplift;
    }

    /**
     * @return FoamValue
     */
    public Double getFoamvalue() {
        return foamvalue;
    }

    /**
     * @param foamvalue
     */
    public void setFoamvalue(Double foamvalue) {
        this.foamvalue = foamvalue;
    }

    /**
     * @return PowderTankPosition
     */
    public String getPowdertankposition() {
        return powdertankposition;
    }

    /**
     * @param powdertankposition
     */
    public void setPowdertankposition(String powdertankposition) {
        this.powdertankposition = powdertankposition;
    }

    /**
     * @return SmokePreventionZoneNum
     */
    public Integer getSmokepreventionzonenum() {
        return smokepreventionzonenum;
    }

    /**
     * @param smokepreventionzonenum
     */
    public void setSmokepreventionzonenum(Integer smokepreventionzonenum) {
        this.smokepreventionzonenum = smokepreventionzonenum;
    }

    /**
     * @return SmokePreventionZonePosition
     */
    public String getSmokepreventionzoneposition() {
        return smokepreventionzoneposition;
    }

    /**
     * @param smokepreventionzoneposition
     */
    public void setSmokepreventionzoneposition(String smokepreventionzoneposition) {
        this.smokepreventionzoneposition = smokepreventionzoneposition;
    }

    /**
     * @return FanNum
     */
    public Integer getFannum() {
        return fannum;
    }

    /**
     * @param fannum
     */
    public void setFannum(Integer fannum) {
        this.fannum = fannum;
    }

    /**
     * @return FanPosition
     */
    public String getFanposition() {
        return fanposition;
    }

    /**
     * @param fanposition
     */
    public void setFanposition(String fanposition) {
        this.fanposition = fanposition;
    }

    /**
     * @return FanAirVolume
     */
    public Double getFanairvolume() {
        return fanairvolume;
    }

    /**
     * @param fanairvolume
     */
    public void setFanairvolume(Double fanairvolume) {
        this.fanairvolume = fanairvolume;
    }

    /**
     * @return TuyereSettingPosition
     */
    public String getTuyeresettingposition() {
        return tuyeresettingposition;
    }

    /**
     * @param tuyeresettingposition
     */
    public void setTuyeresettingposition(String tuyeresettingposition) {
        this.tuyeresettingposition = tuyeresettingposition;
    }

    /**
     * @return DampersNum
     */
    public Integer getDampersnum() {
        return dampersnum;
    }

    /**
     * @param dampersnum
     */
    public void setDampersnum(Integer dampersnum) {
        this.dampersnum = dampersnum;
    }

    /**
     * @return SmokeEmissionValveNum
     */
    public Integer getSmokeemissionvalvenum() {
        return smokeemissionvalvenum;
    }

    /**
     * @param smokeemissionvalvenum
     */
    public void setSmokeemissionvalvenum(Integer smokeemissionvalvenum) {
        this.smokeemissionvalvenum = smokeemissionvalvenum;
    }

    /**
     * @return AirSupplyValve
     */
    public Integer getAirsupplyvalve() {
        return airsupplyvalve;
    }

    /**
     * @param airsupplyvalve
     */
    public void setAirsupplyvalve(Integer airsupplyvalve) {
        this.airsupplyvalve = airsupplyvalve;
    }

    /**
     * @return FireDoorNum
     */
    public Integer getFiredoornum() {
        return firedoornum;
    }

    /**
     * @param firedoornum
     */
    public void setFiredoornum(Integer firedoornum) {
        this.firedoornum = firedoornum;
    }

    /**
     * @return FireCurtainNum
     */
    public Integer getFirecurtainnum() {
        return firecurtainnum;
    }

    /**
     * @param firecurtainnum
     */
    public void setFirecurtainnum(Integer firecurtainnum) {
        this.firecurtainnum = firecurtainnum;
    }

    /**
     * @return FireDoorPosition
     */
    public String getFiredoorposition() {
        return firedoorposition;
    }

    /**
     * @param firedoorposition
     */
    public void setFiredoorposition(String firedoorposition) {
        this.firedoorposition = firedoorposition;
    }

    /**
     * @return FireCurtainPosition
     */
    public String getFirecurtainposition() {
        return firecurtainposition;
    }

    /**
     * @param firecurtainposition
     */
    public void setFirecurtainposition(String firecurtainposition) {
        this.firecurtainposition = firecurtainposition;
    }

    /**
     * @return AmplifierPower
     */
    public Double getAmplifierpower() {
        return amplifierpower;
    }

    /**
     * @param amplifierpower
     */
    public void setAmplifierpower(Double amplifierpower) {
        this.amplifierpower = amplifierpower;
    }

    /**
     * @return SpareAmplifierPower
     */
    public Double getSpareamplifierpower() {
        return spareamplifierpower;
    }

    /**
     * @param spareamplifierpower
     */
    public void setSpareamplifierpower(Double spareamplifierpower) {
        this.spareamplifierpower = spareamplifierpower;
    }

    /**
     * @return SpeakerNum
     */
    public Integer getSpeakernum() {
        return speakernum;
    }

    /**
     * @param speakernum
     */
    public void setSpeakernum(Integer speakernum) {
        this.speakernum = speakernum;
    }

    /**
     * @return BroadcastPartitionNum
     */
    public Integer getBroadcastpartitionnum() {
        return broadcastpartitionnum;
    }

    /**
     * @param broadcastpartitionnum
     */
    public void setBroadcastpartitionnum(Integer broadcastpartitionnum) {
        this.broadcastpartitionnum = broadcastpartitionnum;
    }

    /**
     * @return BroadcastPartitionPosition
     */
    public String getBroadcastpartitionposition() {
        return broadcastpartitionposition;
    }

    /**
     * @param broadcastpartitionposition
     */
    public void setBroadcastpartitionposition(String broadcastpartitionposition) {
        this.broadcastpartitionposition = broadcastpartitionposition;
    }

    /**
     * @return FirePhoneNum
     */
    public Integer getFirephonenum() {
        return firephonenum;
    }

    /**
     * @param firephonenum
     */
    public void setFirephonenum(Integer firephonenum) {
        this.firephonenum = firephonenum;
    }

    /**
     * @return FirePhonePosition
     */
    public String getFirephoneposition() {
        return firephoneposition;
    }

    /**
     * @param firephoneposition
     */
    public void setFirephoneposition(String firephoneposition) {
        this.firephoneposition = firephoneposition;
    }

    /**
     * @return ELIndicateDeviceNum
     */
    public Integer getElindicatedevicenum() {
        return elindicatedevicenum;
    }

    /**
     * @param elindicatedevicenum
     */
    public void setElindicatedevicenum(Integer elindicatedevicenum) {
        this.elindicatedevicenum = elindicatedevicenum;
    }

    /**
     * @return FirePowerPosition
     */
    public String getFirepowerposition() {
        return firepowerposition;
    }

    /**
     * @param firepowerposition
     */
    public void setFirepowerposition(String firepowerposition) {
        this.firepowerposition = firepowerposition;
    }

    /**
     * @return MainPowerIndependCabinet
     */
    public Integer getMainpowerindependcabinet() {
        return mainpowerindependcabinet;
    }

    /**
     * @param mainpowerindependcabinet
     */
    public void setMainpowerindependcabinet(Integer mainpowerindependcabinet) {
        this.mainpowerindependcabinet = mainpowerindependcabinet;
    }

    /**
     * @return SparePowerTyoe
     */
    public String getSparepowertyoe() {
        return sparepowertyoe;
    }

    /**
     * @param sparepowertyoe
     */
    public void setSparepowertyoe(String sparepowertyoe) {
        this.sparepowertyoe = sparepowertyoe;
    }

    /**
     * @return ExtinguisherPosition
     */
    public String getExtinguisherposition() {
        return extinguisherposition;
    }

    /**
     * @param extinguisherposition
     */
    public void setExtinguisherposition(String extinguisherposition) {
        this.extinguisherposition = extinguisherposition;
    }

    /**
     * @return ExtinguisherConfigureType
     */
    public String getExtinguisherconfiguretype() {
        return extinguisherconfiguretype;
    }

    /**
     * @param extinguisherconfiguretype
     */
    public void setExtinguisherconfiguretype(String extinguisherconfiguretype) {
        this.extinguisherconfiguretype = extinguisherconfiguretype;
    }

    /**
     * @return ExtinguisherProductDate
     */
    public Date getExtinguisherproductdate() {
        return extinguisherproductdate;
    }

    /**
     * @param extinguisherproductdate
     */
    public void setExtinguisherproductdate(Date extinguisherproductdate) {
        this.extinguisherproductdate = extinguisherproductdate;
    }

    /**
     * @return ChangeExtinguisherDate
     */
    public Date getChangeextinguisherdate() {
        return changeextinguisherdate;
    }

    /**
     * @param changeextinguisherdate
     */
    public void setChangeextinguisherdate(Date changeextinguisherdate) {
        this.changeextinguisherdate = changeextinguisherdate;
    }

    /**
     * @return ExtinguisherNum
     */
    public Integer getExtinguishernum() {
        return extinguishernum;
    }

    /**
     * @param extinguishernum
     */
    public void setExtinguishernum(Integer extinguishernum) {
        this.extinguishernum = extinguishernum;
    }

    /**
     * @return FacilityServiceStatus
     */
    public Integer getFacilityservicestatus() {
        return facilityservicestatus;
    }

    /**
     * @param facilityservicestatus
     */
    public void setFacilityservicestatus(Integer facilityservicestatus) {
        this.facilityservicestatus = facilityservicestatus;
    }

    /**
     * @return ProductUnitName
     */
    public String getProductunitname() {
        return productunitname;
    }

    /**
     * @param productunitname
     */
    public void setProductunitname(String productunitname) {
        this.productunitname = productunitname;
    }

    /**
     * @return ProductUnitPhone
     */
    public String getProductunitphone() {
        return productunitphone;
    }

    /**
     * @param productunitphone
     */
    public void setProductunitphone(String productunitphone) {
        this.productunitphone = productunitphone;
    }

    /**
     * @return MaintenanceUnitName
     */
    public String getMaintenanceunitname() {
        return maintenanceunitname;
    }

    /**
     * @param maintenanceunitname
     */
    public void setMaintenanceunitname(String maintenanceunitname) {
        this.maintenanceunitname = maintenanceunitname;
    }

    /**
     * @return MaintenanceUnitPhone
     */
    public String getMaintenanceunitphone() {
        return maintenanceunitphone;
    }

    /**
     * @param maintenanceunitphone
     */
    public void setMaintenanceunitphone(String maintenanceunitphone) {
        this.maintenanceunitphone = maintenanceunitphone;
    }

    /**
     * @return FacilityStatus
     */
    public Integer getFacilitystatus() {
        return facilitystatus;
    }

    /**
     * @param facilitystatus
     */
    public void setFacilitystatus(Integer facilitystatus) {
        this.facilitystatus = facilitystatus;
    }

    /**
     * @return StatusDescribe
     */
    public String getStatusdescribe() {
        return statusdescribe;
    }

    /**
     * @param statusdescribe
     */
    public void setStatusdescribe(String statusdescribe) {
        this.statusdescribe = statusdescribe;
    }

    /**
     * @return StatusChangeDate
     */
    public Date getStatuschangedate() {
        return statuschangedate;
    }

    /**
     * @param statuschangedate
     */
    public void setStatuschangedate(Date statuschangedate) {
        this.statuschangedate = statuschangedate;
    }

    /**
     * @return FireAlarmSysGraph
     */
    public byte[] getFirealarmsysgraph() {
        return firealarmsysgraph;
    }

    /**
     * @param firealarmsysgraph
     */
    public void setFirealarmsysgraph(byte[] firealarmsysgraph) {
        this.firealarmsysgraph = firealarmsysgraph;
    }

    /**
     * @return SupplyWaterSysGraph
     */
    public byte[] getSupplywatersysgraph() {
        return supplywatersysgraph;
    }

    /**
     * @param supplywatersysgraph
     */
    public void setSupplywatersysgraph(byte[] supplywatersysgraph) {
        this.supplywatersysgraph = supplywatersysgraph;
    }

    /**
     * @return OutdoorHydrantGraph
     */
    public byte[] getOutdoorhydrantgraph() {
        return outdoorhydrantgraph;
    }

    /**
     * @param outdoorhydrantgraph
     */
    public void setOutdoorhydrantgraph(byte[] outdoorhydrantgraph) {
        this.outdoorhydrantgraph = outdoorhydrantgraph;
    }

    /**
     * @return IndoorHydrantGraph
     */
    public byte[] getIndoorhydrantgraph() {
        return indoorhydrantgraph;
    }

    /**
     * @param indoorhydrantgraph
     */
    public void setIndoorhydrantgraph(byte[] indoorhydrantgraph) {
        this.indoorhydrantgraph = indoorhydrantgraph;
    }

    /**
     * @return AutomaticSprinklerSysGraph
     */
    public byte[] getAutomaticsprinklersysgraph() {
        return automaticsprinklersysgraph;
    }

    /**
     * @param automaticsprinklersysgraph
     */
    public void setAutomaticsprinklersysgraph(byte[] automaticsprinklersysgraph) {
        this.automaticsprinklersysgraph = automaticsprinklersysgraph;
    }

    /**
     * @return WaterSprayExtinguishingGraph
     */
    public byte[] getWatersprayextinguishinggraph() {
        return watersprayextinguishinggraph;
    }

    /**
     * @param watersprayextinguishinggraph
     */
    public void setWatersprayextinguishinggraph(byte[] watersprayextinguishinggraph) {
        this.watersprayextinguishinggraph = watersprayextinguishinggraph;
    }

    /**
     * @return GasExtinguishingGraph
     */
    public byte[] getGasextinguishinggraph() {
        return gasextinguishinggraph;
    }

    /**
     * @param gasextinguishinggraph
     */
    public void setGasextinguishinggraph(byte[] gasextinguishinggraph) {
        this.gasextinguishinggraph = gasextinguishinggraph;
    }

    /**
     * @return FoamExtinguishingGraph
     */
    public byte[] getFoamextinguishinggraph() {
        return foamextinguishinggraph;
    }

    /**
     * @param foamextinguishinggraph
     */
    public void setFoamextinguishinggraph(byte[] foamextinguishinggraph) {
        this.foamextinguishinggraph = foamextinguishinggraph;
    }

    /**
     * @return PowerExtinguishingGraph
     */
    public byte[] getPowerextinguishinggraph() {
        return powerextinguishinggraph;
    }

    /**
     * @param powerextinguishinggraph
     */
    public void setPowerextinguishinggraph(byte[] powerextinguishinggraph) {
        this.powerextinguishinggraph = powerextinguishinggraph;
    }

    /**
     * @return SmokeSysGraph
     */
    public byte[] getSmokesysgraph() {
        return smokesysgraph;
    }

    /**
     * @param smokesysgraph
     */
    public void setSmokesysgraph(byte[] smokesysgraph) {
        this.smokesysgraph = smokesysgraph;
    }

    /**
     * @return FireBroadcastSysGraph
     */
    public byte[] getFirebroadcastsysgraph() {
        return firebroadcastsysgraph;
    }

    /**
     * @param firebroadcastsysgraph
     */
    public void setFirebroadcastsysgraph(byte[] firebroadcastsysgraph) {
        this.firebroadcastsysgraph = firebroadcastsysgraph;
    }

    /**
     * @return ELIndicateDeviceSysGraph
     */
    public byte[] getElindicatedevicesysgraph() {
        return elindicatedevicesysgraph;
    }

    /**
     * @param elindicatedevicesysgraph
     */
    public void setElindicatedevicesysgraph(byte[] elindicatedevicesysgraph) {
        this.elindicatedevicesysgraph = elindicatedevicesysgraph;
    }
}