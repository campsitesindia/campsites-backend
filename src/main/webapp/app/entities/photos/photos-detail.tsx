import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './photos.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PhotosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const photosEntity = useAppSelector(state => state.photos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="photosDetailsHeading">
          <Translate contentKey="tripperNestApp.photos.detail.title">Photos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{photosEntity.id}</dd>
          <dt>
            <span id="alt">
              <Translate contentKey="tripperNestApp.photos.alt">Alt</Translate>
            </span>
          </dt>
          <dd>{photosEntity.alt}</dd>
          <dt>
            <span id="caption">
              <Translate contentKey="tripperNestApp.photos.caption">Caption</Translate>
            </span>
          </dt>
          <dd>{photosEntity.caption}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="tripperNestApp.photos.description">Description</Translate>
            </span>
          </dt>
          <dd>{photosEntity.description}</dd>
          <dt>
            <span id="href">
              <Translate contentKey="tripperNestApp.photos.href">Href</Translate>
            </span>
          </dt>
          <dd>{photosEntity.href}</dd>
          <dt>
            <span id="src">
              <Translate contentKey="tripperNestApp.photos.src">Src</Translate>
            </span>
          </dt>
          <dd>{photosEntity.src}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="tripperNestApp.photos.title">Title</Translate>
            </span>
          </dt>
          <dd>{photosEntity.title}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="tripperNestApp.photos.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{photosEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="tripperNestApp.photos.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{photosEntity.createdDate ? <TextFormat value={photosEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="tripperNestApp.photos.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{photosEntity.updatedBy ? <TextFormat value={photosEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="tripperNestApp.photos.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{photosEntity.updateDate ? <TextFormat value={photosEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="tripperNestApp.photos.listing">Listing</Translate>
          </dt>
          <dd>{photosEntity.listing ? photosEntity.listing.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/photos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/photos/${photosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PhotosDetail;
