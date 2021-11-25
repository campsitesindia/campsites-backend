import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/authenticated-user">
      <Translate contentKey="global.menu.entities.authenticatedUser" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/post">
      <Translate contentKey="global.menu.entities.post" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/images">
      <Translate contentKey="global.menu.entities.images" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/comments">
      <Translate contentKey="global.menu.entities.comments" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/location">
      <Translate contentKey="global.menu.entities.location" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/rating">
      <Translate contentKey="global.menu.entities.rating" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/listing-type">
      <Translate contentKey="global.menu.entities.listingType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/listing">
      <Translate contentKey="global.menu.entities.listing" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/photos">
      <Translate contentKey="global.menu.entities.photos" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/videos">
      <Translate contentKey="global.menu.entities.videos" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/features">
      <Translate contentKey="global.menu.entities.features" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bookings">
      <Translate contentKey="global.menu.entities.bookings" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/invoice">
      <Translate contentKey="global.menu.entities.invoice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/room">
      <Translate contentKey="global.menu.entities.room" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/room-type">
      <Translate contentKey="global.menu.entities.roomType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/review">
      <Translate contentKey="global.menu.entities.review" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/like">
      <Translate contentKey="global.menu.entities.like" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/followers">
      <Translate contentKey="global.menu.entities.followers" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/features-listing">
      <Translate contentKey="global.menu.entities.featuresListing" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/features-in-room">
      <Translate contentKey="global.menu.entities.featuresInRoom" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/rooms-for-listing">
      <Translate contentKey="global.menu.entities.roomsForListing" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/rooms-in-booking">
      <Translate contentKey="global.menu.entities.roomsInBooking" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/album">
      <Translate contentKey="global.menu.entities.album" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tag">
      <Translate contentKey="global.menu.entities.tag" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
