import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './listing.reducer';
import { IListing } from 'app/shared/model/listing.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Listing = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const listingList = useAppSelector(state => state.listing.entities);
  const loading = useAppSelector(state => state.listing.loading);
  const totalItems = useAppSelector(state => state.listing.totalItems);

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
      <h2 id="listing-heading" data-cy="ListingHeading">
        <Translate contentKey="tripperNestApp.listing.home.title">Listings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="tripperNestApp.listing.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="tripperNestApp.listing.home.createLabel">Create new Listing</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {listingList && listingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="tripperNestApp.listing.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="tripperNestApp.listing.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('latitude')}>
                  <Translate contentKey="tripperNestApp.listing.latitude">Latitude</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('longitude')}>
                  <Translate contentKey="tripperNestApp.listing.longitude">Longitude</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('url')}>
                  <Translate contentKey="tripperNestApp.listing.url">Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="tripperNestApp.listing.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('content')}>
                  <Translate contentKey="tripperNestApp.listing.content">Content</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('thumbnail')}>
                  <Translate contentKey="tripperNestApp.listing.thumbnail">Thumbnail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isFeatured')}>
                  <Translate contentKey="tripperNestApp.listing.isFeatured">Is Featured</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phone')}>
                  <Translate contentKey="tripperNestApp.listing.phone">Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="tripperNestApp.listing.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('website')}>
                  <Translate contentKey="tripperNestApp.listing.website">Website</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  <Translate contentKey="tripperNestApp.listing.comment">Comment</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('disableBooking')}>
                  <Translate contentKey="tripperNestApp.listing.disableBooking">Disable Booking</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('viewCount')}>
                  <Translate contentKey="tripperNestApp.listing.viewCount">View Count</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="tripperNestApp.listing.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="tripperNestApp.listing.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="tripperNestApp.listing.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="tripperNestApp.listing.updateDate">Update Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="tripperNestApp.listing.listingType">Listing Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="tripperNestApp.listing.rating">Rating</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="tripperNestApp.listing.location">Location</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="tripperNestApp.listing.feature">Feature</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="tripperNestApp.listing.room">Room</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="tripperNestApp.listing.owner">Owner</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {listingList.map((listing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${listing.id}`} color="link" size="sm">
                      {listing.id}
                    </Button>
                  </td>
                  <td>{listing.address}</td>
                  <td>{listing.latitude}</td>
                  <td>{listing.longitude}</td>
                  <td>{listing.url}</td>
                  <td>{listing.title}</td>
                  <td>{listing.content}</td>
                  <td>{listing.thumbnail}</td>
                  <td>{listing.isFeatured ? 'true' : 'false'}</td>
                  <td>{listing.phone}</td>
                  <td>{listing.email}</td>
                  <td>{listing.website}</td>
                  <td>{listing.comment ? 'true' : 'false'}</td>
                  <td>{listing.disableBooking ? 'true' : 'false'}</td>
                  <td>{listing.viewCount}</td>
                  <td>{listing.createdBy}</td>
                  <td>{listing.createdDate ? <TextFormat type="date" value={listing.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{listing.updatedBy ? <TextFormat type="date" value={listing.updatedBy} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{listing.updateDate ? <TextFormat type="date" value={listing.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {listing.listingType ? <Link to={`listing-type/${listing.listingType.id}`}>{listing.listingType.title}</Link> : ''}
                  </td>
                  <td>{listing.rating ? <Link to={`rating/${listing.rating.id}`}>{listing.rating.name}</Link> : ''}</td>
                  <td>{listing.location ? <Link to={`location/${listing.location.id}`}>{listing.location.title}</Link> : ''}</td>
                  <td>{listing.feature ? <Link to={`features/${listing.feature.id}`}>{listing.feature.title}</Link> : ''}</td>
                  <td>{listing.room ? <Link to={`room/${listing.room.id}`}>{listing.room.roomNumber}</Link> : ''}</td>
                  <td>{listing.owner ? listing.owner.email : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${listing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${listing.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${listing.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="tripperNestApp.listing.home.notFound">No Listings found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={listingList && listingList.length > 0 ? '' : 'd-none'}>
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

export default Listing;
