import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bookings from './bookings';
import BookingsDetail from './bookings-detail';
import BookingsUpdate from './bookings-update';
import BookingsDeleteDialog from './bookings-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BookingsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BookingsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BookingsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bookings} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BookingsDeleteDialog} />
  </>
);

export default Routes;
