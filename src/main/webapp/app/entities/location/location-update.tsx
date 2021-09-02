import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { getEntity, updateEntity, createEntity, reset } from './location.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LocationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const locations = useAppSelector(state => state.location.entities);
  const locationEntity = useAppSelector(state => state.location.entity);
  const loading = useAppSelector(state => state.location.loading);
  const updating = useAppSelector(state => state.location.updating);
  const updateSuccess = useAppSelector(state => state.location.updateSuccess);

  const handleClose = () => {
    props.history.push('/location' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getLocations({}));
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
      ...locationEntity,
      ...values,
      parentLocation: locations.find(it => it.id.toString() === values.parentLocationId.toString()),
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
          ...locationEntity,
          createdDate: convertDateTimeFromServer(locationEntity.createdDate),
          updatedBy: convertDateTimeFromServer(locationEntity.updatedBy),
          updateDate: convertDateTimeFromServer(locationEntity.updateDate),
          parentLocationId: locationEntity?.parentLocation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tripperNestApp.location.home.createOrEditLabel" data-cy="LocationCreateUpdateHeading">
            <Translate contentKey="tripperNestApp.location.home.createOrEditLabel">Create or edit a Location</Translate>
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
                  id="location-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('tripperNestApp.location.title')}
                id="location-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.count')}
                id="location-count"
                name="count"
                data-cy="count"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.thumbnail')}
                id="location-thumbnail"
                name="thumbnail"
                data-cy="thumbnail"
                type="text"
              />
              <ValidatedField label={translate('tripperNestApp.location.icon')} id="location-icon" name="icon" data-cy="icon" type="text" />
              <ValidatedField
                label={translate('tripperNestApp.location.color')}
                id="location-color"
                name="color"
                data-cy="color"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.imgIcon')}
                id="location-imgIcon"
                name="imgIcon"
                data-cy="imgIcon"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.description')}
                id="location-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.parent')}
                id="location-parent"
                name="parent"
                data-cy="parent"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.taxonomy')}
                id="location-taxonomy"
                name="taxonomy"
                data-cy="taxonomy"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.createdBy')}
                id="location-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.createdDate')}
                id="location-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.updatedBy')}
                id="location-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.location.updateDate')}
                id="location-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="location-parentLocation"
                name="parentLocationId"
                data-cy="parentLocation"
                label={translate('tripperNestApp.location.parentLocation')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/location" replace color="info">
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

export default LocationUpdate;
