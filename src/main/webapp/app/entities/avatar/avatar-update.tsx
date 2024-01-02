import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVykyUser } from 'app/shared/model/vyky-user.model';
import { getEntities as getVykyUsers } from 'app/entities/vyky-user/vyky-user.reducer';
import { IAvatar } from 'app/shared/model/avatar.model';
import { getEntity, updateEntity, createEntity, reset } from './avatar.reducer';

export const AvatarUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vykyUsers = useAppSelector(state => state.vykyUser.entities);
  const avatarEntity = useAppSelector(state => state.avatar.entity);
  const loading = useAppSelector(state => state.avatar.loading);
  const updating = useAppSelector(state => state.avatar.updating);
  const updateSuccess = useAppSelector(state => state.avatar.updateSuccess);

  const handleClose = () => {
    navigate('/avatar');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    values.activationAt = convertDateTimeToServer(values.activationAt);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...avatarEntity,
      ...values,
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
          activationAt: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...avatarEntity,
          activationAt: convertDateTimeFromServer(avatarEntity.activationAt),
          createdAt: convertDateTimeFromServer(avatarEntity.createdAt),
          deletedAt: convertDateTimeFromServer(avatarEntity.deletedAt),
          vykyUser: avatarEntity?.vykyUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterVykyApp.avatar.home.createOrEditLabel" data-cy="AvatarCreateUpdateHeading">
            <Translate contentKey="jhipsterVykyApp.avatar.home.createOrEditLabel">Create or edit a Avatar</Translate>
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
                  id="avatar-id"
                  label={translate('jhipsterVykyApp.avatar.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterVykyApp.avatar.mime')}
                id="avatar-mime"
                name="mime"
                data-cy="mime"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('jhipsterVykyApp.avatar.img')}
                id="avatar-img"
                name="img"
                data-cy="img"
                isImage
                accept="image/*"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.avatar.active')}
                id="avatar-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.avatar.activationAt')}
                id="avatar-activationAt"
                name="activationAt"
                data-cy="activationAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.avatar.createdAt')}
                id="avatar-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.avatar.deletedAt')}
                id="avatar-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="avatar-vykyUser"
                name="vykyUser"
                data-cy="vykyUser"
                label={translate('jhipsterVykyApp.avatar.vykyUser')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/avatar" replace color="info">
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

export default AvatarUpdate;
