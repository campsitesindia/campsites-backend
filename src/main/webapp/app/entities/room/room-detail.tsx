import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './room.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const roomEntity = useAppSelector(state => state.room.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roomDetailsHeading">
          <Translate contentKey="campsitesindiaApp.room.detail.title">Room</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{roomEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="campsitesindiaApp.room.name">Name</Translate>
            </span>
          </dt>
          <dd>{roomEntity.name}</dd>
          <dt>
            <span id="roomNumber">
              <Translate contentKey="campsitesindiaApp.room.roomNumber">Room Number</Translate>
            </span>
          </dt>
          <dd>{roomEntity.roomNumber}</dd>
          <dt>
            <span id="isSmoking">
              <Translate contentKey="campsitesindiaApp.room.isSmoking">Is Smoking</Translate>
            </span>
          </dt>
          <dd>{roomEntity.isSmoking}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="campsitesindiaApp.room.status">Status</Translate>
            </span>
          </dt>
          <dd>{roomEntity.status}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.room.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{roomEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.room.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{roomEntity.createdDate ? <TextFormat value={roomEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.room.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{roomEntity.updatedBy ? <TextFormat value={roomEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.room.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{roomEntity.updateDate ? <TextFormat value={roomEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.room.roomType">Room Type</Translate>
          </dt>
          <dd>{roomEntity.roomType ? roomEntity.roomType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/room" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room/${roomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomDetail;
