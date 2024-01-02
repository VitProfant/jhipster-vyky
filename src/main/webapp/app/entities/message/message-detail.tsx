import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './message.reducer';

export const MessageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const messageEntity = useAppSelector(state => state.message.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="messageDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.message.detail.title">Message</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.message.id">Id</Translate>
            </span>
          </dt>
          <dd>{messageEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="jhipsterVykyApp.message.content">Content</Translate>
            </span>
          </dt>
          <dd>{messageEntity.content}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.message.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{messageEntity.createdAt ? <TextFormat value={messageEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.message.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>{messageEntity.deletedAt ? <TextFormat value={messageEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedByAuthor">
              <Translate contentKey="jhipsterVykyApp.message.deletedByAuthor">Deleted By Author</Translate>
            </span>
          </dt>
          <dd>{messageEntity.deletedByAuthor ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.message.parent">Parent</Translate>
          </dt>
          <dd>{messageEntity.parent ? messageEntity.parent.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.message.author">Author</Translate>
          </dt>
          <dd>{messageEntity.author ? messageEntity.author.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/message" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/message/${messageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MessageDetail;
