import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPost } from 'app/shared/model/post.model';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ILike } from 'app/shared/model/like.model';
import { getEntities as getLikes } from 'app/entities/like/like.reducer';
import { getEntity, updateEntity, createEntity, reset } from './images.reducer';
import { IImages } from 'app/shared/model/images.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ImagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const posts = useAppSelector(state => state.post.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const likes = useAppSelector(state => state.like.entities);
  const imagesEntity = useAppSelector(state => state.images.entity);
  const loading = useAppSelector(state => state.images.loading);
  const updating = useAppSelector(state => state.images.updating);
  const updateSuccess = useAppSelector(state => state.images.updateSuccess);

  const handleClose = () => {
    props.history.push('/images' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPosts({}));
    dispatch(getUsers({}));
    dispatch(getLikes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedBy = convertDateTimeToServer(values.updatedBy);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...imagesEntity,
      ...values,
      post: posts.find(it => it.id.toString() === values.postId.toString()),
      user: users.find(it => it.id.toString() === values.userId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdDate: displayDefaultDateTime(),
          updatedBy: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...imagesEntity,
          createdDate: convertDateTimeFromServer(imagesEntity.createdDate),
          updatedBy: convertDateTimeFromServer(imagesEntity.updatedBy),
          updateDate: convertDateTimeFromServer(imagesEntity.updateDate),
          postId: imagesEntity?.post?.id,
          userId: imagesEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tripperNestApp.images.home.createOrEditLabel" data-cy="ImagesCreateUpdateHeading">
            <Translate contentKey="tripperNestApp.images.home.createOrEditLabel">Create or edit a Images</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="images-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('tripperNestApp.images.imageUrl')}
                id="images-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.images.createdBy')}
                id="images-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('tripperNestApp.images.createdDate')}
                id="images-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.images.updatedBy')}
                id="images-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('tripperNestApp.images.updateDate')}
                id="images-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="images-post" name="postId" data-cy="post" label={translate('tripperNestApp.images.post')} type="select">
                <option value="" key="0" />
                {posts
                  ? posts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="images-user" name="userId" data-cy="user" label={translate('tripperNestApp.images.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.email}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/images" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ImagesUpdate;
