package com.autoremoteexample.polio.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Unknown_ on 5/27/2017.
 */

public class PolioScannerData implements Parcelable, Serializable {

    private String username = "";
    private float tempreture = Float.MIN_VALUE;

    private String qrCodeFormat = "INITQRFORMAT";
    private String qrCodeContent = "INITQRCONTENT";
    //adding this field according to API v1
    private String sample_id = "INITSAMPLEID";
    private String lat = "INITLAT";
    private String lng = "INITLNG";
    //Satrt Elution
    private String elutedByPerson;
    private String elutedBydate;

    //End Ellution
    private float recoveredElutedmL  = Float.MIN_VALUE;;


    //now field parts
    private int rainYesterday  = Integer.MIN_VALUE;
    private int rainToday= Integer.MIN_VALUE; ;
    private float fieldtemp  = Float.MIN_VALUE;;
    private String fieldShippedBy;
    private String fieldShippedOn;
    private float fieldWaterPh  = Float.MIN_VALUE;;
    private float fieldVolume  = Float.MIN_VALUE;;
    private float fieldVolumeAfter  = Float.MIN_VALUE;;
    private float fieldVolumeRemaining  = Float.MIN_VALUE;;
    private int shippedCold = Integer.MIN_VALUE;;
    private float fieldShippedtemp  = Float.MIN_VALUE;;
    private String fieldShippedBy2;
    private String fieldShippedOn2;

    // secondary concenteration
    private String author;



    //RECIEVED IN LAB
    private int tempLoggerIncluded = Integer.MIN_VALUE;;
    private int tempLoggerNotIncluded= Integer.MIN_VALUE;;
    private String labRecievedBy;
    private String labRecievedOn;
    private float labReceivedTemp  = Float.MIN_VALUE;;
    private int sampleCold= Integer.MIN_VALUE;;
    private int sampleNotCold= Integer.MIN_VALUE;;

    //Secondary Concentration
    private String concentratedOn;
    private String concentratedBy;
    private String secConMethod;


    public PolioScannerData() {
        super();
    }

