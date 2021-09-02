import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { IListing } from 'app/shared/model/listing.model';
import { getEntities as getListings } from 'app/entities/listing/listing.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bookings.reducer';
import { IBookings } from 'app/shared/model/bookings.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BookingsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const rooms = useAppSelector(state => state.room.entities);
  const listings = useAppSelector(state => state.listing.entities);
  const bookingsEntity = useAppSelector(state => state.bookings.entity);
  const loading = useAppSelector(state => state.bookings.loading);
  const updating = useAppSelector(state => state.bookings.updating);
  const updateSuccess = useAppSelector(state => state.bookings.updateSuccess);

  const handleClose = () => {
    props.history.push('/bookings' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getRooms({}));
    dispatch(getListings({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.checkInDate = convertDateTimeToServer(values.checkInDate);
    values.checkOutDate = convertDateTimeToServer(values.checkOutDate);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedBy = convertDateTimeToServer(values.updatedBy);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...bookingsEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.userId.toString()),
      room: rooms.find(it => it.id.toString() === values.roomId.toString()),
      listing: listings.find(it => it.id.toString() === values.listingId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          checkInDate: displayDefaultDateTime(),
          checkOutDate: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          updatedBy: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...bookingsEntity,
          checkInDate: convertDateTimeFromServer(bookingsEntity.checkInDate),
          checkOutDate: convertDateTimeFromServer(bookingsEntity.checkOutDate),
          createdDate: convertDateTimeFromServer(bookingsEntity.createdDate),
          updatedBy: convertDateTimeFromServer(bookingsEntity.updatedBy),
          updateDate: convertDateTimeFromServer(bookingsEntity.updateDate),
          userId: bookingsEntity?.user?.id,
          roomId: bookingsEntity?.room?.id,
          listingId: bookingsEntity?.listing?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tripperNestApp.bookings.home.createOrEditLabel" data-cy="BookingsCreateUpdateHeading">
            <Translate contentKey="tripperNestApp.bookings.home.createOrEditLabel">Create or edit a Bookings</Translate>
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
                  id="bookings-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('tripperNestApp.bookings.name')} id="bookings-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('tripperNestApp.bookings.checkInDate')}
                id="bookings-checkInDate"
                name="checkInDate"
                data-cy="checkInDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.bookings.checkOutDate')}
                id="bookings-checkOutDate"
                name="checkOutDate"
                data-cy="checkOutDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.bookings.pricePerNight')}
                id="bookings-pricePerNight"
                name="pricePerNight"
                data-cy="pricePerNight"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.bookings.numOfNights')}
                id="bookings-numOfNights"
                name="numOfNights"
                data-cy="numOfNights"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.bookings.createdBy')}
                id="bookings-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.bookings.createdDate')}
                id="bookings-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.bookings.updatedBy')}
                id="bookings-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.bookings.updateDate')}
                id="bookings-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="bookings-user"
                name="userId"
                data-cy="user"
                label={translate('tripperNestApp.bookings.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.email}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="bookings-room"
                name="roomId"
                data-cy="room"
                label={translate('tripperNestApp.bookings.room')}
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
              <ValidatedField
                id="bookings-listing"
                name="listingId"
                data-cy="listing"
                label={translate('tripperNestApp.bookings.listing')}
                type="select"
              >
                <option value="" key="0" />
                {listings
                  ? listings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bookings" replace color="info">
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

export default BookingsUpdate;
