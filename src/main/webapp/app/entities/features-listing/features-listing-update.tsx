import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IListing } from 'app/shared/model/listing.model';
import { getEntities as getListings } from 'app/entities/listing/listing.reducer';
import { IFeatures } from 'app/shared/model/features.model';
import { getEntities as getFeatures } from 'app/entities/features/features.reducer';
import { getEntity, updateEntity, createEntity, reset } from './features-listing.reducer';
import { IFeaturesListing } from 'app/shared/model/features-listing.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FeaturesListingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const listings = useAppSelector(state => state.listing.entities);
  const features = useAppSelector(state => state.features.entities);
  const featuresListingEntity = useAppSelector(state => state.featuresListing.entity);
  const loading = useAppSelector(state => state.featuresListing.loading);
  const updating = useAppSelector(state => state.featuresListing.updating);
  const updateSuccess = useAppSelector(state => state.featuresListing.updateSuccess);

  const handleClose = () => {
    props.history.push('/features-listing' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getListings({}));
    dispatch(getFeatures({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...featuresListingEntity,
      ...values,
      listing: listings.find(it => it.id.toString() === values.listingId.toString()),
      feature: features.find(it => it.id.toString() === values.featureId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...featuresListingEntity,
          listingId: featuresListingEntity?.listing?.id,
          featureId: featuresListingEntity?.feature?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.featuresListing.home.createOrEditLabel" data-cy="FeaturesListingCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.featuresListing.home.createOrEditLabel">Create or edit a FeaturesListing</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="features-listing-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="features-listing-listing"
                name="listingId"
                data-cy="listing"
                label={translate('campsitesindiaApp.featuresListing.listing')}
                type="select"
              >
                <option value="" key="0" />
                {listings
                  ? listings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="features-listing-feature"
                name="featureId"
                data-cy="feature"
                label={translate('campsitesindiaApp.featuresListing.feature')}
                type="select"
              >
                <option value="" key="0" />
                {features
                  ? features.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/features-listing" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FeaturesListingUpdate;
