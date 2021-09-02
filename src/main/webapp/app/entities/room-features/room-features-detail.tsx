import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './room-features.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomFeaturesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const roomFeaturesEntity = useAppSelector(state => state.roomFeatures.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roomFeaturesDetailsHeading">
          <Translate contentKey="tripperNestApp.roomFeatures.detail.title">RoomFeatures</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="tripperNestApp.roomFeatures.title">Title</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.title}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="tripperNestApp.roomFeatures.count">Count</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.count}</dd>
          <dt>
            <span id="thumbnail">
              <Translate contentKey="tripperNestApp.roomFeatures.thumbnail">Thumbnail</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.thumbnail}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="tripperNestApp.roomFeatures.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.icon}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="tripperNestApp.roomFeatures.color">Color</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.color}</dd>
          <dt>
            <span id="imgIcon">
              <Translate contentKey="tripperNestApp.roomFeatures.imgIcon">Img Icon</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.imgIcon}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="tripperNestApp.roomFeatures.description">Description</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.description}</dd>
          <dt>
            <span id="parent">
              <Translate contentKey="tripperNestApp.roomFeatures.parent">Parent</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.parent}</dd>
          <dt>
            <span id="taxonomy">
              <Translate contentKey="tripperNestApp.roomFeatures.taxonomy">Taxonomy</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.taxonomy}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="tripperNestApp.roomFeatures.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{roomFeaturesEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="tripperNestApp.roomFeatures.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {roomFeaturesEntity.createdDate ? (
              <TextFormat value={roomFeaturesEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="tripperNestApp.roomFeatures.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>
            {roomFeaturesEntity.updatedBy ? <TextFormat value={roomFeaturesEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="tripperNestApp.roomFeatures.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {roomFeaturesEntity.updateDate ? (
              <TextFormat value={roomFeaturesEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="tripperNestApp.roomFeatures.room">Room</Translate>
          </dt>
          <dd>{roomFeaturesEntity.room ? roomFeaturesEntity.room.roomNumber : ''}</dd>
        </dl>
        <Button tag={Link} to="/room-features" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room-features/${roomFeaturesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomFeaturesDetail;
