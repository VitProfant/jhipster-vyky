import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './area.reducer';

export const AreaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const areaEntity = useAppSelector(state => state.area.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="areaDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.area.detail.title">Area</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.area.id">Id</Translate>
            </span>
          </dt>
          <dd>{areaEntity.id}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="jhipsterVykyApp.area.version">Version</Translate>
            </span>
          </dt>
          <dd>{areaEntity.version}</dd>
          <dt>
            <span id="level">
              <Translate contentKey="jhipsterVykyApp.area.level">Level</Translate>
            </span>
          </dt>
          <dd>{areaEntity.level}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterVykyApp.area.name">Name</Translate>
            </span>
          </dt>
          <dd>{areaEntity.name}</dd>
          <dt>
            <span id="minimalRole">
              <Translate contentKey="jhipsterVykyApp.area.minimalRole">Minimal Role</Translate>
            </span>
          </dt>
          <dd>{areaEntity.minimalRole}</dd>
          <dt>
            <span id="parentId">
              <Translate contentKey="jhipsterVykyApp.area.parentId">Parent Id</Translate>
            </span>
          </dt>
          <dd>{areaEntity.parentId}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.area.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{areaEntity.createdAt ? <TextFormat value={areaEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jhipsterVykyApp.area.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{areaEntity.updatedAt ? <TextFormat value={areaEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.area.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>{areaEntity.deletedAt ? <TextFormat value={areaEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.area.parent">Parent</Translate>
          </dt>
          <dd>{areaEntity.parent ? areaEntity.parent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/area" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/area/${areaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AreaDetail;
