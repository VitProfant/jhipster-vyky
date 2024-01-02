import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITopic } from 'app/shared/model/topic.model';
import { getEntities as getTopics } from 'app/entities/topic/topic.reducer';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { IVykyUser } from 'app/shared/model/vyky-user.model';
import { getEntities as getVykyUsers } from 'app/entities/vyky-user/vyky-user.reducer';
import { IPost } from 'app/shared/model/post.model';
import { PostStatus } from 'app/shared/model/enumerations/post-status.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { getEntity, updateEntity, createEntity, reset } from './post.reducer';

export const PostUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const topics = useAppSelector(state => state.topic.entities);
  const posts = useAppSelector(state => state.post.entities);
  const vykyUsers = useAppSelector(state => state.vykyUser.entities);
  const postEntity = useAppSelector(state => state.post.entity);
  const loading = useAppSelector(state => state.post.loading);
  const updating = useAppSelector(state => state.post.updating);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);
  const postStatusValues = Object.keys(PostStatus);
  const roleValues = Object.keys(Role);

  const handleClose = () => {
    navigate('/post');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTopics({}));
    dispatch(getPosts({}));
    dispatch(getVykyUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.version !== undefined && typeof values.version !== 'number') {
      values.version = Number(values.version);
    }
    if (values.level !== undefined && typeof values.level !== 'number') {
      values.level = Number(values.level);
    }
    if (values.parentId !== undefined && typeof values.parentId !== 'number') {
      values.parentId = Number(values.parentId);
    }
    if (values.upvotes !== undefined && typeof values.upvotes !== 'number') {
      values.upvotes = Number(values.upvotes);
    }
    if (values.downvotes !== undefined && typeof values.downvotes !== 'number') {
      values.downvotes = Number(values.downvotes);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...postEntity,
      ...values,
      topic: topics.find(it => it.id.toString() === values.topic.toString()),
      parent: posts.find(it => it.id.toString() === values.parent.toString()),
      author: vykyUsers.find(it => it.id.toString() === values.author.toString()),
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
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          status: 'REGULAR',
          minimalRole: 'ADMIN',
          ...postEntity,
          createdAt: convertDateTimeFromServer(postEntity.createdAt),
          updatedAt: convertDateTimeFromServer(postEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(postEntity.deletedAt),
          topic: postEntity?.topic?.id,
          parent: postEntity?.parent?.id,
          author: postEntity?.author?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterVykyApp.post.home.createOrEditLabel" data-cy="PostCreateUpdateHeading">
            <Translate contentKey="jhipsterVykyApp.post.home.createOrEditLabel">Create or edit a Post</Translate>
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
                  id="post-id"
                  label={translate('jhipsterVykyApp.post.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterVykyApp.post.version')}
                id="post-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.level')}
                id="post-level"
                name="level"
                data-cy="level"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.title')}
                id="post-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.content')}
                id="post-content"
                name="content"
                data-cy="content"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.status')}
                id="post-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {postStatusValues.map(postStatus => (
                  <option value={postStatus} key={postStatus}>
                    {translate('jhipsterVykyApp.PostStatus.' + postStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterVykyApp.post.minimalRole')}
                id="post-minimalRole"
                name="minimalRole"
                data-cy="minimalRole"
                type="select"
              >
                {roleValues.map(role => (
                  <option value={role} key={role}>
                    {translate('jhipsterVykyApp.Role.' + role)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterVykyApp.post.parentId')}
                id="post-parentId"
                name="parentId"
                data-cy="parentId"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.upvotes')}
                id="post-upvotes"
                name="upvotes"
                data-cy="upvotes"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.downvotes')}
                id="post-downvotes"
                name="downvotes"
                data-cy="downvotes"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.createdAt')}
                id="post-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.updatedAt')}
                id="post-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.post.deletedAt')}
                id="post-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="post-topic" name="topic" data-cy="topic" label={translate('jhipsterVykyApp.post.topic')} type="select">
                <option value="" key="0" />
                {topics
                  ? topics.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="post-parent"
                name="parent"
                data-cy="parent"
                label={translate('jhipsterVykyApp.post.parent')}
                type="select"
              >
                <option value="" key="0" />
                {posts
                  ? posts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="post-author"
                name="author"
                data-cy="author"
                label={translate('jhipsterVykyApp.post.author')}
                type="select"
              >
                <option value="" key="0" />
                {vykyUsers
                  ? vykyUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/post" replace color="info">
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

export default PostUpdate;
