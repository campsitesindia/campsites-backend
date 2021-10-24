import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RoomsInBooking from './rooms-in-booking';
import RoomsInBookingDetail from './rooms-in-booking-detail';
import RoomsInBookingUpdate from './rooms-in-booking-update';
import RoomsInBookingDeleteDialog from './rooms-in-booking-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RoomsInBookingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RoomsInBookingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoomsInBookingDetail} />
      <ErrorBoundaryRoute path={match.url} component={RoomsInBooking} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RoomsInBookingDeleteDialog} />
  </>
);

export default Routes;
