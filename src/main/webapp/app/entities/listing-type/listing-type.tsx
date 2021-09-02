import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './listing-type.reducer';
import { IListingType } from 'app/shared/model/listing-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListingType = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const listingTypeList = useAppSelector(state => state.listingType.entities);
  const loading = useAppSelector(state => state.listingType.loading);
  const totalItems = useAppSelector(state => state.listingType.totalItems);

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
      <h2 id="listing-type-heading" data-cy="ListingTypeHeading">
        <Translate contentKey="tripperNestApp.listingType.home.title">Listing Types</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="tripperNestApp.listingType.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="tripperNestApp.listingType.home.createLabel">Create new Listing Type</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {listingTypeList && listingTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="tripperNestApp.listingType.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="tripperNestApp.listingType.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('count')}>
                  <Translate contentKey="tripperNestApp.listingType.count">Count</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('thumbnail')}>
                  <Translate contentKey="tripperNestApp.listingType.thumbnail">Thumbnail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('icon')}>
                  <Translate contentKey="tripperNestApp.listingType.icon">Icon</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('color')}>
                  <Translate contentKey="tripperNestApp.listingType.color">Color</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imgIcon')}>
                  <Translate contentKey="tripperNestApp.listingType.imgIcon">Img Icon</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="tripperNestApp.listingType.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('parent')}>
                  <Translate contentKey="tripperNestApp.listingType.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('taxonomy')}>
                  <Translate contentKey="tripperNestApp.listingType.taxonomy">Taxonomy</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="tripperNestApp.listingType.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="tripperNestApp.listingType.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="tripperNestApp.listingType.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="tripperNestApp.listingType.updateDate">Update Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {listingTypeList.map((listingType, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${listingType.id}`} color="link" size="sm">
                      {listingType.id}
                    </Button>
                  </td>
                  <td>{listingType.title}</td>
                  <td>{listingType.count}</td>
                  <td>{listingType.thumbnail}</td>
                  <td>{listingType.icon}</td>
                  <td>{listingType.color}</td>
                  <td>{listingType.imgIcon}</td>
                  <td>{listingType.description}</td>
                  <td>{listingType.parent}</td>
                  <td>{listingType.taxonomy}</td>
                  <td>{listingType.createdBy}</td>
                  <td>
                    {listingType.createdDate ? <TextFormat type="date" value={listingType.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {listingType.updatedBy ? <TextFormat type="date" value={listingType.updatedBy} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {listingType.updateDate ? <TextFormat type="date" value={listingType.updateDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${listingType.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${listingType.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${listingType.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="tripperNestApp.listingType.home.notFound">No Listing Types found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={listingTypeList && listingTypeList.length > 0 ? '' : 'd-none'}>
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

export default ListingType;
