import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RoomFeatures from './room-features';
import RoomFeaturesDetail from './room-features-detail';
import RoomFeaturesUpdate from './room-features-update';
import RoomFeaturesDeleteDialog from './room-features-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RoomFeaturesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RoomFeaturesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoomFeaturesDetail} />
      <ErrorBoundaryRoute path={match.url} component={RoomFeatures} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RoomFeaturesDeleteDialog} />
  </>
);

export default Routes;
