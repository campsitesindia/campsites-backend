import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import authenticatedUser from 'app/entities/authenticated-user/authenticated-user.reducer';
// prettier-ignore
import post from 'app/entities/post/post.reducer';
// prettier-ignore
import images from 'app/entities/images/images.reducer';
// prettier-ignore
import comments from 'app/entities/comments/comments.reducer';
// prettier-ignore
import location from 'app/entities/location/location.reducer';
// prettier-ignore
import rating from 'app/entities/rating/rating.reducer';
// prettier-ignore
import listingType from 'app/entities/listing-type/listing-type.reducer';
// prettier-ignore
import listing from 'app/entities/listing/listing.reducer';
// prettier-ignore
import photos from 'app/entities/photos/photos.reducer';
// prettier-ignore
import videos from 'app/entities/videos/videos.reducer';
// prettier-ignore
import features from 'app/entities/features/features.reducer';
// prettier-ignore
import bookings from 'app/entities/bookings/bookings.reducer';
// prettier-ignore
import invoice from 'app/entities/invoice/invoice.reducer';
// prettier-ignore
import room from 'app/entities/room/room.reducer';
// prettier-ignore
import roomType from 'app/entities/room-type/room-type.reducer';
// prettier-ignore
import review from 'app/entities/review/review.reducer';
// prettier-ignore
import like from 'app/entities/like/like.reducer';
// prettier-ignore
import followers from 'app/entities/followers/followers.reducer';
// prettier-ignore
import featuresListing from 'app/entities/features-listing/features-listing.reducer';
// prettier-ignore
import featuresInRoom from 'app/entities/features-in-room/features-in-room.reducer';
// prettier-ignore
import roomsForListing from 'app/entities/rooms-for-listing/rooms-for-listing.reducer';
// prettier-ignore
import roomsInBooking from 'app/entities/rooms-in-booking/rooms-in-booking.reducer';
// prettier-ignore
import album from 'app/entities/album/album.reducer';
// prettier-ignore
import tag from 'app/entities/tag/tag.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  authenticatedUser,
  post,
  images,
  comments,
  location,
  rating,
  listingType,
  listing,
  photos,
  videos,
  features,
  bookings,
  invoice,
  room,
  roomType,
  review,
  like,
  followers,
  featuresListing,
  featuresInRoom,
  roomsForListing,
  roomsInBooking,
  album,
  tag,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
