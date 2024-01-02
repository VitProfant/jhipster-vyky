import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './thumb.reducer';

export const ThumbDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const thumbEntity = useAppSelector(state => state.thumb.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="thumbDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.thumb.detail.title">Thumb</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.thumb.id">Id</Translate>
            </span>
          </dt>
          <dd>{thumbEntity.id}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="jhipsterVykyApp.thumb.version">Version</Translate>
            </span>
          </dt>
          <dd>{thumbEntity.version}</dd>
          <dt>
            <span id="up">
              <Translate contentKey="jhipsterVykyApp.thumb.up">Up</Translate>
            </span>
          </dt>
          <dd>{thumbEntity.up ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.thumb.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{thumbEntity.createdAt ? <TextFormat value={thumbEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jhipsterVykyApp.thumb.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{thumbEntity.updatedAt ? <TextFormat value={thumbEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.thumb.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>{thumbEntity.deletedAt ? <TextFormat value={thumbEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.thumb.post">Post</Translate>
          </dt>
          <dd>{thumbEntity.post ? thumbEntity.post.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.thumb.vykyUser">Vyky User</Translate>
          </dt>
          <dd>{thumbEntity.vykyUser ? thumbEntity.vykyUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/thumb" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/thumb/${thumbEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ThumbDetail;
