import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Features from './features';
import FeaturesDetail from './features-detail';
import FeaturesUpdate from './features-update';
import FeaturesDeleteDialog from './features-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeaturesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeaturesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeaturesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Features} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FeaturesDeleteDialog} />
  </>
);

export default Routes;
