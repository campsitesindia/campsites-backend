package com.dd.campsites.campsitesindia.razorpay.route.model;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Notes {

    private String name;
    private String rollNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Notes withName(String name) {
        this.name = name;
        return this;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public Notes withRollNo(String rollNo) {
        this.rollNo = rollNo;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Notes.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("rollNo");
        sb.append('=');
        sb.append(((this.rollNo == null) ? "<null>" : this.rollNo));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.rollNo == null) ? 0 : this.rollNo.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Notes) == false) {
            return false;
        }
        Notes rhs = ((Notes) other);
        return (
            ((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name))) &&
            ((this.rollNo == rhs.rollNo) || ((this.rollNo != null) && this.rollNo.equals(rhs.rollNo)))
        );
    }
}
