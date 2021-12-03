package com.dd.campsites.campsitesindia.razorpay.payment.model;

import com.dd.campsites.campsitesindia.razorpay.route.model.Notes;

public class OrderEntity {

    private String id;
    private String entity;
    private Integer amount;
    private Integer amountPaid;
    private Integer amountDue;
    private String currency;
    private String receipt;
    private String status;
    private Integer attempts;
    private Notes notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderEntity withId(String id) {
        this.id = id;
        return this;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public OrderEntity withEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public OrderEntity withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Integer getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    public OrderEntity withAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public Integer getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Integer amountDue) {
        this.amountDue = amountDue;
    }

    public OrderEntity withAmountDue(Integer amountDue) {
        this.amountDue = amountDue;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OrderEntity withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public OrderEntity withReceipt(String receipt) {
        this.receipt = receipt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderEntity withStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public OrderEntity withAttempts(Integer attempts) {
        this.attempts = attempts;
        return this;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public OrderEntity withNotes(Notes notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(OrderEntity.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("entity");
        sb.append('=');
        sb.append(((this.entity == null) ? "<null>" : this.entity));
        sb.append(',');
        sb.append("amount");
        sb.append('=');
        sb.append(((this.amount == null) ? "<null>" : this.amount));
        sb.append(',');
        sb.append("amountPaid");
        sb.append('=');
        sb.append(((this.amountPaid == null) ? "<null>" : this.amountPaid));
        sb.append(',');
        sb.append("amountDue");
        sb.append('=');
        sb.append(((this.amountDue == null) ? "<null>" : this.amountDue));
        sb.append(',');
        sb.append("currency");
        sb.append('=');
        sb.append(((this.currency == null) ? "<null>" : this.currency));
        sb.append(',');
        sb.append("receipt");
        sb.append('=');
        sb.append(((this.receipt == null) ? "<null>" : this.receipt));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null) ? "<null>" : this.status));
        sb.append(',');
        sb.append("attempts");
        sb.append('=');
        sb.append(((this.attempts == null) ? "<null>" : this.attempts));
        sb.append(',');
        sb.append("notes");
        sb.append('=');
        sb.append(((this.notes == null) ? "<null>" : this.notes));
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
        result = ((result * 31) + ((this.amountDue == null) ? 0 : this.amountDue.hashCode()));
        result = ((result * 31) + ((this.amount == null) ? 0 : this.amount.hashCode()));
        result = ((result * 31) + ((this.notes == null) ? 0 : this.notes.hashCode()));
        result = ((result * 31) + ((this.amountPaid == null) ? 0 : this.amountPaid.hashCode()));
        result = ((result * 31) + ((this.currency == null) ? 0 : this.currency.hashCode()));
        result = ((result * 31) + ((this.receipt == null) ? 0 : this.receipt.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.entity == null) ? 0 : this.entity.hashCode()));
        result = ((result * 31) + ((this.status == null) ? 0 : this.status.hashCode()));
        result = ((result * 31) + ((this.attempts == null) ? 0 : this.attempts.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OrderEntity) == false) {
            return false;
        }
        OrderEntity rhs = ((OrderEntity) other);
        return (
            (
                (
                    (
                        (
                            (
                                (
                                    (
                                        (
                                            (
                                                (this.amountDue == rhs.amountDue) ||
                                                ((this.amountDue != null) && this.amountDue.equals(rhs.amountDue))
                                            ) &&
                                            ((this.amount == rhs.amount) || ((this.amount != null) && this.amount.equals(rhs.amount)))
                                        ) &&
                                        ((this.notes == rhs.notes) || ((this.notes != null) && this.notes.equals(rhs.notes)))
                                    ) &&
                                    (
                                        (this.amountPaid == rhs.amountPaid) ||
                                        ((this.amountPaid != null) && this.amountPaid.equals(rhs.amountPaid))
                                    )
                                ) &&
                                ((this.currency == rhs.currency) || ((this.currency != null) && this.currency.equals(rhs.currency)))
                            ) &&
                            ((this.receipt == rhs.receipt) || ((this.receipt != null) && this.receipt.equals(rhs.receipt)))
                        ) &&
                        ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id)))
                    ) &&
                    ((this.entity == rhs.entity) || ((this.entity != null) && this.entity.equals(rhs.entity)))
                ) &&
                ((this.status == rhs.status) || ((this.status != null) && this.status.equals(rhs.status)))
            ) &&
            ((this.attempts == rhs.attempts) || ((this.attempts != null) && this.attempts.equals(rhs.attempts)))
        );
    }
}
