import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { IFeatures } from 'app/shared/model/features.model';
import { getEntities as getFeatures } from 'app/entities/features/features.reducer';
import { getEntity, updateEntity, createEntity, reset } from './features-in-room.reducer';
import { IFeaturesInRoom } from 'app/shared/model/features-in-room.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FeaturesInRoomUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const rooms = useAppSelector(state => state.room.entities);
  const features = useAppSelector(state => state.features.entities);
  const featuresInRoomEntity = useAppSelector(state => state.featuresInRoom.entity);
  const loading = useAppSelector(state => state.featuresInRoom.loading);
  const updating = useAppSelector(state => state.featuresInRoom.updating);
  const updateSuccess = useAppSelector(state => state.featuresInRoom.updateSuccess);

  const handleClose = () => {
    props.history.push('/features-in-room' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getRooms({}));
    dispatch(getFeatures({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...featuresInRoomEntity,
      ...values,
      room: rooms.find(it => it.id.toString() === values.roomId.toString()),
      feature: features.find(it => it.id.toString() === values.featureId.toString()),
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
          ...featuresInRoomEntity,
          roomId: featuresInRoomEntity?.room?.id,
          featureId: featuresInRoomEntity?.feature?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.featuresInRoom.home.createOrEditLabel" data-cy="FeaturesInRoomCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.featuresInRoom.home.createOrEditLabel">Create or edit a FeaturesInRoom</Translate>
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
                  id="features-in-room-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="features-in-room-room"
                name="roomId"
                data-cy="room"
                label={translate('campsitesindiaApp.featuresInRoom.room')}
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
                id="features-in-room-feature"
                name="featureId"
                data-cy="feature"
                label={translate('campsitesindiaApp.featuresInRoom.feature')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/features-in-room" replace color="info">
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

export default FeaturesInRoomUpdate;
