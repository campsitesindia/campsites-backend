import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './photos.reducer';
import { IPhotos } from 'app/shared/model/photos.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import Gallery from 'react-photo-gallery';
export const Photos = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const photosList = useAppSelector(state => state.photos.entities);
  const loading = useAppSelector(state => state.photos.loading);
  const totalItems = useAppSelector(state => state.photos.totalItems);
  const links = useAppSelector(state => state.photos.links);
  const entity = useAppSelector(state => state.photos.entity);
  const updateSuccess = useAppSelector(state => state.photos.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;
  const photoSet = photosList.map(photo => ({
    src: `data:${photo.imageContentType};base64,${photo.image}`,
    width: photo.height > photo.width ? 3 : photo.height === photo.width ? 1 : 4,
    height: photo.height > photo.width ? 4 : photo.height === photo.width ? 1 : 3,
  }));

  return (
    <div>
      <h2 id="photos-heading" data-cy="PhotosHeading">
        <Translate contentKey="campsitesindiaApp.photos.home.title">Photos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="campsitesindiaApp.photos.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="campsitesindiaApp.photos.home.createLabel">Create new Photos</Translate>
          </Link>
        </div>
      </h2>
      <Gallery photos={photoSet} />
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {photosList && photosList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="campsitesindiaApp.photos.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('alt')}>
                    <Translate contentKey="campsitesindiaApp.photos.alt">Alt</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('caption')}>
                    <Translate contentKey="campsitesindiaApp.photos.caption">Caption</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="campsitesindiaApp.photos.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('href')}>
                    <Translate contentKey="campsitesindiaApp.photos.href">Href</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('src')}>
                    <Translate contentKey="campsitesindiaApp.photos.src">Src</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('title')}>
                    <Translate contentKey="campsitesindiaApp.photos.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('image')}>
                    <Translate contentKey="campsitesindiaApp.photos.image">Image</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isCoverImage')}>
                    <Translate contentKey="campsitesindiaApp.photos.isCoverImage">Is Cover Image</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('height')}>
                    <Translate contentKey="campsitesindiaApp.photos.height">Height</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('width')}>
                    <Translate contentKey="campsitesindiaApp.photos.width">Width</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('taken')}>
                    <Translate contentKey="campsitesindiaApp.photos.taken">Taken</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('uploaded')}>
                    <Translate contentKey="campsitesindiaApp.photos.uploaded">Uploaded</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="campsitesindiaApp.photos.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdDate')}>
                    <Translate contentKey="campsitesindiaApp.photos.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="campsitesindiaApp.photos.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updateDate')}>
                    <Translate contentKey="campsitesindiaApp.photos.updateDate">Update Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="campsitesindiaApp.photos.album">Album</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="campsitesindiaApp.photos.listing">Listing</Translate> <FontAwesomeIcon icon="sort" />
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
                    <td>
                      {photos.image ? (
                        <div>
                          {photos.imageContentType ? (
                            <a onClick={openFile(photos.imageContentType, photos.image)}>
                              <img src={`data:${photos.imageContentType};base64,${photos.image}`} style={{ maxHeight: '30px' }} />
                              &nbsp;
                            </a>
                          ) : null}
                          <span>
                            {photos.imageContentType}, {byteSize(photos.image)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{photos.isCoverImage ? 'true' : 'false'}</td>
                    <td>{photos.height}</td>
                    <td>{photos.width}</td>
                    <td>{photos.taken ? <TextFormat type="date" value={photos.taken} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{photos.uploaded ? <TextFormat type="date" value={photos.uploaded} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{photos.createdBy}</td>
                    <td>{photos.createdDate ? <TextFormat type="date" value={photos.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{photos.updatedBy ? <TextFormat type="date" value={photos.updatedBy} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{photos.updateDate ? <TextFormat type="date" value={photos.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{photos.album ? <Link to={`album/${photos.album.id}`}>{photos.album.title}</Link> : ''}</td>
                    <td>{photos.listing ? <Link to={`listing/${photos.listing.id}`}>{photos.listing.title}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${photos.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${photos.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${photos.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="campsitesindiaApp.photos.home.notFound">No Photos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Photos;
