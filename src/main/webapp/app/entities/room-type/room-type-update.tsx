import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './room-type.reducer';
import { IRoomType } from 'app/shared/model/room-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomTypeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const roomTypeEntity = useAppSelector(state => state.roomType.entity);
  const loading = useAppSelector(state => state.roomType.loading);
  const updating = useAppSelector(state => state.roomType.updating);
  const updateSuccess = useAppSelector(state => state.roomType.updateSuccess);

  const handleClose = () => {
    props.history.push('/room-type' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
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
      ...roomTypeEntity,
      ...values,
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
          ...roomTypeEntity,
          createdDate: convertDateTimeFromServer(roomTypeEntity.createdDate),
          updatedBy: convertDateTimeFromServer(roomTypeEntity.updatedBy),
          updateDate: convertDateTimeFromServer(roomTypeEntity.updateDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.roomType.home.createOrEditLabel" data-cy="RoomTypeCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.roomType.home.createOrEditLabel">Create or edit a RoomType</Translate>
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
                  id="room-type-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.type')}
                id="room-type-type"
                name="type"
                data-cy="type"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.description')}
                id="room-type-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.maxCapacity')}
                id="room-type-maxCapacity"
                name="maxCapacity"
                data-cy="maxCapacity"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.numberOfBeds')}
                id="room-type-numberOfBeds"
                name="numberOfBeds"
                data-cy="numberOfBeds"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.numberOfBathrooms')}
                id="room-type-numberOfBathrooms"
                name="numberOfBathrooms"
                data-cy="numberOfBathrooms"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.roomRatePerNight')}
                id="room-type-roomRatePerNight"
                name="roomRatePerNight"
                data-cy="roomRatePerNight"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.roomRateChildPerNight')}
                id="room-type-roomRateChildPerNight"
                name="roomRateChildPerNight"
                data-cy="roomRateChildPerNight"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.createdBy')}
                id="room-type-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.createdDate')}
                id="room-type-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.updatedBy')}
                id="room-type-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.roomType.updateDate')}
                id="room-type-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/room-type" replace color="info">
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

export default RoomTypeUpdate;
