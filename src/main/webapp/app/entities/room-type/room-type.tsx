import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './room-type.reducer';
import { IRoomType } from 'app/shared/model/room-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoomType = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const roomTypeList = useAppSelector(state => state.roomType.entities);
  const loading = useAppSelector(state => state.roomType.loading);
  const totalItems = useAppSelector(state => state.roomType.totalItems);

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
      <h2 id="room-type-heading" data-cy="RoomTypeHeading">
        <Translate contentKey="tripperNestApp.roomType.home.title">Room Types</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="tripperNestApp.roomType.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="tripperNestApp.roomType.home.createLabel">Create new Room Type</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {roomTypeList && roomTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="tripperNestApp.roomType.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="tripperNestApp.roomType.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maxCapacity')}>
                  <Translate contentKey="tripperNestApp.roomType.maxCapacity">Max Capacity</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numberOfBeds')}>
                  <Translate contentKey="tripperNestApp.roomType.numberOfBeds">Number Of Beds</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numberOfBathrooms')}>
                  <Translate contentKey="tripperNestApp.roomType.numberOfBathrooms">Number Of Bathrooms</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('roomRatePerNigt')}>
                  <Translate contentKey="tripperNestApp.roomType.roomRatePerNigt">Room Rate Per Nigt</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="tripperNestApp.roomType.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="tripperNestApp.roomType.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="tripperNestApp.roomType.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="tripperNestApp.roomType.updateDate">Update Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {roomTypeList.map((roomType, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${roomType.id}`} color="link" size="sm">
                      {roomType.id}
                    </Button>
                  </td>
                  <td>{roomType.description}</td>
                  <td>{roomType.maxCapacity}</td>
                  <td>{roomType.numberOfBeds}</td>
                  <td>{roomType.numberOfBathrooms}</td>
                  <td>{roomType.roomRatePerNigt}</td>
                  <td>{roomType.createdBy}</td>
                  <td>{roomType.createdDate ? <TextFormat type="date" value={roomType.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{roomType.updatedBy ? <TextFormat type="date" value={roomType.updatedBy} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{roomType.updateDate ? <TextFormat type="date" value={roomType.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${roomType.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${roomType.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${roomType.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="tripperNestApp.roomType.home.notFound">No Room Types found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={roomTypeList && roomTypeList.length > 0 ? '' : 'd-none'}>
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

export default RoomType;
