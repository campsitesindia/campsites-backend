import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IListingType } from 'app/shared/model/listing-type.model';
import { getEntities as getListingTypes } from 'app/entities/listing-type/listing-type.reducer';
import { IRating } from 'app/shared/model/rating.model';
import { getEntities as getRatings } from 'app/entities/rating/rating.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IFeatures } from 'app/shared/model/features.model';
import { getEntities as getFeatures } from 'app/entities/features/features.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './listing.reducer';
import { IListing } from 'app/shared/model/listing.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const listingTypes = useAppSelector(state => state.listingType.entities);
  const ratings = useAppSelector(state => state.rating.entities);
  const locations = useAppSelector(state => state.location.entities);
  const features = useAppSelector(state => state.features.entities);
  const rooms = useAppSelector(state => state.room.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const listingEntity = useAppSelector(state => state.listing.entity);
  const loading = useAppSelector(state => state.listing.loading);
  const updating = useAppSelector(state => state.listing.updating);
  const updateSuccess = useAppSelector(state => state.listing.updateSuccess);

  const handleClose = () => {
    props.history.push('/listing' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getListingTypes({}));
    dispatch(getRatings({}));
    dispatch(getLocations({}));
    dispatch(getFeatures({}));
    dispatch(getRooms({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedBy = convertDateTimeToServer(values.updatedBy);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...listingEntity,
      ...values,
      listingType: listingTypes.find(it => it.id.toString() === values.listingTypeId.toString()),
      rating: ratings.find(it => it.id.toString() === values.ratingId.toString()),
      location: locations.find(it => it.id.toString() === values.locationId.toString()),
      feature: features.find(it => it.id.toString() === values.featureId.toString()),
      room: rooms.find(it => it.id.toString() === values.roomId.toString()),
      owner: users.find(it => it.id.toString() === values.ownerId.toString()),
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
          createdDate: displayDefaultDateTime(),
          updatedBy: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...listingEntity,
          createdDate: convertDateTimeFromServer(listingEntity.createdDate),
          updatedBy: convertDateTimeFromServer(listingEntity.updatedBy),
          updateDate: convertDateTimeFromServer(listingEntity.updateDate),
          listingTypeId: listingEntity?.listingType?.id,
          ratingId: listingEntity?.rating?.id,
          locationId: listingEntity?.location?.id,
          featureId: listingEntity?.feature?.id,
          roomId: listingEntity?.room?.id,
          ownerId: listingEntity?.owner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tripperNestApp.listing.home.createOrEditLabel" data-cy="ListingCreateUpdateHeading">
            <Translate contentKey="tripperNestApp.listing.home.createOrEditLabel">Create or edit a Listing</Translate>
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
                  id="listing-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('tripperNestApp.listing.address')}
                id="listing-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.latitude')}
                id="listing-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.longitude')}
                id="listing-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField label={translate('tripperNestApp.listing.url')} id="listing-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                label={translate('tripperNestApp.listing.title')}
                id="listing-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.content')}
                id="listing-content"
                name="content"
                data-cy="content"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.thumbnail')}
                id="listing-thumbnail"
                name="thumbnail"
                data-cy="thumbnail"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.isFeatured')}
                id="listing-isFeatured"
                name="isFeatured"
                data-cy="isFeatured"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.phone')}
                id="listing-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.email')}
                id="listing-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.website')}
                id="listing-website"
                name="website"
                data-cy="website"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.comment')}
                id="listing-comment"
                name="comment"
                data-cy="comment"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.disableBooking')}
                id="listing-disableBooking"
                name="disableBooking"
                data-cy="disableBooking"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.viewCount')}
                id="listing-viewCount"
                name="viewCount"
                data-cy="viewCount"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.createdBy')}
                id="listing-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.createdDate')}
                id="listing-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.updatedBy')}
                id="listing-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.listing.updateDate')}
                id="listing-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="listing-listingType"
                name="listingTypeId"
                data-cy="listingType"
                label={translate('tripperNestApp.listing.listingType')}
                type="select"
              >
                <option value="" key="0" />
                {listingTypes
                  ? listingTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="listing-rating"
                name="ratingId"
                data-cy="rating"
                label={translate('tripperNestApp.listing.rating')}
                type="select"
              >
                <option value="" key="0" />
                {ratings
                  ? ratings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="listing-location"
                name="locationId"
                data-cy="location"
                label={translate('tripperNestApp.listing.location')}
                type="select"
              >
                <option value="" key="0" />
                {locations
                  ? locations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="listing-feature"
                name="featureId"
                data-cy="feature"
                label={translate('tripperNestApp.listing.feature')}
                type="select"
              >
                <option value="" key="0" />
                {features
                  ? features.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="listing-room" name="roomId" data-cy="room" label={translate('tripperNestApp.listing.room')} type="select">
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
                id="listing-owner"
                name="ownerId"
                data-cy="owner"
                label={translate('tripperNestApp.listing.owner')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/listing" replace color="info">
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

export default ListingUpdate;
