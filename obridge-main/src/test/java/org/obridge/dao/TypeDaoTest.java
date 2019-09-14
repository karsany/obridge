package org.obridge.dao;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.obridge.BaseTest;
import org.obridge.model.data.Type;
import org.obridge.model.data.TypeAttribute;
import org.obridge.util.MustacheRunner;

import java.util.List;

public class TypeDaoTest extends BaseTest {

    public static final String SAMPLE_TYPE_LISTS = "SAMPLE_TYPE_LISTS";
    private static final String SAMPLE_TYPE_ONE = "SAMPLE_TYPE_ONE";
    private static final String SAMPLE_TYPE_ONE_LIST = "SAMPLE_TYPE_ONE_LIST";
    private static final String SAMPLE_TYPE_TWO = "SAMPLE_TYPE_TWO";
    private static final String NOT_EXISTING_TYPE = "NOT_EXISTING_TYPE";
    private TypeDao typeDao;

    @Before
    public void init2() {
        typeDao = new TypeDao(ds);
    }

    @Test
    public void testGetTypeList() {
        List<String> typeList = typeDao.getTypeList(null);
        Assert.assertTrue(typeList.contains(SAMPLE_TYPE_ONE));
        Assert.assertTrue(typeList.contains(SAMPLE_TYPE_TWO));
        Assert.assertFalse(typeList.contains(SAMPLE_TYPE_ONE_LIST));
        Assert.assertFalse(typeList.contains(NOT_EXISTING_TYPE));
    }

    @Test
    public void testGetTypeAttributes() {
        List<TypeAttribute> typeAttributes = typeDao.getTypeAttributes(SAMPLE_TYPE_ONE);
        Assert.assertEquals(9, typeAttributes.size());
        Assert.assertEquals("attrVarchar", typeAttributes.get(0).getJavaPropertyName());
        Assert.assertEquals("String", typeAttributes.get(0).getJavaDataType());

    }

    @Test
    public void testConverterMustache() {
        List<TypeAttribute> typeAttributes = typeDao.getTypeAttributes(SAMPLE_TYPE_ONE);
        Type t = new Type();
        t.setTypeName(SAMPLE_TYPE_ONE);
        t.setAttributeList(typeAttributes);

        MustacheRunner.build("converter.mustache", t);

    }

}