package com.dd.campsites.campsitesindia.razorpay.route.model;

import java.util.List;

public class Transfer {

    private String account;
    private Integer amount;
    private String currency;
    private Notes notes;
    private List<String> linkedAccountNotes = null;
    private Boolean onHold;
    private Integer onHoldUntil;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Transfer withAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Transfer withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Transfer withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Transfer withNotes(Notes notes) {
        this.notes = notes;
        return this;
    }

    public List<String> getLinkedAccountNotes() {
        return linkedAccountNotes;
    }

    public void setLinkedAccountNotes(List<String> linkedAccountNotes) {
        this.linkedAccountNotes = linkedAccountNotes;
    }

    public Transfer withLinkedAccountNotes(List<String> linkedAccountNotes) {
        this.linkedAccountNotes = linkedAccountNotes;
        return this;
    }

    public Boolean getOnHold() {
        return onHold;
    }

    public void setOnHold(Boolean onHold) {
        this.onHold = onHold;
    }

    public Transfer withOnHold(Boolean onHold) {
        this.onHold = onHold;
        return this;
    }

    public Integer getOnHoldUntil() {
        return onHoldUntil;
    }

    public void setOnHoldUntil(Integer onHoldUntil) {
        this.onHoldUntil = onHoldUntil;
    }

    public Transfer withOnHoldUntil(Integer onHoldUntil) {
        this.onHoldUntil = onHoldUntil;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Transfer.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("account");
        sb.append('=');
        sb.append(((this.account == null) ? "<null>" : this.account));
        sb.append(',');
        sb.append("amount");
        sb.append('=');
        sb.append(((this.amount == null) ? "<null>" : this.amount));
        sb.append(',');
        sb.append("currency");
        sb.append('=');
        sb.append(((this.currency == null) ? "<null>" : this.currency));
        sb.append(',');
        sb.append("notes");
        sb.append('=');
        sb.append(((this.notes == null) ? "<null>" : this.notes));
        sb.append(',');
        sb.append("linkedAccountNotes");
        sb.append('=');
        sb.append(((this.linkedAccountNotes == null) ? "<null>" : this.linkedAccountNotes));
        sb.append(',');
        sb.append("onHold");
        sb.append('=');
        sb.append(((this.onHold == null) ? "<null>" : this.onHold));
        sb.append(',');
        sb.append("onHoldUntil");
        sb.append('=');
        sb.append(((this.onHoldUntil == null) ? "<null>" : this.onHoldUntil));
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
        result = ((result * 31) + ((this.onHoldUntil == null) ? 0 : this.onHoldUntil.hashCode()));
        result = ((result * 31) + ((this.amount == null) ? 0 : this.amount.hashCode()));
        result = ((result * 31) + ((this.notes == null) ? 0 : this.notes.hashCode()));
        result = ((result * 31) + ((this.onHold == null) ? 0 : this.onHold.hashCode()));
        result = ((result * 31) + ((this.currency == null) ? 0 : this.currency.hashCode()));
        result = ((result * 31) + ((this.linkedAccountNotes == null) ? 0 : this.linkedAccountNotes.hashCode()));
        result = ((result * 31) + ((this.account == null) ? 0 : this.account.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Transfer) == false) {
            return false;
        }
        Transfer rhs = ((Transfer) other);
        return (
            (
                (
                    (
                        (
                            (
                                (
                                    (this.onHoldUntil == rhs.onHoldUntil) ||
                                    ((this.onHoldUntil != null) && this.onHoldUntil.equals(rhs.onHoldUntil))
                                ) &&
                                ((this.amount == rhs.amount) || ((this.amount != null) && this.amount.equals(rhs.amount)))
                            ) &&
                            ((this.notes == rhs.notes) || ((this.notes != null) && this.notes.equals(rhs.notes)))
                        ) &&
                        ((this.onHold == rhs.onHold) || ((this.onHold != null) && this.onHold.equals(rhs.onHold)))
                    ) &&
                    ((this.currency == rhs.currency) || ((this.currency != null) && this.currency.equals(rhs.currency)))
                ) &&
                (
                    (this.linkedAccountNotes == rhs.linkedAccountNotes) ||
                    ((this.linkedAccountNotes != null) && this.linkedAccountNotes.equals(rhs.linkedAccountNotes))
                )
            ) &&
            ((this.account == rhs.account) || ((this.account != null) && this.account.equals(rhs.account)))
        );
    }
}
