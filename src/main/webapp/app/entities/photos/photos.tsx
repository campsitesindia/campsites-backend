import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './photos.reducer';
import { IPhotos } from 'app/shared/model/photos.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Photos = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const photosList = useAppSelector(state => state.photos.entities);
  const loading = useAppSelector(state => state.photos.loading);
  const totalItems = useAppSelector(state => state.photos.totalItems);

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
      <h2 id="photos-heading" data-cy="PhotosHeading">
        <Translate contentKey="tripperNestApp.photos.home.title">Photos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="tripperNestApp.photos.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="tripperNestApp.photos.home.createLabel">Create new Photos</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {photosList && photosList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="tripperNestApp.photos.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('alt')}>
                  <Translate contentKey="tripperNestApp.photos.alt">Alt</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('caption')}>
                  <Translate contentKey="tripperNestApp.photos.caption">Caption</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="tripperNestApp.photos.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('href')}>
                  <Translate contentKey="tripperNestApp.photos.href">Href</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('src')}>
                  <Translate contentKey="tripperNestApp.photos.src">Src</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="tripperNestApp.photos.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="tripperNestApp.photos.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="tripperNestApp.photos.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="tripperNestApp.photos.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="tripperNestApp.photos.updateDate">Update Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="tripperNestApp.photos.listing">Listing</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {photosList.map((photos, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${photos.id}`} color="link" size="sm">
                      {photos.id}
                    </Button>
                  </td>
                  <td>{photos.alt}</td>
                  <td>{photos.caption}</td>
                  <td>{photos.description}</td>
                  <td>{photos.href}</td>
                  <td>{photos.src}</td>
                  <td>{photos.title}</td>
                  <td>{photos.createdBy}</td>
                  <td>{photos.createdDate ? <TextFormat type="date" value={photos.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{photos.updatedBy ? <TextFormat type="date" value={photos.updatedBy} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{photos.updateDate ? <TextFormat type="date" value={photos.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{photos.listing ? <Link to={`listing/${photos.listing.id}`}>{photos.listing.title}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${photos.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${photos.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${photos.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="tripperNestApp.photos.home.notFound">No Photos found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={photosList && photosList.length > 0 ? '' : 'd-none'}>
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

export default Photos;
