package org.obridge.test.database.context;

import org.obridge.test.database.objects.SampleTypeOne;

import java.util.List;

public class TestPackageCombinedTypesTest1 {


    private SampleTypeOne objectType;
    private List<SampleTypeOne> listOf;

    public SampleTypeOne getObjectType() {
        return this.objectType;
    }

    public void setObjectType(SampleTypeOne objectType) {
        this.objectType = objectType;
    }

    public List<SampleTypeOne> getListOf() {
        return this.listOf;
    }

    public void setListOf(List<SampleTypeOne> listOf) {
        this.listOf = listOf;
    }


}