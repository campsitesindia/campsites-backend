import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './features.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FeaturesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const featuresEntity = useAppSelector(state => state.features.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="featuresDetailsHeading">
          <Translate contentKey="campsitesindiaApp.features.detail.title">Features</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="campsitesindiaApp.features.title">Title</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.title}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="campsitesindiaApp.features.count">Count</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.count}</dd>
          <dt>
            <span id="thumbnail">
              <Translate contentKey="campsitesindiaApp.features.thumbnail">Thumbnail</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.thumbnail}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="campsitesindiaApp.features.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.icon}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="campsitesindiaApp.features.color">Color</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.color}</dd>
          <dt>
            <span id="imgIcon">
              <Translate contentKey="campsitesindiaApp.features.imgIcon">Img Icon</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.imgIcon}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="campsitesindiaApp.features.description">Description</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.description}</dd>
          <dt>
            <span id="parent">
              <Translate contentKey="campsitesindiaApp.features.parent">Parent</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.parent}</dd>
          <dt>
            <span id="taxonomy">
              <Translate contentKey="campsitesindiaApp.features.taxonomy">Taxonomy</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.taxonomy}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.features.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.features.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {featuresEntity.createdDate ? <TextFormat value={featuresEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.features.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{featuresEntity.updatedBy ? <TextFormat value={featuresEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.features.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {featuresEntity.updateDate ? <TextFormat value={featuresEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/features" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/features/${featuresEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeaturesDetail;
