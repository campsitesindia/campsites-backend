import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './features.reducer';
import { IFeatures } from 'app/shared/model/features.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Features = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const featuresList = useAppSelector(state => state.features.entities);
  const loading = useAppSelector(state => state.features.loading);
  const totalItems = useAppSelector(state => state.features.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="features-heading" data-cy="FeaturesHeading">
        <Translate contentKey="tripperNestApp.features.home.title">Features</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="tripperNestApp.features.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="tripperNestApp.features.home.createLabel">Create new Features</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {featuresList && featuresList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="tripperNestApp.features.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="tripperNestApp.features.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('count')}>
                  <Translate contentKey="tripperNestApp.features.count">Count</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('thumbnail')}>
                  <Translate contentKey="tripperNestApp.features.thumbnail">Thumbnail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('icon')}>
                  <Translate contentKey="tripperNestApp.features.icon">Icon</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('color')}>
                  <Translate contentKey="tripperNestApp.features.color">Color</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imgIcon')}>
                  <Translate contentKey="tripperNestApp.features.imgIcon">Img Icon</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="tripperNestApp.features.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('parent')}>
                  <Translate contentKey="tripperNestApp.features.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('taxonomy')}>
                  <Translate contentKey="tripperNestApp.features.taxonomy">Taxonomy</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="tripperNestApp.features.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="tripperNestApp.features.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="tripperNestApp.features.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="tripperNestApp.features.updateDate">Update Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {featuresList.map((features, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${features.id}`} color="link" size="sm">
                      {features.id}
                    </Button>
                  </td>
                  <td>{features.title}</td>
                  <td>{features.count}</td>
                  <td>{features.thumbnail}</td>
                  <td>{features.icon}</td>
                  <td>{features.color}</td>
                  <td>{features.imgIcon}</td>
                  <td>{features.description}</td>
                  <td>{features.parent}</td>
                  <td>{features.taxonomy}</td>
                  <td>{features.createdBy}</td>
                  <td>{features.createdDate ? <TextFormat type="date" value={features.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{features.updatedBy ? <TextFormat type="date" value={features.updatedBy} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{features.updateDate ? <TextFormat type="date" value={features.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${features.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${features.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${features.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="tripperNestApp.features.home.notFound">No Features found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={featuresList && featuresList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Features;
