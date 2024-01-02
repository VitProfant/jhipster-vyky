import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAreas } from 'app/entities/area/area.reducer';
import { IArea } from 'app/shared/model/area.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { getEntity, updateEntity, createEntity, reset } from './area.reducer';

export const AreaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const areas = useAppSelector(state => state.area.entities);
  const areaEntity = useAppSelector(state => state.area.entity);
  const loading = useAppSelector(state => state.area.loading);
  const updating = useAppSelector(state => state.area.updating);
  const updateSuccess = useAppSelector(state => state.area.updateSuccess);
  const roleValues = Object.keys(Role);

  const handleClose = () => {
    navigate('/area');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAreas({}));
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
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...areaEntity,
      ...values,
      parent: areas.find(it => it.id.toString() === values.parent.toString()),
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
          ...areaEntity,
          createdAt: convertDateTimeFromServer(areaEntity.createdAt),
          updatedAt: convertDateTimeFromServer(areaEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(areaEntity.deletedAt),
          parent: areaEntity?.parent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterVykyApp.area.home.createOrEditLabel" data-cy="AreaCreateUpdateHeading">
            <Translate contentKey="jhipsterVykyApp.area.home.createOrEditLabel">Create or edit a Area</Translate>
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
                  id="area-id"
                  label={translate('jhipsterVykyApp.area.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterVykyApp.area.version')}
                id="area-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.area.level')}
                id="area-level"
                name="level"
                data-cy="level"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.area.name')}
                id="area-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.area.minimalRole')}
                id="area-minimalRole"
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
                label={translate('jhipsterVykyApp.area.parentId')}
                id="area-parentId"
                name="parentId"
                data-cy="parentId"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.area.createdAt')}
                id="area-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.area.updatedAt')}
                id="area-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.area.deletedAt')}
                id="area-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="area-parent"
                name="parent"
                data-cy="parent"
                label={translate('jhipsterVykyApp.area.parent')}
                type="select"
              >
                <option value="" key="0" />
                {areas
                  ? areas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/area" replace color="info">
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

export default AreaUpdate;
