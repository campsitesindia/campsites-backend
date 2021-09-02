import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RoomType from './room-type';
import RoomTypeDetail from './room-type-detail';
import RoomTypeUpdate from './room-type-update';
import RoomTypeDeleteDialog from './room-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RoomTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RoomTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoomTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={RoomType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RoomTypeDeleteDialog} />
  </>
);

export default Routes;
