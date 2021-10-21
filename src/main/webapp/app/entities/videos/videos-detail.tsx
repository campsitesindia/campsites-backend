import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './videos.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VideosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const videosEntity = useAppSelector(state => state.videos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="videosDetailsHeading">
          <Translate contentKey="campsitesindiaApp.videos.detail.title">Videos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{videosEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="campsitesindiaApp.videos.name">Name</Translate>
            </span>
          </dt>
          <dd>{videosEntity.name}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="campsitesindiaApp.videos.url">Url</Translate>
            </span>
          </dt>
          <dd>{videosEntity.url}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.videos.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{videosEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.videos.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{videosEntity.createdDate ? <TextFormat value={videosEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.videos.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{videosEntity.updatedBy ? <TextFormat value={videosEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.videos.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{videosEntity.updateDate ? <TextFormat value={videosEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.videos.listing">Listing</Translate>
          </dt>
          <dd>{videosEntity.listing ? videosEntity.listing.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/videos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/videos/${videosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VideosDetail;
