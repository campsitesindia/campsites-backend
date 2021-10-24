import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RoomsForListing from './rooms-for-listing';
import RoomsForListingDetail from './rooms-for-listing-detail';
import RoomsForListingUpdate from './rooms-for-listing-update';
import RoomsForListingDeleteDialog from './rooms-for-listing-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RoomsForListingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RoomsForListingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoomsForListingDetail} />
      <ErrorBoundaryRoute path={match.url} component={RoomsForListing} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RoomsForListingDeleteDialog} />
  </>
);

export default Routes;
