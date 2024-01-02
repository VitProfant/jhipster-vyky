import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './candidate.reducer';

export const CandidateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const candidateEntity = useAppSelector(state => state.candidate.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="candidateDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.candidate.detail.title">Candidate</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.candidate.id">Id</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.id}</dd>
          <dt>
            <span id="login">
              <Translate contentKey="jhipsterVykyApp.candidate.login">Login</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.login}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="jhipsterVykyApp.candidate.password">Password</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.password}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="jhipsterVykyApp.candidate.email">Email</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.email}</dd>
          <dt>
            <span id="token">
              <Translate contentKey="jhipsterVykyApp.candidate.token">Token</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.token}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.candidate.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {candidateEntity.createdAt ? <TextFormat value={candidateEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.candidate.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>
            {candidateEntity.deletedAt ? <TextFormat value={candidateEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/candidate" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/candidate/${candidateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CandidateDetail;
