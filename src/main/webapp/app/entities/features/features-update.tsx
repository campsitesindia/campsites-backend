import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './features.reducer';
import { IFeatures } from 'app/shared/model/features.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FeaturesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const featuresEntity = useAppSelector(state => state.features.entity);
  const loading = useAppSelector(state => state.features.loading);
  const updating = useAppSelector(state => state.features.updating);
  const updateSuccess = useAppSelector(state => state.features.updateSuccess);

  const handleClose = () => {
    props.history.push('/features' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedBy = convertDateTimeToServer(values.updatedBy);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...featuresEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdDate: displayDefaultDateTime(),
          updatedBy: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...featuresEntity,
          createdDate: convertDateTimeFromServer(featuresEntity.createdDate),
          updatedBy: convertDateTimeFromServer(featuresEntity.updatedBy),
          updateDate: convertDateTimeFromServer(featuresEntity.updateDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.features.home.createOrEditLabel" data-cy="FeaturesCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.features.home.createOrEditLabel">Create or edit a Features</Translate>
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
                  id="features-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('campsitesindiaApp.features.title')}
                id="features-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.count')}
                id="features-count"
                name="count"
                data-cy="count"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.thumbnail')}
                id="features-thumbnail"
                name="thumbnail"
                data-cy="thumbnail"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.icon')}
                id="features-icon"
                name="icon"
                data-cy="icon"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.color')}
                id="features-color"
                name="color"
                data-cy="color"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.imgIcon')}
                id="features-imgIcon"
                name="imgIcon"
                data-cy="imgIcon"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.description')}
                id="features-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.parent')}
                id="features-parent"
                name="parent"
                data-cy="parent"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.taxonomy')}
                id="features-taxonomy"
                name="taxonomy"
                data-cy="taxonomy"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.createdBy')}
                id="features-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.createdDate')}
                id="features-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.updatedBy')}
                id="features-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.features.updateDate')}
                id="features-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/features" replace color="info">
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

export default FeaturesUpdate;
