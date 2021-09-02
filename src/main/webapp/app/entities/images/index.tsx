import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Images from './images';
import ImagesDetail from './images-detail';
import ImagesUpdate from './images-update';
import ImagesDeleteDialog from './images-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ImagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ImagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ImagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Images} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ImagesDeleteDialog} />
  </>
);

export default Routes;
