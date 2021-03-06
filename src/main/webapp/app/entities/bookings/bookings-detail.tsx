import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bookings.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BookingsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bookingsEntity = useAppSelector(state => state.bookings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookingsDetailsHeading">
          <Translate contentKey="campsitesindiaApp.bookings.detail.title">Bookings</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="campsitesindiaApp.bookings.name">Name</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.name}</dd>
          <dt>
            <span id="checkInDate">
              <Translate contentKey="campsitesindiaApp.bookings.checkInDate">Check In Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingsEntity.checkInDate ? <TextFormat value={bookingsEntity.checkInDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="checkOutDate">
              <Translate contentKey="campsitesindiaApp.bookings.checkOutDate">Check Out Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingsEntity.checkOutDate ? <TextFormat value={bookingsEntity.checkOutDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="pricePerNight">
              <Translate contentKey="campsitesindiaApp.bookings.pricePerNight">Price Per Night</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.pricePerNight}</dd>
          <dt>
            <span id="childPricePerNight">
              <Translate contentKey="campsitesindiaApp.bookings.childPricePerNight">Child Price Per Night</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.childPricePerNight}</dd>
          <dt>
            <span id="numOfNights">
              <Translate contentKey="campsitesindiaApp.bookings.numOfNights">Num Of Nights</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.numOfNights}</dd>
          <dt>
            <span id="razorpayPaymentId">
              <Translate contentKey="campsitesindiaApp.bookings.razorpayPaymentId">Razorpay Payment Id</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.razorpayPaymentId}</dd>
          <dt>
            <span id="razorpayOrderId">
              <Translate contentKey="campsitesindiaApp.bookings.razorpayOrderId">Razorpay Order Id</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.razorpayOrderId}</dd>
          <dt>
            <span id="razorpaySignature">
              <Translate contentKey="campsitesindiaApp.bookings.razorpaySignature">Razorpay Signature</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.razorpaySignature}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="campsitesindiaApp.bookings.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.discount}</dd>
          <dt>
            <span id="totalAmount">
              <Translate contentKey="campsitesindiaApp.bookings.totalAmount">Total Amount</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.totalAmount}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.bookings.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.bookings.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingsEntity.createdDate ? <TextFormat value={bookingsEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.bookings.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{bookingsEntity.updatedBy ? <TextFormat value={bookingsEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.bookings.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingsEntity.updateDate ? <TextFormat value={bookingsEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.bookings.user">User</Translate>
          </dt>
          <dd>{bookingsEntity.user ? bookingsEntity.user.email : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.bookings.listing">Listing</Translate>
          </dt>
          <dd>{bookingsEntity.listing ? bookingsEntity.listing.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/bookings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bookings/${bookingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookingsDetail;
