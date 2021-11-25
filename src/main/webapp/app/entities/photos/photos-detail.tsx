import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './photos.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PhotosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const photosEntity = useAppSelector(state => state.photos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="photosDetailsHeading">
          <Translate contentKey="campsitesindiaApp.photos.detail.title">Photos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{photosEntity.id}</dd>
          <dt>
            <span id="alt">
              <Translate contentKey="campsitesindiaApp.photos.alt">Alt</Translate>
            </span>
          </dt>
          <dd>{photosEntity.alt}</dd>
          <dt>
            <span id="caption">
              <Translate contentKey="campsitesindiaApp.photos.caption">Caption</Translate>
            </span>
          </dt>
          <dd>{photosEntity.caption}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="campsitesindiaApp.photos.description">Description</Translate>
            </span>
          </dt>
          <dd>{photosEntity.description}</dd>
          <dt>
            <span id="href">
              <Translate contentKey="campsitesindiaApp.photos.href">Href</Translate>
            </span>
          </dt>
          <dd>{photosEntity.href}</dd>
          <dt>
            <span id="src">
              <Translate contentKey="campsitesindiaApp.photos.src">Src</Translate>
            </span>
          </dt>
          <dd>{photosEntity.src}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="campsitesindiaApp.photos.title">Title</Translate>
            </span>
          </dt>
          <dd>{photosEntity.title}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="campsitesindiaApp.photos.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {photosEntity.image ? (
              <div>
                {photosEntity.imageContentType ? (
                  <a onClick={openFile(photosEntity.imageContentType, photosEntity.image)}>
                    <img src={`data:${photosEntity.imageContentType};base64,${photosEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {photosEntity.imageContentType}, {byteSize(photosEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="isCoverImage">
              <Translate contentKey="campsitesindiaApp.photos.isCoverImage">Is Cover Image</Translate>
            </span>
          </dt>
          <dd>{photosEntity.isCoverImage ? 'true' : 'false'}</dd>
          <dt>
            <span id="height">
              <Translate contentKey="campsitesindiaApp.photos.height">Height</Translate>
            </span>
          </dt>
          <dd>{photosEntity.height}</dd>
          <dt>
            <span id="width">
              <Translate contentKey="campsitesindiaApp.photos.width">Width</Translate>
            </span>
          </dt>
          <dd>{photosEntity.width}</dd>
          <dt>
            <span id="taken">
              <Translate contentKey="campsitesindiaApp.photos.taken">Taken</Translate>
            </span>
          </dt>
          <dd>{photosEntity.taken ? <TextFormat value={photosEntity.taken} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="uploaded">
              <Translate contentKey="campsitesindiaApp.photos.uploaded">Uploaded</Translate>
            </span>
          </dt>
          <dd>{photosEntity.uploaded ? <TextFormat value={photosEntity.uploaded} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.photos.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{photosEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.photos.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{photosEntity.createdDate ? <TextFormat value={photosEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.photos.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{photosEntity.updatedBy ? <TextFormat value={photosEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.photos.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{photosEntity.updateDate ? <TextFormat value={photosEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.photos.album">Album</Translate>
          </dt>
          <dd>{photosEntity.album ? photosEntity.album.title : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.photos.listing">Listing</Translate>
          </dt>
          <dd>{photosEntity.listing ? photosEntity.listing.title : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.photos.tag">Tag</Translate>
          </dt>
          <dd>
            {photosEntity.tags
              ? photosEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {photosEntity.tags && i === photosEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/photos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/photos/${photosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PhotosDetail;
