import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './config.reducer';

export const ConfigDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const configEntity = useAppSelector(state => state.config.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="configDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.config.detail.title">Config</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{configEntity.id}</dd>
          <dt>
            <span id="configItem">
              <Translate contentKey="jhipsterVykyApp.config.configItem">Config Item</Translate>
            </span>
          </dt>
          <dd>{configEntity.configItem}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="jhipsterVykyApp.config.version">Version</Translate>
            </span>
          </dt>
          <dd>{configEntity.version}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="jhipsterVykyApp.config.value">Value</Translate>
            </span>
          </dt>
          <dd>{configEntity.value}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.config.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{configEntity.createdAt ? <TextFormat value={configEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jhipsterVykyApp.config.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{configEntity.updatedAt ? <TextFormat value={configEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.config.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>{configEntity.deletedAt ? <TextFormat value={configEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/config" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/config/${configEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConfigDetail;
