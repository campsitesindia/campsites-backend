import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './images.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ImagesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const imagesEntity = useAppSelector(state => state.images.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="imagesDetailsHeading">
          <Translate contentKey="tripperNestApp.images.detail.title">Images</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.id}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="tripperNestApp.images.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.imageUrl}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="tripperNestApp.images.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="tripperNestApp.images.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.createdDate ? <TextFormat value={imagesEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="tripperNestApp.images.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.updatedBy ? <TextFormat value={imagesEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="tripperNestApp.images.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.updateDate ? <TextFormat value={imagesEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="tripperNestApp.images.post">Post</Translate>
          </dt>
          <dd>{imagesEntity.post ? imagesEntity.post.id : ''}</dd>
          <dt>
            <Translate contentKey="tripperNestApp.images.user">User</Translate>
          </dt>
          <dd>{imagesEntity.user ? imagesEntity.user.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/images" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/images/${imagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImagesDetail;
