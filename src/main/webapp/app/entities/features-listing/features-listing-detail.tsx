import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './features-listing.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FeaturesListingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const featuresListingEntity = useAppSelector(state => state.featuresListing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="featuresListingDetailsHeading">
          <Translate contentKey="campsitesindiaApp.featuresListing.detail.title">FeaturesListing</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{featuresListingEntity.id}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.featuresListing.listing">Listing</Translate>
          </dt>
          <dd>{featuresListingEntity.listing ? featuresListingEntity.listing.title : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.featuresListing.feature">Feature</Translate>
          </dt>
          <dd>{featuresListingEntity.feature ? featuresListingEntity.feature.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/features-listing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/features-listing/${featuresListingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeaturesListingDetail;
