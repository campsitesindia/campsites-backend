import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './followers.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FollowersDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const followersEntity = useAppSelector(state => state.followers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="followersDetailsHeading">
          <Translate contentKey="tripperNestApp.followers.detail.title">Followers</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{followersEntity.id}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="tripperNestApp.followers.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{followersEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="tripperNestApp.followers.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {followersEntity.createdDate ? <TextFormat value={followersEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="tripperNestApp.followers.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>
            {followersEntity.updatedBy ? <TextFormat value={followersEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="tripperNestApp.followers.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {followersEntity.updateDate ? <TextFormat value={followersEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="tripperNestApp.followers.followedBy">Followed By</Translate>
          </dt>
          <dd>{followersEntity.followedBy ? followersEntity.followedBy.email : ''}</dd>
          <dt>
            <Translate contentKey="tripperNestApp.followers.user">User</Translate>
          </dt>
          <dd>{followersEntity.user ? followersEntity.user.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/followers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/followers/${followersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FollowersDetail;
