import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './features-in-room.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FeaturesInRoomDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const featuresInRoomEntity = useAppSelector(state => state.featuresInRoom.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="featuresInRoomDetailsHeading">
          <Translate contentKey="campsitesindiaApp.featuresInRoom.detail.title">FeaturesInRoom</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{featuresInRoomEntity.id}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.featuresInRoom.room">Room</Translate>
          </dt>
          <dd>{featuresInRoomEntity.room ? featuresInRoomEntity.room.roomNumber : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.featuresInRoom.feature">Feature</Translate>
          </dt>
          <dd>{featuresInRoomEntity.feature ? featuresInRoomEntity.feature.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/features-in-room" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/features-in-room/${featuresInRoomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeaturesInRoomDetail;
