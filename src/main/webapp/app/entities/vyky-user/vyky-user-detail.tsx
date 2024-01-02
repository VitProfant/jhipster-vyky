import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vyky-user.reducer';

export const VykyUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vykyUserEntity = useAppSelector(state => state.vykyUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vykyUserDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.vykyUser.detail.title">VykyUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.vykyUser.id">Id</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.id}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="jhipsterVykyApp.vykyUser.version">Version</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.version}</dd>
          <dt>
            <span id="login">
              <Translate contentKey="jhipsterVykyApp.vykyUser.login">Login</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.login}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="jhipsterVykyApp.vykyUser.password">Password</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.password}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="jhipsterVykyApp.vykyUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.email}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="jhipsterVykyApp.vykyUser.role">Role</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.role}</dd>
          <dt>
            <span id="messagesReadAt">
              <Translate contentKey="jhipsterVykyApp.vykyUser.messagesReadAt">Messages Read At</Translate>
            </span>
          </dt>
          <dd>
            {vykyUserEntity.messagesReadAt ? (
              <TextFormat value={vykyUserEntity.messagesReadAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="reactionsReadAt">
              <Translate contentKey="jhipsterVykyApp.vykyUser.reactionsReadAt">Reactions Read At</Translate>
            </span>
          </dt>
          <dd>
            {vykyUserEntity.reactionsReadAt ? (
              <TextFormat value={vykyUserEntity.reactionsReadAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastActivity">
              <Translate contentKey="jhipsterVykyApp.vykyUser.lastActivity">Last Activity</Translate>
            </span>
          </dt>
          <dd>
            {vykyUserEntity.lastActivity ? <TextFormat value={vykyUserEntity.lastActivity} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.vykyUser.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.createdAt ? <TextFormat value={vykyUserEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jhipsterVykyApp.vykyUser.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.updatedAt ? <TextFormat value={vykyUserEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.vykyUser.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>{vykyUserEntity.deletedAt ? <TextFormat value={vykyUserEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/vyky-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vyky-user/${vykyUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VykyUserDetail;
