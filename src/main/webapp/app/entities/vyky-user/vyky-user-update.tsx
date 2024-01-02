import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVykyUser } from 'app/shared/model/vyky-user.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { getEntity, updateEntity, createEntity, reset } from './vyky-user.reducer';

export const VykyUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vykyUserEntity = useAppSelector(state => state.vykyUser.entity);
  const loading = useAppSelector(state => state.vykyUser.loading);
  const updating = useAppSelector(state => state.vykyUser.updating);
  const updateSuccess = useAppSelector(state => state.vykyUser.updateSuccess);
  const roleValues = Object.keys(Role);

  const handleClose = () => {
    navigate('/vyky-user');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    values.messagesReadAt = convertDateTimeToServer(values.messagesReadAt);
    values.reactionsReadAt = convertDateTimeToServer(values.reactionsReadAt);
    values.lastActivity = convertDateTimeToServer(values.lastActivity);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...vykyUserEntity,
      ...values,
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
          messagesReadAt: displayDefaultDateTime(),
          reactionsReadAt: displayDefaultDateTime(),
          lastActivity: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          role: 'ADMIN',
          ...vykyUserEntity,
          messagesReadAt: convertDateTimeFromServer(vykyUserEntity.messagesReadAt),
          reactionsReadAt: convertDateTimeFromServer(vykyUserEntity.reactionsReadAt),
          lastActivity: convertDateTimeFromServer(vykyUserEntity.lastActivity),
          createdAt: convertDateTimeFromServer(vykyUserEntity.createdAt),
          updatedAt: convertDateTimeFromServer(vykyUserEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(vykyUserEntity.deletedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterVykyApp.vykyUser.home.createOrEditLabel" data-cy="VykyUserCreateUpdateHeading">
            <Translate contentKey="jhipsterVykyApp.vykyUser.home.createOrEditLabel">Create or edit a VykyUser</Translate>
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
                  id="vyky-user-id"
                  label={translate('jhipsterVykyApp.vykyUser.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.version')}
                id="vyky-user-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.login')}
                id="vyky-user-login"
                name="login"
                data-cy="login"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.password')}
                id="vyky-user-password"
                name="password"
                data-cy="password"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.email')}
                id="vyky-user-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.role')}
                id="vyky-user-role"
                name="role"
                data-cy="role"
                type="select"
              >
                {roleValues.map(role => (
                  <option value={role} key={role}>
                    {translate('jhipsterVykyApp.Role.' + role)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.messagesReadAt')}
                id="vyky-user-messagesReadAt"
                name="messagesReadAt"
                data-cy="messagesReadAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.reactionsReadAt')}
                id="vyky-user-reactionsReadAt"
                name="reactionsReadAt"
                data-cy="reactionsReadAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.lastActivity')}
                id="vyky-user-lastActivity"
                name="lastActivity"
                data-cy="lastActivity"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.createdAt')}
                id="vyky-user-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.updatedAt')}
                id="vyky-user-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.vykyUser.deletedAt')}
                id="vyky-user-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vyky-user" replace color="info">
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

export default VykyUserUpdate;
