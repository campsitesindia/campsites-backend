package com.dd.campsites.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.dd.campsites.domain.Bookings} entity. This class is used
 * in {@link com.dd.campsites.web.rest.BookingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bookings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter checkInDate;

    private InstantFilter checkOutDate;

    private DoubleFilter pricePerNight;

    private DoubleFilter childPricePerNight;

    private IntegerFilter numOfNights;

    private StringFilter razorpayPaymentId;

    private StringFilter razorpayOrderId;

    private StringFilter razorpaySignature;

    private DoubleFilter discount;

    private DoubleFilter totalAmount;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter updatedBy;

    private InstantFilter updateDate;

    private LongFilter userId;

    private LongFilter listingId;

    private LongFilter invoiceId;

    public BookingsCriteria() {}

    public BookingsCriteria(BookingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.checkInDate = other.checkInDate == null ? null : other.checkInDate.copy();
        this.checkOutDate = other.checkOutDate == null ? null : other.checkOutDate.copy();
        this.pricePerNight = other.pricePerNight == null ? null : other.pricePerNight.copy();
        this.childPricePerNight = other.childPricePerNight == null ? null : other.childPricePerNight.copy();
        this.numOfNights = other.numOfNights == null ? null : other.numOfNights.copy();
        this.razorpayPaymentId = other.razorpayPaymentId == null ? null : other.razorpayPaymentId.copy();
        this.razorpayOrderId = other.razorpayOrderId == null ? null : other.razorpayOrderId.copy();
        this.razorpaySignature = other.razorpaySignature == null ? null : other.razorpaySignature.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.listingId = other.listingId == null ? null : other.listingId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
    }

    @Override
    public BookingsCriteria copy() {
        return new BookingsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public InstantFilter getCheckInDate() {
        return checkInDate;
    }

    public InstantFilter checkInDate() {
        if (checkInDate == null) {
            checkInDate = new InstantFilter();
        }
        return checkInDate;
    }

    public void setCheckInDate(InstantFilter checkInDate) {
        this.checkInDate = checkInDate;
    }

    public InstantFilter getCheckOutDate() {
        return checkOutDate;
    }

    public InstantFilter checkOutDate() {
        if (checkOutDate == null) {
            checkOutDate = new InstantFilter();
        }
        return checkOutDate;
    }

    public void setCheckOutDate(InstantFilter checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public DoubleFilter getPricePerNight() {
        return pricePerNight;
    }

    public DoubleFilter pricePerNight() {
        if (pricePerNight == null) {
            pricePerNight = new DoubleFilter();
        }
        return pricePerNight;
    }

    public void setPricePerNight(DoubleFilter pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public DoubleFilter getChildPricePerNight() {
        return childPricePerNight;
    }

    public DoubleFilter childPricePerNight() {
        if (childPricePerNight == null) {
            childPricePerNight = new DoubleFilter();
        }
        return childPricePerNight;
    }

    public void setChildPricePerNight(DoubleFilter childPricePerNight) {
        this.childPricePerNight = childPricePerNight;
    }

    public IntegerFilter getNumOfNights() {
        return numOfNights;
    }

    public IntegerFilter numOfNights() {
        if (numOfNights == null) {
            numOfNights = new IntegerFilter();
        }
        return numOfNights;
    }

    public void setNumOfNights(IntegerFilter numOfNights) {
        this.numOfNights = numOfNights;
    }

    public StringFilter getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public StringFilter razorpayPaymentId() {
        if (razorpayPaymentId == null) {
            razorpayPaymentId = new StringFilter();
        }
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(StringFilter razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public StringFilter getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public StringFilter razorpayOrderId() {
        if (razorpayOrderId == null) {
            razorpayOrderId = new StringFilter();
        }
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(StringFilter razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public StringFilter getRazorpaySignature() {
        return razorpaySignature;
    }

    public StringFilter razorpaySignature() {
        if (razorpaySignature == null) {
            razorpaySignature = new StringFilter();
        }
        return razorpaySignature;
    }

    public void setRazorpaySignature(StringFilter razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }

    public DoubleFilter getDiscount() {
        return discount;
    }

    public DoubleFilter discount() {
        if (discount == null) {
            discount = new DoubleFilter();
        }
        return discount;
    }

    public void setDiscount(DoubleFilter discount) {
        this.discount = discount;
    }

    public DoubleFilter getTotalAmount() {
        return totalAmount;
    }

    public DoubleFilter totalAmount() {
        if (totalAmount == null) {
            totalAmount = new DoubleFilter();
        }
        return totalAmount;
    }

    public void setTotalAmount(DoubleFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public InstantFilter getUpdatedBy() {
        return updatedBy;
    }

    public InstantFilter updatedBy() {
        if (updatedBy == null) {
            updatedBy = new InstantFilter();
        }
        return updatedBy;
    }

    public void setUpdatedBy(InstantFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdateDate() {
        return updateDate;
    }

    public InstantFilter updateDate() {
        if (updateDate == null) {
            updateDate = new InstantFilter();
        }
        return updateDate;
    }

    public void setUpdateDate(InstantFilter updateDate) {
        this.updateDate = updateDate;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getListingId() {
        return listingId;
    }

    public LongFilter listingId() {
        if (listingId == null) {
            listingId = new LongFilter();
        }
        return listingId;
    }

    public void setListingId(LongFilter listingId) {
        this.listingId = listingId;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public LongFilter invoiceId() {
        if (invoiceId == null) {
            invoiceId = new LongFilter();
        }
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookingsCriteria that = (BookingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(checkInDate, that.checkInDate) &&
            Objects.equals(checkOutDate, that.checkOutDate) &&
            Objects.equals(pricePerNight, that.pricePerNight) &&
            Objects.equals(childPricePerNight, that.childPricePerNight) &&
            Objects.equals(numOfNights, that.numOfNights) &&
            Objects.equals(razorpayPaymentId, that.razorpayPaymentId) &&
            Objects.equals(razorpayOrderId, that.razorpayOrderId) &&
            Objects.equals(razorpaySignature, that.razorpaySignature) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(listingId, that.listingId) &&
            Objects.equals(invoiceId, that.invoiceId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            checkInDate,
            checkOutDate,
            pricePerNight,
            childPricePerNight,
            numOfNights,
            razorpayPaymentId,
            razorpayOrderId,
            razorpaySignature,
            discount,
            totalAmount,
            createdBy,
            createdDate,
            updatedBy,
            updateDate,
            userId,
            listingId,
            invoiceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (checkInDate != null ? "checkInDate=" + checkInDate + ", " : "") +
            (checkOutDate != null ? "checkOutDate=" + checkOutDate + ", " : "") +
            (pricePerNight != null ? "pricePerNight=" + pricePerNight + ", " : "") +
            (childPricePerNight != null ? "childPricePerNight=" + childPricePerNight + ", " : "") +
            (numOfNights != null ? "numOfNights=" + numOfNights + ", " : "") +
            (razorpayPaymentId != null ? "razorpayPaymentId=" + razorpayPaymentId + ", " : "") +
            (razorpayOrderId != null ? "razorpayOrderId=" + razorpayOrderId + ", " : "") +
            (razorpaySignature != null ? "razorpaySignature=" + razorpaySignature + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (listingId != null ? "listingId=" + listingId + ", " : "") +
            (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
            "}";
    }
}
