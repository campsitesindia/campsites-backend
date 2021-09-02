import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AuthenticatedUser from './authenticated-user';
import Post from './post';
import Images from './images';
import Comments from './comments';
import Location from './location';
import Rating from './rating';
import ListingType from './listing-type';
import Listing from './listing';
import Photos from './photos';
import Videos from './videos';
import Features from './features';
import Bookings from './bookings';
import Invoice from './invoice';
import Room from './room';
import RoomType from './room-type';
import RoomFeatures from './room-features';
import Review from './review';
import Like from './like';
import Followers from './followers';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}authenticated-user`} component={AuthenticatedUser} />
      <ErrorBoundaryRoute path={`${match.url}post`} component={Post} />
      <ErrorBoundaryRoute path={`${match.url}images`} component={Images} />
      <ErrorBoundaryRoute path={`${match.url}comments`} component={Comments} />
      <ErrorBoundaryRoute path={`${match.url}location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}rating`} component={Rating} />
      <ErrorBoundaryRoute path={`${match.url}listing-type`} component={ListingType} />
      <ErrorBoundaryRoute path={`${match.url}listing`} component={Listing} />
      <ErrorBoundaryRoute path={`${match.url}photos`} component={Photos} />
      <ErrorBoundaryRoute path={`${match.url}videos`} component={Videos} />
      <ErrorBoundaryRoute path={`${match.url}features`} component={Features} />
      <ErrorBoundaryRoute path={`${match.url}bookings`} component={Bookings} />
      <ErrorBoundaryRoute path={`${match.url}invoice`} component={Invoice} />
      <ErrorBoundaryRoute path={`${match.url}room`} component={Room} />
      <ErrorBoundaryRoute path={`${match.url}room-type`} component={RoomType} />
      <ErrorBoundaryRoute path={`${match.url}room-features`} component={RoomFeatures} />
      <ErrorBoundaryRoute path={`${match.url}review`} component={Review} />
      <ErrorBoundaryRoute path={`${match.url}like`} component={Like} />
      <ErrorBoundaryRoute path={`${match.url}followers`} component={Followers} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
