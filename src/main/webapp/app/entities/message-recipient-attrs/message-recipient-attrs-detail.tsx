import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './message-recipient-attrs.reducer';

export const MessageRecipientAttrsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const messageRecipientAttrsEntity = useAppSelector(state => state.messageRecipientAttrs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="messageRecipientAttrsDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.detail.title">MessageRecipientAttrs</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.id">Id</Translate>
            </span>
          </dt>
          <dd>{messageRecipientAttrsEntity.id}</dd>
          <dt>
            <span id="isRead">
              <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.isRead">Is Read</Translate>
            </span>
          </dt>
          <dd>{messageRecipientAttrsEntity.isRead ? 'true' : 'false'}</dd>
          <dt>
            <span id="isDeleted">
              <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.isDeleted">Is Deleted</Translate>
            </span>
          </dt>
          <dd>{messageRecipientAttrsEntity.isDeleted ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.message">Message</Translate>
          </dt>
          <dd>{messageRecipientAttrsEntity.message ? messageRecipientAttrsEntity.message.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.recipient">Recipient</Translate>
          </dt>
          <dd>{messageRecipientAttrsEntity.recipient ? messageRecipientAttrsEntity.recipient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/message-recipient-attrs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/message-recipient-attrs/${messageRecipientAttrsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MessageRecipientAttrsDetail;
