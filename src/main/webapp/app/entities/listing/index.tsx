import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Listing from './listing';
import ListingDetail from './listing-detail';
import ListingUpdate from './listing-update';
import ListingDeleteDialog from './listing-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ListingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ListingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ListingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Listing} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ListingDeleteDialog} />
  </>
);

export default Routes;
