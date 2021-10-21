import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './like.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LikeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const likeEntity = useAppSelector(state => state.like.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="likeDetailsHeading">
          <Translate contentKey="campsitesindiaApp.like.detail.title">Like</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{likeEntity.id}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.like.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{likeEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.like.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{likeEntity.createdDate ? <TextFormat value={likeEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.like.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{likeEntity.updatedBy ? <TextFormat value={likeEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.like.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{likeEntity.updateDate ? <TextFormat value={likeEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.like.post">Post</Translate>
          </dt>
          <dd>{likeEntity.post ? likeEntity.post.id : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.like.user">User</Translate>
          </dt>
          <dd>{likeEntity.user ? likeEntity.user.firstName : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.like.images">Images</Translate>
          </dt>
          <dd>{likeEntity.images ? likeEntity.images.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/like" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/like/${likeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LikeDetail;
