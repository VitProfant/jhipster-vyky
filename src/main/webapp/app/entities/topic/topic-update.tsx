import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArea } from 'app/shared/model/area.model';
import { getEntities as getAreas } from 'app/entities/area/area.reducer';
import { IVykyUser } from 'app/shared/model/vyky-user.model';
import { getEntities as getVykyUsers } from 'app/entities/vyky-user/vyky-user.reducer';
import { ITopic } from 'app/shared/model/topic.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { PostStatus } from 'app/shared/model/enumerations/post-status.model';
import { getEntity, updateEntity, createEntity, reset } from './topic.reducer';

export const TopicUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const areas = useAppSelector(state => state.area.entities);
  const vykyUsers = useAppSelector(state => state.vykyUser.entities);
  const topicEntity = useAppSelector(state => state.topic.entity);
  const loading = useAppSelector(state => state.topic.loading);
  const updating = useAppSelector(state => state.topic.updating);
  const updateSuccess = useAppSelector(state => state.topic.updateSuccess);
  const roleValues = Object.keys(Role);
  const postStatusValues = Object.keys(PostStatus);

  const handleClose = () => {
    navigate('/topic');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAreas({}));
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
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...topicEntity,
      ...values,
      area: areas.find(it => it.id.toString() === values.area.toString()),
      vykyUser: vykyUsers.find(it => it.id.toString() === values.vykyUser.toString()),
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
          minimalRole: 'ADMIN',
          status: 'REGULAR',
          ...topicEntity,
          createdAt: convertDateTimeFromServer(topicEntity.createdAt),
          updatedAt: convertDateTimeFromServer(topicEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(topicEntity.deletedAt),
          area: topicEntity?.area?.id,
          vykyUser: topicEntity?.vykyUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterVykyApp.topic.home.createOrEditLabel" data-cy="TopicCreateUpdateHeading">
            <Translate contentKey="jhipsterVykyApp.topic.home.createOrEditLabel">Create or edit a Topic</Translate>
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
                  id="topic-id"
                  label={translate('jhipsterVykyApp.topic.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterVykyApp.topic.version')}
                id="topic-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.topic.name')}
                id="topic-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.topic.description')}
                id="topic-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.topic.minimalRole')}
                id="topic-minimalRole"
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
                label={translate('jhipsterVykyApp.topic.status')}
                id="topic-status"
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
                label={translate('jhipsterVykyApp.topic.createdAt')}
                id="topic-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.topic.updatedAt')}
                id="topic-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.topic.deletedAt')}
                id="topic-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="topic-area" name="area" data-cy="area" label={translate('jhipsterVykyApp.topic.area')} type="select">
                <option value="" key="0" />
                {areas
                  ? areas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="topic-vykyUser"
                name="vykyUser"
                data-cy="vykyUser"
                label={translate('jhipsterVykyApp.topic.vykyUser')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/topic" replace color="info">
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

export default TopicUpdate;
