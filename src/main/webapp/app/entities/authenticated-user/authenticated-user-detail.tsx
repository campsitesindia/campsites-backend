import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './authenticated-user.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AuthenticatedUserDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const authenticatedUserEntity = useAppSelector(state => state.authenticatedUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="authenticatedUserDetailsHeading">
          <Translate contentKey="campsitesindiaApp.authenticatedUser.detail.title">AuthenticatedUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{authenticatedUserEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="campsitesindiaApp.authenticatedUser.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{authenticatedUserEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="campsitesindiaApp.authenticatedUser.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{authenticatedUserEntity.lastName}</dd>
          <dt>
            <span id="provider">
              <Translate contentKey="campsitesindiaApp.authenticatedUser.provider">Provider</Translate>
            </span>
          </dt>
          <dd>{authenticatedUserEntity.provider}</dd>
          <dt>
            <span id="authTimestamp">
              <Translate contentKey="campsitesindiaApp.authenticatedUser.authTimestamp">Auth Timestamp</Translate>
            </span>
          </dt>
          <dd>
            {authenticatedUserEntity.authTimestamp ? (
              <TextFormat value={authenticatedUserEntity.authTimestamp} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.authenticatedUser.user">User</Translate>
          </dt>
          <dd>{authenticatedUserEntity.user ? authenticatedUserEntity.user.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/authenticated-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/authenticated-user/${authenticatedUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AuthenticatedUserDetail;
