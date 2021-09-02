import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ListingType from './listing-type';
import ListingTypeDetail from './listing-type-detail';
import ListingTypeUpdate from './listing-type-update';
import ListingTypeDeleteDialog from './listing-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ListingTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ListingTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ListingTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ListingType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ListingTypeDeleteDialog} />
  </>
);

export default Routes;
