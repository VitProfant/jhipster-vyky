import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConfig } from 'app/shared/model/config.model';
import { ConfigItem } from 'app/shared/model/enumerations/config-item.model';
import { getEntity, updateEntity, createEntity, reset } from './config.reducer';

export const ConfigUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const configEntity = useAppSelector(state => state.config.entity);
  const loading = useAppSelector(state => state.config.loading);
  const updating = useAppSelector(state => state.config.updating);
  const updateSuccess = useAppSelector(state => state.config.updateSuccess);
  const configItemValues = Object.keys(ConfigItem);

  const handleClose = () => {
    navigate('/config');
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
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...configEntity,
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
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          configItem: 'HIDDEN_POST_TEXT',
          ...configEntity,
          createdAt: convertDateTimeFromServer(configEntity.createdAt),
          updatedAt: convertDateTimeFromServer(configEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(configEntity.deletedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterVykyApp.config.home.createOrEditLabel" data-cy="ConfigCreateUpdateHeading">
            <Translate contentKey="jhipsterVykyApp.config.home.createOrEditLabel">Create or edit a Config</Translate>
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
                  id="config-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterVykyApp.config.configItem')}
                id="config-configItem"
                name="configItem"
                data-cy="configItem"
                type="select"
              >
                {configItemValues.map(configItem => (
                  <option value={configItem} key={configItem}>
                    {translate('jhipsterVykyApp.ConfigItem.' + configItem)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterVykyApp.config.version')}
                id="config-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.config.value')}
                id="config-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.config.createdAt')}
                id="config-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.config.updatedAt')}
                id="config-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.config.deletedAt')}
                id="config-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/config" replace color="info">
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

export default ConfigUpdate;
