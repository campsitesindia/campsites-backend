import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FeaturesInRoom from './features-in-room';
import FeaturesInRoomDetail from './features-in-room-detail';
import FeaturesInRoomUpdate from './features-in-room-update';
import FeaturesInRoomDeleteDialog from './features-in-room-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeaturesInRoomUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeaturesInRoomUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeaturesInRoomDetail} />
      <ErrorBoundaryRoute path={match.url} component={FeaturesInRoom} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FeaturesInRoomDeleteDialog} />
  </>
);

export default Routes;
