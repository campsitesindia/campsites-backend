import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBookings } from 'app/shared/model/bookings.model';
import { getEntities as getBookings } from 'app/entities/bookings/bookings.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rooms-in-booking.reducer';
import { IRoomsInBooking } from 'app/shared/model/rooms-in-booking.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomsInBookingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bookings = useAppSelector(state => state.bookings.entities);
  const rooms = useAppSelector(state => state.room.entities);
  const roomsInBookingEntity = useAppSelector(state => state.roomsInBooking.entity);
  const loading = useAppSelector(state => state.roomsInBooking.loading);
  const updating = useAppSelector(state => state.roomsInBooking.updating);
  const updateSuccess = useAppSelector(state => state.roomsInBooking.updateSuccess);

  const handleClose = () => {
    props.history.push('/rooms-in-booking' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBookings({}));
    dispatch(getRooms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...roomsInBookingEntity,
      ...values,
      bookings: bookings.find(it => it.id.toString() === values.bookingsId.toString()),
      room: rooms.find(it => it.id.toString() === values.roomId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...roomsInBookingEntity,
          bookingsId: roomsInBookingEntity?.bookings?.id,
          roomId: roomsInBookingEntity?.room?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.roomsInBooking.home.createOrEditLabel" data-cy="RoomsInBookingCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.roomsInBooking.home.createOrEditLabel">Create or edit a RoomsInBooking</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="rooms-in-booking-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="rooms-in-booking-bookings"
                name="bookingsId"
                data-cy="bookings"
                label={translate('campsitesindiaApp.roomsInBooking.bookings')}
                type="select"
              >
                <option value="" key="0" />
                {bookings
                  ? bookings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="rooms-in-booking-room"
                name="roomId"
                data-cy="room"
                label={translate('campsitesindiaApp.roomsInBooking.room')}
                type="select"
              >
                <option value="" key="0" />
                {rooms
                  ? rooms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.roomNumber}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rooms-in-booking" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RoomsInBookingUpdate;
