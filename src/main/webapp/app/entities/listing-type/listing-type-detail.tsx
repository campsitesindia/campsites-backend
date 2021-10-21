import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './listing-type.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListingTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const listingTypeEntity = useAppSelector(state => state.listingType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="listingTypeDetailsHeading">
          <Translate contentKey="campsitesindiaApp.listingType.detail.title">ListingType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="campsitesindiaApp.listingType.title">Title</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.title}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="campsitesindiaApp.listingType.count">Count</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.count}</dd>
          <dt>
            <span id="thumbnail">
              <Translate contentKey="campsitesindiaApp.listingType.thumbnail">Thumbnail</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.thumbnail}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="campsitesindiaApp.listingType.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.icon}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="campsitesindiaApp.listingType.color">Color</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.color}</dd>
          <dt>
            <span id="imgIcon">
              <Translate contentKey="campsitesindiaApp.listingType.imgIcon">Img Icon</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.imgIcon}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="campsitesindiaApp.listingType.description">Description</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.description}</dd>
          <dt>
            <span id="taxonomy">
              <Translate contentKey="campsitesindiaApp.listingType.taxonomy">Taxonomy</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.taxonomy}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.listingType.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{listingTypeEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.listingType.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {listingTypeEntity.createdDate ? (
              <TextFormat value={listingTypeEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.listingType.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>
            {listingTypeEntity.updatedBy ? <TextFormat value={listingTypeEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.listingType.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {listingTypeEntity.updateDate ? <TextFormat value={listingTypeEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.listingType.parent">Parent</Translate>
          </dt>
          <dd>{listingTypeEntity.parent ? listingTypeEntity.parent.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/listing-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/listing-type/${listingTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ListingTypeDetail;
