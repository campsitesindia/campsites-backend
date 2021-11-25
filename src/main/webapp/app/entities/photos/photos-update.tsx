import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAlbum } from 'app/shared/model/album.model';
import { getEntities as getAlbums } from 'app/entities/album/album.reducer';
import { IListing } from 'app/shared/model/listing.model';
import { getEntities as getListings } from 'app/entities/listing/listing.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { getEntity, updateEntity, createEntity, reset } from './photos.reducer';
import { IPhotos } from 'app/shared/model/photos.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PhotosUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const albums = useAppSelector(state => state.album.entities);
  const listings = useAppSelector(state => state.listing.entities);
  const tags = useAppSelector(state => state.tag.entities);
  const photosEntity = useAppSelector(state => state.photos.entity);
  const loading = useAppSelector(state => state.photos.loading);
  const updating = useAppSelector(state => state.photos.updating);
  const updateSuccess = useAppSelector(state => state.photos.updateSuccess);

  const handleClose = () => {
    props.history.push('/photos');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAlbums({}));
    dispatch(getListings({}));
    dispatch(getTags({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.taken = convertDateTimeToServer(values.taken);
    values.uploaded = convertDateTimeToServer(values.uploaded);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedBy = convertDateTimeToServer(values.updatedBy);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...photosEntity,
      ...values,
      tags: mapIdList(values.tags),
      album: albums.find(it => it.id.toString() === values.albumId.toString()),
      listing: listings.find(it => it.id.toString() === values.listingId.toString()),
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
          taken: displayDefaultDateTime(),
          uploaded: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          updatedBy: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...photosEntity,
          taken: convertDateTimeFromServer(photosEntity.taken),
          uploaded: convertDateTimeFromServer(photosEntity.uploaded),
          createdDate: convertDateTimeFromServer(photosEntity.createdDate),
          updatedBy: convertDateTimeFromServer(photosEntity.updatedBy),
          updateDate: convertDateTimeFromServer(photosEntity.updateDate),
          albumId: photosEntity?.album?.id,
          listingId: photosEntity?.listing?.id,
          tags: photosEntity?.tags?.map(e => e.id.toString()),
        };

  const metadata = (
    <div>
      <ValidatedField label={translate('campsitesindiaApp.photo.height')} id="photo-height" name="height" data-cy="height" type="text" />
      <ValidatedField label={translate('campsitesindiaApp.photo.width')} id="photo-width" name="width" data-cy="width" type="text" />
      <ValidatedField
        label={translate('campsitesindiaApp.photo.taken')}
        id="photo-taken"
        name="taken"
        data-cy="taken"
        type="datetime-local"
        placeholder="YYYY-MM-DD HH:mm"
      />
      <ValidatedField
        label={translate('campsitesindiaApp.photo.uploaded')}
        id="photo-uploaded"
        name="uploaded"
        data-cy="uploaded"
        type="datetime-local"
        placeholder="YYYY-MM-DD HH:mm"
      />
    </div>
  );
  const metadataRows = isNew ? '' : metadata;

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campsitesindiaApp.photos.home.createOrEditLabel" data-cy="PhotosCreateUpdateHeading">
            <Translate contentKey="campsitesindiaApp.photos.home.createOrEditLabel">Create or edit a Photos</Translate>
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
                  id="photos-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('campsitesindiaApp.photos.alt')} id="photos-alt" name="alt" data-cy="alt" type="text" />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.caption')}
                id="photos-caption"
                name="caption"
                data-cy="caption"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.description')}
                id="photos-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('campsitesindiaApp.photos.href')} id="photos-href" name="href" data-cy="href" type="text" />
              <ValidatedField label={translate('campsitesindiaApp.photos.src')} id="photos-src" name="src" data-cy="src" type="text" />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.title')}
                id="photos-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedBlobField
                label={translate('campsitesindiaApp.photos.image')}
                id="photos-image"
                name="image"
                data-cy="image"
                isImage
                accept="image/*"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.isCoverImage')}
                id="photos-isCoverImage"
                name="isCoverImage"
                data-cy="isCoverImage"
                check
                type="checkbox"
              />
              {metadataRows}
              <ValidatedField
                label={translate('campsitesindiaApp.photos.taken')}
                id="photos-taken"
                name="taken"
                data-cy="taken"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.uploaded')}
                id="photos-uploaded"
                name="uploaded"
                data-cy="uploaded"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.createdBy')}
                id="photos-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.createdDate')}
                id="photos-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.updatedBy')}
                id="photos-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('campsitesindiaApp.photos.updateDate')}
                id="photos-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="photos-album"
                name="albumId"
                data-cy="album"
                label={translate('campsitesindiaApp.photos.album')}
                type="select"
              >
                <option value="" key="0" />
                {albums
                  ? albums.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="photos-listing"
                name="listingId"
                data-cy="listing"
                label={translate('campsitesindiaApp.photos.listing')}
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
                label={translate('campsitesindiaApp.photos.tag')}
                id="photos-tag"
                data-cy="tag"
                type="select"
                multiple
                name="tags"
              >
                <option value="" key="0" />
                {tags
                  ? tags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/photos" replace color="info">
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

export default PhotosUpdate;
