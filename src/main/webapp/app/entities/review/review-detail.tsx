import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './review.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReviewDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const reviewEntity = useAppSelector(state => state.review.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reviewDetailsHeading">
          <Translate contentKey="campsitesindiaApp.review.detail.title">Review</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.id}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="campsitesindiaApp.review.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.rating}</dd>
          <dt>
            <span id="reviewbBody">
              <Translate contentKey="campsitesindiaApp.review.reviewbBody">Reviewb Body</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.reviewbBody}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campsitesindiaApp.review.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="campsitesindiaApp.review.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.createdDate ? <TextFormat value={reviewEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campsitesindiaApp.review.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.updatedBy ? <TextFormat value={reviewEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="campsitesindiaApp.review.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.updateDate ? <TextFormat value={reviewEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.review.listing">Listing</Translate>
          </dt>
          <dd>{reviewEntity.listing ? reviewEntity.listing.title : ''}</dd>
          <dt>
            <Translate contentKey="campsitesindiaApp.review.booking">Booking</Translate>
          </dt>
          <dd>{reviewEntity.booking ? reviewEntity.booking.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/review" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/review/${reviewEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReviewDetail;
