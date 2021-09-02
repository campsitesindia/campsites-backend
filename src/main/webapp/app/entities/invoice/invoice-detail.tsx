import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './invoice.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InvoiceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="invoiceDetailsHeading">
          <Translate contentKey="tripperNestApp.invoice.detail.title">Invoice</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.id}</dd>
          <dt>
            <span id="invoiceAmount">
              <Translate contentKey="tripperNestApp.invoice.invoiceAmount">Invoice Amount</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.invoiceAmount}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="tripperNestApp.invoice.status">Status</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.status}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="tripperNestApp.invoice.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="tripperNestApp.invoice.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.createdDate ? <TextFormat value={invoiceEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="tripperNestApp.invoice.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.updatedBy ? <TextFormat value={invoiceEntity.updatedBy} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="tripperNestApp.invoice.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.updateDate ? <TextFormat value={invoiceEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="tripperNestApp.invoice.bookings">Bookings</Translate>
          </dt>
          <dd>{invoiceEntity.bookings ? invoiceEntity.bookings.id : ''}</dd>
          <dt>
            <Translate contentKey="tripperNestApp.invoice.customer">Customer</Translate>
          </dt>
          <dd>{invoiceEntity.customer ? invoiceEntity.customer.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/invoice" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice/${invoiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InvoiceDetail;
