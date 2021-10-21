import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './comments.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommentsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const commentsEntity = useAppSelector(state => state.comments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commentsDetailsHeading">
          <Translate contentKey="campsitesindiaApp.comments.detail.title">Comments</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{commentsEntity.id}</dd>
          <dt>
            <span id="commentText">
              <Translate contentKey="campsitesindiaApp.comments.commentText">Comment Text</Translate>
            </span>
          </dt>
          <dd>{commentsEntity.commentText}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.comments.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{commentsEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.comments.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {commentsEntity.createdDate ? <TextFormat value={commentsEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.comments.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{commentsEntity.updatedBy ? <TextFormat value={commentsEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.comments.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {commentsEntity.updateDate ? <TextFormat value={commentsEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.comments.post">Post</Translate>
          </dt>
          <dd>{commentsEntity.post ? commentsEntity.post.id : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.comments.user">User</Translate>
          </dt>
          <dd>{commentsEntity.user ? commentsEntity.user.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/comments" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comments/${commentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommentsDetail;
