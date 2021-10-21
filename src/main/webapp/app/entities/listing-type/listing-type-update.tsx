import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities as getListingTypes } from 'app/entities/listing-type/listing-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './listing-type.reducer';
import { IListingType } from 'app/shared/model/listing-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListingTypeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const listingTypes = useAppSelector(state => state.listingType.entities);
  const listingTypeEntity = useAppSelector(state => state.listingType.entity);
  const loading = useAppSelector(state => state.listingType.loading);
  const updating = useAppSelector(state => state.listingType.updating);
  const updateSuccess = useAppSelector(state => state.listingType.updateSuccess);

  const handleClose = () => {
    props.history.push('/listing-type' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getListingTypes({}));
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
      ...listingTypeEntity,
      ...values,
      parent: listingTypes.find(it => it.id.toString() === values.parentId.toString()),
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
          ...listingTypeEntity,
          createdDate: convertDateTimeFromServer(listingTypeEntity.createdDate),
          updatedBy: convertDateTimeFromServer(listingTypeEntity.updatedBy),
          updateDate: convertDateTimeFromServer(listingTypeEntity.updateDate),
          parentId: listingTypeEntity?.parent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.listingType.home.createOrEditLabel" data-cy="ListingTypeCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.listingType.home.createOrEditLabel">Create or edit a ListingType</Translate>
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
                  id="listing-type-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.title')}
                id="listing-type-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.count')}
                id="listing-type-count"
                name="count"
                data-cy="count"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.thumbnail')}
                id="listing-type-thumbnail"
                name="thumbnail"
                data-cy="thumbnail"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.icon')}
                id="listing-type-icon"
                name="icon"
                data-cy="icon"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.color')}
                id="listing-type-color"
                name="color"
                data-cy="color"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.imgIcon')}
                id="listing-type-imgIcon"
                name="imgIcon"
                data-cy="imgIcon"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.description')}
                id="listing-type-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.taxonomy')}
                id="listing-type-taxonomy"
                name="taxonomy"
                data-cy="taxonomy"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.createdBy')}
                id="listing-type-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.createdDate')}
                id="listing-type-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.updatedBy')}
                id="listing-type-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.listingType.updateDate')}
                id="listing-type-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="listing-type-parent"
                name="parentId"
                data-cy="parent"
                label={translate('campsitesindiaApp.listingType.parent')}
                type="select"
              >
                <option value="" key="0" />
                {listingTypes
                  ? listingTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/listing-type" replace color="info">
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

export default ListingTypeUpdate;
