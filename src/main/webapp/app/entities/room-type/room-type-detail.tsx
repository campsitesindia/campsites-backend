import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './room-type.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const roomTypeEntity = useAppSelector(state => state.roomType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roomTypeDetailsHeading">
          <Translate contentKey="campsitesindiaApp.roomType.detail.title">RoomType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="campsitesindiaApp.roomType.type">Type</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.type}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="campsitesindiaApp.roomType.description">Description</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.description}</dd>
          <dt>
            <span id="maxCapacity">
              <Translate contentKey="campsitesindiaApp.roomType.maxCapacity">Max Capacity</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.maxCapacity}</dd>
          <dt>
            <span id="numberOfBeds">
              <Translate contentKey="campsitesindiaApp.roomType.numberOfBeds">Number Of Beds</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.numberOfBeds}</dd>
          <dt>
            <span id="numberOfBathrooms">
              <Translate contentKey="campsitesindiaApp.roomType.numberOfBathrooms">Number Of Bathrooms</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.numberOfBathrooms}</dd>
          <dt>
            <span id="roomRatePerNight">
              <Translate contentKey="campsitesindiaApp.roomType.roomRatePerNight">Room Rate Per Night</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.roomRatePerNight}</dd>
          <dt>
            <span id="roomRateChildPerNight">
              <Translate contentKey="campsitesindiaApp.roomType.roomRateChildPerNight">Room Rate Child Per Night</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.roomRateChildPerNight}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.roomType.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.roomType.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {roomTypeEntity.createdDate ? <TextFormat value={roomTypeEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.roomType.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{roomTypeEntity.updatedBy ? <TextFormat value={roomTypeEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.roomType.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {roomTypeEntity.updateDate ? <TextFormat value={roomTypeEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/room-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room-type/${roomTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomTypeDetail;
