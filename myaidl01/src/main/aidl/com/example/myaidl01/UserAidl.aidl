// UserAidl.aidl
package com.example.myaidl01;

// Declare any non-default types here with import statements

interface UserAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    String getStr();
    int add(int num1,int num2);
}
