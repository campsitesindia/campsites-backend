import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { getEntity, updateEntity, createEntity, reset } from './room-features.reducer';
import { IRoomFeatures } from 'app/shared/model/room-features.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomFeaturesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const rooms = useAppSelector(state => state.room.entities);
  const roomFeaturesEntity = useAppSelector(state => state.roomFeatures.entity);
  const loading = useAppSelector(state => state.roomFeatures.loading);
  const updating = useAppSelector(state => state.roomFeatures.updating);
  const updateSuccess = useAppSelector(state => state.roomFeatures.updateSuccess);

  const handleClose = () => {
    props.history.push('/room-features' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getRooms({}));
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
      ...roomFeaturesEntity,
      ...values,
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
      ? {
          createdDate: displayDefaultDateTime(),
          updatedBy: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...roomFeaturesEntity,
          createdDate: convertDateTimeFromServer(roomFeaturesEntity.createdDate),
          updatedBy: convertDateTimeFromServer(roomFeaturesEntity.updatedBy),
          updateDate: convertDateTimeFromServer(roomFeaturesEntity.updateDate),
          roomId: roomFeaturesEntity?.room?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tripperNestApp.roomFeatures.home.createOrEditLabel" data-cy="RoomFeaturesCreateUpdateHeading">
            <Translate contentKey="tripperNestApp.roomFeatures.home.createOrEditLabel">Create or edit a RoomFeatures</Translate>
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
                  id="room-features-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.title')}
                id="room-features-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.count')}
                id="room-features-count"
                name="count"
                data-cy="count"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.thumbnail')}
                id="room-features-thumbnail"
                name="thumbnail"
                data-cy="thumbnail"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.icon')}
                id="room-features-icon"
                name="icon"
                data-cy="icon"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.color')}
                id="room-features-color"
                name="color"
                data-cy="color"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.imgIcon')}
                id="room-features-imgIcon"
                name="imgIcon"
                data-cy="imgIcon"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.description')}
                id="room-features-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.parent')}
                id="room-features-parent"
                name="parent"
                data-cy="parent"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.taxonomy')}
                id="room-features-taxonomy"
                name="taxonomy"
                data-cy="taxonomy"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.createdBy')}
                id="room-features-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.createdDate')}
                id="room-features-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.updatedBy')}
                id="room-features-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.roomFeatures.updateDate')}
                id="room-features-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="room-features-room"
                name="roomId"
                data-cy="room"
                label={translate('tripperNestApp.roomFeatures.room')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/room-features" replace color="info">
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

export default RoomFeaturesUpdate;
