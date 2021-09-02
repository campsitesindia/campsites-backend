import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Followers from './followers';
import FollowersDetail from './followers-detail';
import FollowersUpdate from './followers-update';
import FollowersDeleteDialog from './followers-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FollowersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FollowersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FollowersDetail} />
      <ErrorBoundaryRoute path={match.url} component={Followers} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FollowersDeleteDialog} />
  </>
);

export default Routes;