    protected PolioScannerData(Parcel in) {
        username = in.readString();
        tempreture = in.readFloat();

        qrCodeContent = in.readString();
        qrCodeFormat = in.readString();


        // field from API v1
        sample_id = in.readString();
        lat = in.readString();
        lng = in.readString();


        elutedByPerson = in.readString();
        elutedBydate = in.readString();
        recoveredElutedmL = in.readFloat();

        // now field part
        rainYesterday = in.readInt();
        rainToday = in.readInt();
        fieldtemp = in.readFloat();
        fieldShippedBy = in.readString();
        fieldShippedOn = in.readString();
        fieldWaterPh = in.readFloat();
        fieldVolume = in.readFloat();
        fieldVolumeAfter = in.readFloat();
        fieldVolumeRemaining = in.readFloat();
        shippedCold = in.readInt();
        fieldShippedtemp = in.readFloat();
        fieldShippedBy2 = in.readString();
        fieldShippedOn2 = in.readString();

// secondary concenteration
        author = in.readString();


        //Recieved in lab data
        tempLoggerIncluded = in.readInt();
        tempLoggerNotIncluded = in.readInt();
        labRecievedBy = in.readString();
        labRecievedOn = in.readString();
        labReceivedTemp = in.readFloat();
        sampleCold = in.readInt();
        sampleNotCold = in.readInt();

        //Secondary Concentration
        concentratedOn = in.readString();
        concentratedBy = in.readString();
        secConMethod = in.readString();


// field sections screen folan

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getTempreture() {
        return tempreture;
    }

    public void setTempreture(float tempreture) {
        this.tempreture = tempreture;
    }

    public String getQrCodeFormat() {
        return qrCodeFormat;
    }

    public void setQrCodeFormat(String qrCodeFormat) {
        this.qrCodeFormat = qrCodeFormat;
    }

    public String getQrCodeContent() {
        return qrCodeContent;
    }

    public void setQrCodeContent(String qrCodeContent) {
        this.qrCodeContent = qrCodeContent;
    }

    public String getElutedByPerson() {
        return elutedByPerson;
    }

    public void setElutedByPerson(String elutedByPerson) {
        this.elutedByPerson = elutedByPerson;
    }

    public String getElutedBydate() {
        return elutedBydate;
    }

    public void setElutedBydate(String elutedBydate) {
        this.elutedBydate = elutedBydate;
    }

    public float getRecoveredElutedmL() {
        return recoveredElutedmL;
    }

    public void setRecoveredElutedmL(float recoveredElutedmL) {
        this.recoveredElutedmL = recoveredElutedmL;
    }

    public int getRainYesterday() {
        return rainYesterday;
    }

    public void setRainYesterday(int rainYesterday) {
        this.rainYesterday = rainYesterday;
    }

    public int getRainToday() {
        return rainToday;
    }

    public void setRainToday(int rainToday) {
        this.rainToday = rainToday;
    }

    public float getFieldtemp() {
        return fieldtemp;
    }

    public void setFieldtemp(float fieldtemp) {
        this.fieldtemp = fieldtemp;
    }

    public String getFieldShippedBy() {
        return fieldShippedBy;
    }

    public void setFieldShippedBy(String fieldShippedBy) {
        this.fieldShippedBy = fieldShippedBy;
    }

    public String getFieldShippedOn() {
        return fieldShippedOn;
    }

    public void setFieldShippedOn(String fieldShippedOn) {
        this.fieldShippedOn = fieldShippedOn;
    }

    public float getFieldWaterPh() {
        return fieldWaterPh;
    }

    public void setFieldWaterPh(float fieldWaterPh) {
        this.fieldWaterPh = fieldWaterPh;
    }

    public float getFieldVolume() {
        return fieldVolume;
    }

    public void setFieldVolume(float fieldVolume) {
        this.fieldVolume = fieldVolume;
    }

    public float getFieldVolumeAfter() {
        return fieldVolumeAfter;
    }

    public void setFieldVolumeAfter(float fieldVolumeAfter) {
        this.fieldVolumeAfter = fieldVolumeAfter;
    }

    public float getFieldVolumeRemaining() {
        return fieldVolumeRemaining;
    }

    public void setFieldVolumeRemaining(float fieldVolumeRemaining) {
        this.fieldVolumeRemaining = fieldVolumeRemaining;
    }

    public int getShippedCold() {
        return shippedCold;
    }

    public void setShippedCold(int shippedCold) {
        this.shippedCold = shippedCold;
    }

    public float getFieldShippedtemp() {
        return fieldShippedtemp;
    }

    public void setFieldShippedtemp(float fieldShippedtemp) {
        this.fieldShippedtemp = fieldShippedtemp;
    }

    public String getFieldShippedBy2() {
        return fieldShippedBy2;
    }

    public void setFieldShippedBy2(String fieldShippedBy2) {
        this.fieldShippedBy2 = fieldShippedBy2;
    }

    public String getFieldShippedOn2() {
        return fieldShippedOn2;
    }

    public void setFieldShippedOn2(String fieldShippedOn2) {
        this.fieldShippedOn2 = fieldShippedOn2;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSample_id() {
        return sample_id;
    }

    public void setSample_id(String sample_id) {
        this.sample_id = sample_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getTempLoggerIncluded() {
        return tempLoggerIncluded;
    }

    public void setTempLoggerIncluded(int tempLoggerIncluded) {
        this.tempLoggerIncluded = tempLoggerIncluded;
    }

    public int getTempLoggerNotIncluded() {
        return tempLoggerNotIncluded;
    }

    public void setTempLoggerNotIncluded(int tempLoggerNotIncluded) {
        this.tempLoggerNotIncluded = tempLoggerNotIncluded;
    }

    public String getLabRecievedBy() {
        return labRecievedBy;
    }

    public void setLabRecievedBy(String labRecievedBy) {
        this.labRecievedBy = labRecievedBy;
    }

    public String getLabRecievedOn() {
        return labRecievedOn;
    }

    public void setLabRecievedOn(String labRecievedOn) {
        this.labRecievedOn = labRecievedOn;
    }

    public float getLabReceivedTemp() {
        return labReceivedTemp;
    }

    public void setLabReceivedTemp(float labReceivedTemp) {
        this.labReceivedTemp = labReceivedTemp;
    }

    public int getSampleCold() {
        return sampleCold;
    }

    public void setSampleCold(int sampleCold) {
        this.sampleCold = sampleCold;
    }

    public int getSampleNotCold() {
        return sampleNotCold;
    }

    public void setSampleNotCold(int sampleNotCold) {
        this.sampleNotCold = sampleNotCold;
    }

    public String getConcentratedOn() {
        return concentratedOn;
    }

    public void setConcentratedOn(String concentratedOn) {
        this.concentratedOn = concentratedOn;
    }

    public String getConcentratedBy() {
        return concentratedBy;
    }

    public void setConcentratedBy(String concentratedBy) {
        this.concentratedBy = concentratedBy;
    }

    public String getSecConMethod() {
        return secConMethod;
    }

    public void setSecConMethod(String secConMethod) {
        this.secConMethod = secConMethod;
    }


    // writing and serialization process


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeFloat(tempreture);

        dest.writeString(qrCodeContent);
        dest.writeString(qrCodeFormat);
        // API v1 fields
        dest.writeString(sample_id);
        dest.writeString(lat);
        dest.writeString(lng);


        dest.writeString(elutedByPerson);
        dest.writeString(elutedBydate);
        dest.writeFloat(recoveredElutedmL);


        // no field parts
        dest.writeInt(rainYesterday);
        dest.writeInt(rainToday);
        dest.writeFloat(fieldtemp);
        dest.writeString(fieldShippedBy);
        dest.writeString(fieldShippedOn);
        dest.writeFloat(fieldWaterPh);
        dest.writeFloat(fieldVolume);
        dest.writeFloat(fieldVolumeAfter);
        dest.writeInt(shippedCold);
        dest.writeFloat(fieldShippedtemp);
        dest.writeString(fieldShippedBy2);
        dest.writeString(fieldShippedOn2);


        // secondary concenteration
        dest.writeString(author);




        //Recieved in Lab
        dest.writeInt(tempLoggerIncluded);
        dest.writeInt(tempLoggerNotIncluded);
        dest.writeString(labRecievedBy);
        dest.writeString(labRecievedOn);
        dest.writeFloat(labReceivedTemp);
        dest.writeInt(sampleCold);
        dest.writeInt(sampleNotCold);

        //Secondary Concentration
        dest.writeString(concentratedOn);
        dest.writeString(concentratedBy);
        dest.writeString(secConMethod);


    }

    //reading back to object from serilization process - unmarshaling data
    public static final Creator<PolioScannerData> CREATOR = new Creator<PolioScannerData>() {
        @Override
        public PolioScannerData createFromParcel(Parcel in) {
            return new PolioScannerData(in);
        }

        @Override
        public PolioScannerData[] newArray(int size) {
            return new PolioScannerData[size];
        }
    };


}
