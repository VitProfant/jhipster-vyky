import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './topic.reducer';

export const TopicDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const topicEntity = useAppSelector(state => state.topic.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="topicDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.topic.detail.title">Topic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.topic.id">Id</Translate>
            </span>
          </dt>
          <dd>{topicEntity.id}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="jhipsterVykyApp.topic.version">Version</Translate>
            </span>
          </dt>
          <dd>{topicEntity.version}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterVykyApp.topic.name">Name</Translate>
            </span>
          </dt>
          <dd>{topicEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterVykyApp.topic.description">Description</Translate>
            </span>
          </dt>
          <dd>{topicEntity.description}</dd>
          <dt>
            <span id="minimalRole">
              <Translate contentKey="jhipsterVykyApp.topic.minimalRole">Minimal Role</Translate>
            </span>
          </dt>
          <dd>{topicEntity.minimalRole}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="jhipsterVykyApp.topic.status">Status</Translate>
            </span>
          </dt>
          <dd>{topicEntity.status}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.topic.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{topicEntity.createdAt ? <TextFormat value={topicEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jhipsterVykyApp.topic.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{topicEntity.updatedAt ? <TextFormat value={topicEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.topic.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>{topicEntity.deletedAt ? <TextFormat value={topicEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.topic.area">Area</Translate>
          </dt>
          <dd>{topicEntity.area ? topicEntity.area.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.topic.vykyUser">Vyky User</Translate>
          </dt>
          <dd>{topicEntity.vykyUser ? topicEntity.vykyUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/topic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/topic/${topicEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TopicDetail;
