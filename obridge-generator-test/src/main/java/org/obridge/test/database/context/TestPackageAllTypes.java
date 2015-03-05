package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;

public class TestPackageAllTypes {


    private BigDecimal n;
    private Integer bi;
    private Integer pi;
    private String vch;
    private String nvch;
    private String ch;
    private String nch;
    private Date d;
    private Timestamp ts;
    private String cl;
    private Boolean b;
    private List<SampleTypeOne> tbl;
    private SampleTypeOne o;

    public BigDecimal getN() {
        return this.n;
    }

    public void setN(BigDecimal n) {
        this.n = n;
    }

    public Integer getBi() {
        return this.bi;
    }

    public void setBi(Integer bi) {
        this.bi = bi;
    }

    public Integer getPi() {
        return this.pi;
    }

    public void setPi(Integer pi) {
        this.pi = pi;
    }

    public String getVch() {
        return this.vch;
    }

    public void setVch(String vch) {
        this.vch = vch;
    }

    public String getNvch() {
        return this.nvch;
    }

    public void setNvch(String nvch) {
        this.nvch = nvch;
    }

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getNch() {
        return this.nch;
    }

    public void setNch(String nch) {
        this.nch = nch;
    }

    public Date getD() {
        return this.d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public Timestamp getTs() {
        return this.ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public String getCl() {
        return this.cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public Boolean getB() {
        return this.b;
    }

    public void setB(Boolean b) {
        this.b = b;
    }

    public List<SampleTypeOne> getTbl() {
        return this.tbl;
    }

    public void setTbl(List<SampleTypeOne> tbl) {
        this.tbl = tbl;
    }

    public SampleTypeOne getO() {
        return this.o;
    }

    public void setO(SampleTypeOne o) {
        this.o = o;
    }


}