import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './listing.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const listingEntity = useAppSelector(state => state.listing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="listingDetailsHeading">
          <Translate contentKey="campsitesindiaApp.listing.detail.title">Listing</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{listingEntity.id}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="campsitesindiaApp.listing.address">Address</Translate>
            </span>
          </dt>
          <dd>{listingEntity.address}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="campsitesindiaApp.listing.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{listingEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="campsitesindiaApp.listing.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{listingEntity.longitude}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="campsitesindiaApp.listing.url">Url</Translate>
            </span>
          </dt>
          <dd>{listingEntity.url}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="campsitesindiaApp.listing.title">Title</Translate>
            </span>
          </dt>
          <dd>{listingEntity.title}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="campsitesindiaApp.listing.content">Content</Translate>
            </span>
          </dt>
          <dd>{listingEntity.content}</dd>
          <dt>
            <span id="thumbnail">
              <Translate contentKey="campsitesindiaApp.listing.thumbnail">Thumbnail</Translate>
            </span>
          </dt>
          <dd>{listingEntity.thumbnail}</dd>
          <dt>
            <span id="isFeatured">
              <Translate contentKey="campsitesindiaApp.listing.isFeatured">Is Featured</Translate>
            </span>
          </dt>
          <dd>{listingEntity.isFeatured ? 'true' : 'false'}</dd>
          <dt>
            <span id="pricePerPerson">
              <Translate contentKey="campsitesindiaApp.listing.pricePerPerson">Price Per Person</Translate>
            </span>
          </dt>
          <dd>{listingEntity.pricePerPerson}</dd>
          <dt>
            <span id="pricePerChild">
              <Translate contentKey="campsitesindiaApp.listing.pricePerChild">Price Per Child</Translate>
            </span>
          </dt>
          <dd>{listingEntity.pricePerChild}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="campsitesindiaApp.listing.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{listingEntity.discount}</dd>
          <dt>
            <span id="isPublished">
              <Translate contentKey="campsitesindiaApp.listing.isPublished">Is Published</Translate>
            </span>
          </dt>
          <dd>{listingEntity.isPublished ? 'true' : 'false'}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="campsitesindiaApp.listing.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{listingEntity.phone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="campsitesindiaApp.listing.email">Email</Translate>
            </span>
          </dt>
          <dd>{listingEntity.email}</dd>
          <dt>
            <span id="website">
              <Translate contentKey="campsitesindiaApp.listing.website">Website</Translate>
            </span>
          </dt>
          <dd>{listingEntity.website}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="campsitesindiaApp.listing.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{listingEntity.comment}</dd>
          <dt>
            <span id="disableBooking">
              <Translate contentKey="campsitesindiaApp.listing.disableBooking">Disable Booking</Translate>
            </span>
          </dt>
          <dd>{listingEntity.disableBooking ? 'true' : 'false'}</dd>
          <dt>
            <span id="viewCount">
              <Translate contentKey="campsitesindiaApp.listing.viewCount">View Count</Translate>
            </span>
          </dt>
          <dd>{listingEntity.viewCount}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.listing.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{listingEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.listing.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {listingEntity.createdDate ? <TextFormat value={listingEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.listing.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{listingEntity.updatedBy ? <TextFormat value={listingEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.listing.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{listingEntity.updateDate ? <TextFormat value={listingEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.listing.location">Location</Translate>
          </dt>
          <dd>{listingEntity.location ? listingEntity.location.title : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.listing.listingType">Listing Type</Translate>
          </dt>
          <dd>{listingEntity.listingType ? listingEntity.listingType.title : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.listing.owner">Owner</Translate>
          </dt>
          <dd>{listingEntity.owner ? listingEntity.owner.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/listing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/listing/${listingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ListingDetail;
