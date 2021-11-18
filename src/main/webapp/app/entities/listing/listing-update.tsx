import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IListingType } from 'app/shared/model/listing-type.model';
import { getEntities as getListingTypes } from 'app/entities/listing-type/listing-type.reducer';
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

  const locations = useAppSelector(state => state.location.entities);
  const listingTypes = useAppSelector(state => state.listingType.entities);
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

    dispatch(getLocations({}));
    dispatch(getListingTypes({}));
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
      location: locations.find(it => it.id.toString() === values.locationId.toString()),
      listingType: listingTypes.find(it => it.id.toString() === values.listingTypeId.toString()),
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
          locationId: listingEntity?.location?.id,
          listingTypeId: listingEntity?.listingType?.id,
          ownerId: listingEntity?.owner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.listing.home.createOrEditLabel" data-cy="ListingCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.listing.home.createOrEditLabel">Create or edit a Listing</Translate>
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
                label={translate('campsitesindiaApp.listing.address')}
                id="listing-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.latitude')}
                id="listing-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.longitude')}
                id="listing-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField label={translate('campsitesindiaApp.listing.url')} id="listing-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.title')}
                id="listing-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.content')}
                id="listing-content"
                name="content"
                data-cy="content"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.thumbnail')}
                id="listing-thumbnail"
                name="thumbnail"
                data-cy="thumbnail"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.isFeatured')}
                id="listing-isFeatured"
                name="isFeatured"
                data-cy="isFeatured"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.pricePerPerson')}
                id="listing-pricePerPerson"
                name="pricePerPerson"
                data-cy="pricePerPerson"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.pricePerChild')}
                id="listing-pricePerChild"
                name="pricePerChild"
                data-cy="pricePerChild"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.discount')}
                id="listing-discount"
                name="discount"
                data-cy="discount"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.isPublished')}
                id="listing-isPublished"
                name="isPublished"
                data-cy="isPublished"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.phone')}
                id="listing-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.email')}
                id="listing-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.website')}
                id="listing-website"
                name="website"
                data-cy="website"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.comment')}
                id="listing-comment"
                name="comment"
                data-cy="comment"
                type="textarea"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.disableBooking')}
                id="listing-disableBooking"
                name="disableBooking"
                data-cy="disableBooking"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.viewCount')}
                id="listing-viewCount"
                name="viewCount"
                data-cy="viewCount"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.createdBy')}
                id="listing-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.createdDate')}
                id="listing-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.updatedBy')}
                id="listing-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listing.updateDate')}
                id="listing-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="listing-location"
                name="locationId"
                data-cy="location"
                label={translate('campsitesindiaApp.listing.location')}
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
                id="listing-listingType"
                name="listingTypeId"
                data-cy="listingType"
                label={translate('campsitesindiaApp.listing.listingType')}
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
                id="listing-owner"
                name="ownerId"
                data-cy="owner"
                label={translate('campsitesindiaApp.listing.owner')}
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
