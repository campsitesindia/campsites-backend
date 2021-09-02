import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AuthenticatedUser from './authenticated-user';
import AuthenticatedUserDetail from './authenticated-user-detail';
import AuthenticatedUserUpdate from './authenticated-user-update';
import AuthenticatedUserDeleteDialog from './authenticated-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AuthenticatedUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AuthenticatedUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AuthenticatedUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={AuthenticatedUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AuthenticatedUserDeleteDialog} />
  </>
);

export default Routes;
