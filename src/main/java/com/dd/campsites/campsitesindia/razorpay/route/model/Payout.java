package com.dd.campsites.campsitesindia.razorpay.route.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Payout {

    private List<Transfer> transfers = null;

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public Payout withTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Payout.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("transfers");
        sb.append('=');
        sb.append(((this.transfers == null) ? "<null>" : this.transfers));
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
        result = ((result * 31) + ((this.transfers == null) ? 0 : this.transfers.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Payout) == false) {
            return false;
        }
        Payout rhs = ((Payout) other);
        return ((this.transfers == rhs.transfers) || ((this.transfers != null) && this.transfers.equals(rhs.transfers)));
    }
}
