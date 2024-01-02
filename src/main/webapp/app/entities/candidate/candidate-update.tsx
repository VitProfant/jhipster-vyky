import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICandidate } from 'app/shared/model/candidate.model';
import { getEntity, updateEntity, createEntity, reset } from './candidate.reducer';

export const CandidateUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const candidateEntity = useAppSelector(state => state.candidate.entity);
  const loading = useAppSelector(state => state.candidate.loading);
  const updating = useAppSelector(state => state.candidate.updating);
  const updateSuccess = useAppSelector(state => state.candidate.updateSuccess);

  const handleClose = () => {
    navigate('/candidate');
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
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...candidateEntity,
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
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...candidateEntity,
          createdAt: convertDateTimeFromServer(candidateEntity.createdAt),
          deletedAt: convertDateTimeFromServer(candidateEntity.deletedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterVykyApp.candidate.home.createOrEditLabel" data-cy="CandidateCreateUpdateHeading">
            <Translate contentKey="jhipsterVykyApp.candidate.home.createOrEditLabel">Create or edit a Candidate</Translate>
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
                  id="candidate-id"
                  label={translate('jhipsterVykyApp.candidate.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterVykyApp.candidate.login')}
                id="candidate-login"
                name="login"
                data-cy="login"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.candidate.password')}
                id="candidate-password"
                name="password"
                data-cy="password"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.candidate.email')}
                id="candidate-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.candidate.token')}
                id="candidate-token"
                name="token"
                data-cy="token"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.candidate.createdAt')}
                id="candidate-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.candidate.deletedAt')}
                id="candidate-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/candidate" replace color="info">
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

export default CandidateUpdate;
