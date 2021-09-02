import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './room-features.reducer';
import { IRoomFeatures } from 'app/shared/model/room-features.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomFeatures = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const roomFeaturesList = useAppSelector(state => state.roomFeatures.entities);
  const loading = useAppSelector(state => state.roomFeatures.loading);
  const totalItems = useAppSelector(state => state.roomFeatures.totalItems);

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
      <h2 id="room-features-heading" data-cy="RoomFeaturesHeading">
        <Translate contentKey="tripperNestApp.roomFeatures.home.title">Room Features</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="tripperNestApp.roomFeatures.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="tripperNestApp.roomFeatures.home.createLabel">Create new Room Features</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {roomFeaturesList && roomFeaturesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('count')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.count">Count</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('thumbnail')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.thumbnail">Thumbnail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('icon')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.icon">Icon</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('color')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.color">Color</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imgIcon')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.imgIcon">Img Icon</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('parent')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('taxonomy')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.taxonomy">Taxonomy</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="tripperNestApp.roomFeatures.updateDate">Update Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="tripperNestApp.roomFeatures.room">Room</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {roomFeaturesList.map((roomFeatures, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${roomFeatures.id}`} color="link" size="sm">
                      {roomFeatures.id}
                    </Button>
                  </td>
                  <td>{roomFeatures.title}</td>
                  <td>{roomFeatures.count}</td>
                  <td>{roomFeatures.thumbnail}</td>
                  <td>{roomFeatures.icon}</td>
                  <td>{roomFeatures.color}</td>
                  <td>{roomFeatures.imgIcon}</td>
                  <td>{roomFeatures.description}</td>
                  <td>{roomFeatures.parent}</td>
                  <td>{roomFeatures.taxonomy}</td>
                  <td>{roomFeatures.createdBy}</td>
                  <td>
                    {roomFeatures.createdDate ? <TextFormat type="date" value={roomFeatures.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {roomFeatures.updatedBy ? <TextFormat type="date" value={roomFeatures.updatedBy} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {roomFeatures.updateDate ? <TextFormat type="date" value={roomFeatures.updateDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{roomFeatures.room ? <Link to={`room/${roomFeatures.room.id}`}>{roomFeatures.room.roomNumber}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${roomFeatures.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${roomFeatures.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${roomFeatures.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="tripperNestApp.roomFeatures.home.notFound">No Room Features found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={roomFeaturesList && roomFeaturesList.length > 0 ? '' : 'd-none'}>
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

export default RoomFeatures;
