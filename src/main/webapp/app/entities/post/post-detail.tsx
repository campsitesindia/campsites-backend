import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './post.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PostDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const postEntity = useAppSelector(state => state.post.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postDetailsHeading">
          <Translate contentKey="tripperNestApp.post.detail.title">Post</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{postEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="tripperNestApp.post.content">Content</Translate>
            </span>
          </dt>
          <dd>{postEntity.content}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="tripperNestApp.post.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{postEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="tripperNestApp.post.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{postEntity.createdDate ? <TextFormat value={postEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="tripperNestApp.post.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{postEntity.updatedBy ? <TextFormat value={postEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="tripperNestApp.post.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{postEntity.updateDate ? <TextFormat value={postEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="tripperNestApp.post.user">User</Translate>
          </dt>
          <dd>{postEntity.user ? postEntity.user.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/post" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostDetail;
