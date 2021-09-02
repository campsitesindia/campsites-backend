import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './location.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LocationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const locationEntity = useAppSelector(state => state.location.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDetailsHeading">
          <Translate contentKey="tripperNestApp.location.detail.title">Location</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{locationEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="tripperNestApp.location.title">Title</Translate>
            </span>
          </dt>
          <dd>{locationEntity.title}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="tripperNestApp.location.count">Count</Translate>
            </span>
          </dt>
          <dd>{locationEntity.count}</dd>
          <dt>
            <span id="thumbnail">
              <Translate contentKey="tripperNestApp.location.thumbnail">Thumbnail</Translate>
            </span>
          </dt>
          <dd>{locationEntity.thumbnail}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="tripperNestApp.location.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{locationEntity.icon}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="tripperNestApp.location.color">Color</Translate>
            </span>
          </dt>
          <dd>{locationEntity.color}</dd>
          <dt>
            <span id="imgIcon">
              <Translate contentKey="tripperNestApp.location.imgIcon">Img Icon</Translate>
            </span>
          </dt>
          <dd>{locationEntity.imgIcon}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="tripperNestApp.location.description">Description</Translate>
            </span>
          </dt>
          <dd>{locationEntity.description}</dd>
          <dt>
            <span id="parent">
              <Translate contentKey="tripperNestApp.location.parent">Parent</Translate>
            </span>
          </dt>
          <dd>{locationEntity.parent}</dd>
          <dt>
            <span id="taxonomy">
              <Translate contentKey="tripperNestApp.location.taxonomy">Taxonomy</Translate>
            </span>
          </dt>
          <dd>{locationEntity.taxonomy}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="tripperNestApp.location.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{locationEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="tripperNestApp.location.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {locationEntity.createdDate ? <TextFormat value={locationEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="tripperNestApp.location.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{locationEntity.updatedBy ? <TextFormat value={locationEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="tripperNestApp.location.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {locationEntity.updateDate ? <TextFormat value={locationEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="tripperNestApp.location.parentLocation">Parent Location</Translate>
          </dt>
          <dd>{locationEntity.parentLocation ? locationEntity.parentLocation.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationDetail;
