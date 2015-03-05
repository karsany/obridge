package org.obridge.test.database.objects;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;

public class SampleTypeOne {


    private String attrVarchar;
    private String attrClob;
    private Integer attrInt;
    private BigDecimal attrBigdec1;
    private BigDecimal attrBigdec2;
    private Date dateA;
    private Timestamp timestB;
    private Timestamp timestC;

    public String getAttrVarchar() {
        return this.attrVarchar;
    }

    public void setAttrVarchar(String attrVarchar) {
        this.attrVarchar = attrVarchar;
    }

    public String getAttrClob() {
        return this.attrClob;
    }

    public void setAttrClob(String attrClob) {
        this.attrClob = attrClob;
    }

    public Integer getAttrInt() {
        return this.attrInt;
    }

    public void setAttrInt(Integer attrInt) {
        this.attrInt = attrInt;
    }

    public BigDecimal getAttrBigdec1() {
        return this.attrBigdec1;
    }

    public void setAttrBigdec1(BigDecimal attrBigdec1) {
        this.attrBigdec1 = attrBigdec1;
    }

    public BigDecimal getAttrBigdec2() {
        return this.attrBigdec2;
    }

    public void setAttrBigdec2(BigDecimal attrBigdec2) {
        this.attrBigdec2 = attrBigdec2;
    }

    public Date getDateA() {
        return this.dateA;
    }

    public void setDateA(Date dateA) {
        this.dateA = dateA;
    }

    public Timestamp getTimestB() {
        return this.timestB;
    }

    public void setTimestB(Timestamp timestB) {
        this.timestB = timestB;
    }

    public Timestamp getTimestC() {
        return this.timestC;
    }

    public void setTimestC(Timestamp timestC) {
        this.timestC = timestC;
    }


}