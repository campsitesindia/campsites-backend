import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IListing } from 'app/shared/model/listing.model';
import { getEntities as getListings } from 'app/entities/listing/listing.reducer';
import { getEntity, updateEntity, createEntity, reset } from './photos.reducer';
import { IPhotos } from 'app/shared/model/photos.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PhotosUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const listings = useAppSelector(state => state.listing.entities);
  const photosEntity = useAppSelector(state => state.photos.entity);
  const loading = useAppSelector(state => state.photos.loading);
  const updating = useAppSelector(state => state.photos.updating);
  const updateSuccess = useAppSelector(state => state.photos.updateSuccess);

  const handleClose = () => {
    props.history.push('/photos' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getListings({}));
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
      ...photosEntity,
      ...values,
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
          createdDate: displayDefaultDateTime(),
          updatedBy: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...photosEntity,
          createdDate: convertDateTimeFromServer(photosEntity.createdDate),
          updatedBy: convertDateTimeFromServer(photosEntity.updatedBy),
          updateDate: convertDateTimeFromServer(photosEntity.updateDate),
          listingId: photosEntity?.listing?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tripperNestApp.photos.home.createOrEditLabel" data-cy="PhotosCreateUpdateHeading">
            <Translate contentKey="tripperNestApp.photos.home.createOrEditLabel">Create or edit a Photos</Translate>
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
                  id="photos-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('tripperNestApp.photos.alt')} id="photos-alt" name="alt" data-cy="alt" type="text" />
              <ValidatedField
                label={translate('tripperNestApp.photos.caption')}
                id="photos-caption"
                name="caption"
                data-cy="caption"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.photos.description')}
                id="photos-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('tripperNestApp.photos.href')} id="photos-href" name="href" data-cy="href" type="text" />
              <ValidatedField label={translate('tripperNestApp.photos.src')} id="photos-src" name="src" data-cy="src" type="text" />
              <ValidatedField label={translate('tripperNestApp.photos.title')} id="photos-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('tripperNestApp.photos.createdBy')}
                id="photos-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.photos.createdDate')}
                id="photos-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.photos.updatedBy')}
                id="photos-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.photos.updateDate')}
                id="photos-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="photos-listing"
                name="listingId"
                data-cy="listing"
                label={translate('tripperNestApp.photos.listing')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/photos" replace color="info">
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

export default PhotosUpdate;
