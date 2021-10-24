import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FeaturesListing from './features-listing';
import FeaturesListingDetail from './features-listing-detail';
import FeaturesListingUpdate from './features-listing-update';
import FeaturesListingDeleteDialog from './features-listing-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeaturesListingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeaturesListingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeaturesListingDetail} />
      <ErrorBoundaryRoute path={match.url} component={FeaturesListing} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FeaturesListingDeleteDialog} />
  </>
);

export default Routes;
