import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './rooms-in-booking.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomsInBookingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const roomsInBookingEntity = useAppSelector(state => state.roomsInBooking.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roomsInBookingDetailsHeading">
          <Translate contentKey="campsitesindiaApp.roomsInBooking.detail.title">RoomsInBooking</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{roomsInBookingEntity.id}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.roomsInBooking.bookings">Bookings</Translate>
          </dt>
          <dd>{roomsInBookingEntity.bookings ? roomsInBookingEntity.bookings.name : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.roomsInBooking.room">Room</Translate>
          </dt>
          <dd>{roomsInBookingEntity.room ? roomsInBookingEntity.room.roomNumber : ''}</dd>
        </dl>
        <Button tag={Link} to="/rooms-in-booking" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rooms-in-booking/${roomsInBookingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomsInBookingDetail;
