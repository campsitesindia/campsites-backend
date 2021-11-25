import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './authenticated-user.reducer';
import { IAuthenticatedUser } from 'app/shared/model/authenticated-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AuthenticatedUserUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const authenticatedUserEntity = useAppSelector(state => state.authenticatedUser.entity);
  const loading = useAppSelector(state => state.authenticatedUser.loading);
  const updating = useAppSelector(state => state.authenticatedUser.updating);
  const updateSuccess = useAppSelector(state => state.authenticatedUser.updateSuccess);

  const handleClose = () => {
    props.history.push('/authenticated-user' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.authTimestamp = convertDateTimeToServer(values.authTimestamp);

    const entity = {
      ...authenticatedUserEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.userId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          authTimestamp: displayDefaultDateTime(),
        }
      : {
          ...authenticatedUserEntity,
          provider: 'LOCAL',
          authTimestamp: convertDateTimeFromServer(authenticatedUserEntity.authTimestamp),
          userId: authenticatedUserEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.authenticatedUser.home.createOrEditLabel" data-cy="AuthenticatedUserCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.authenticatedUser.home.createOrEditLabel">
              Create or edit a AuthenticatedUser
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="authenticated-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('campsitesindiaApp.authenticatedUser.firstName')}
                id="authenticated-user-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.authenticatedUser.lastName')}
                id="authenticated-user-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.authenticatedUser.provider')}
                id="authenticated-user-provider"
                name="provider"
                data-cy="provider"
                type="select"
              >
                <option value="LOCAL">{translate('campsitesindiaApp.AuthProvider.LOCAL')}</option>
                <option value="FAEBOOK">{translate('campsitesindiaApp.AuthProvider.FAEBOOK')}</option>
                <option value="GOOGLE">{translate('campsitesindiaApp.AuthProvider.GOOGLE')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('campsitesindiaApp.authenticatedUser.authTimestamp')}
                id="authenticated-user-authTimestamp"
                name="authTimestamp"
                data-cy="authTimestamp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="authenticated-user-user"
                name="userId"
                data-cy="user"
                label={translate('campsitesindiaApp.authenticatedUser.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.email}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/authenticated-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AuthenticatedUserUpdate;
