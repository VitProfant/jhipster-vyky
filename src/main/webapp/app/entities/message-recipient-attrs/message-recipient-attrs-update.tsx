import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMessage } from 'app/shared/model/message.model';
import { getEntities as getMessages } from 'app/entities/message/message.reducer';
import { IVykyUser } from 'app/shared/model/vyky-user.model';
import { getEntities as getVykyUsers } from 'app/entities/vyky-user/vyky-user.reducer';
import { IMessageRecipientAttrs } from 'app/shared/model/message-recipient-attrs.model';
import { getEntity, updateEntity, createEntity, reset } from './message-recipient-attrs.reducer';

export const MessageRecipientAttrsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const messages = useAppSelector(state => state.message.entities);
  const vykyUsers = useAppSelector(state => state.vykyUser.entities);
  const messageRecipientAttrsEntity = useAppSelector(state => state.messageRecipientAttrs.entity);
  const loading = useAppSelector(state => state.messageRecipientAttrs.loading);
  const updating = useAppSelector(state => state.messageRecipientAttrs.updating);
  const updateSuccess = useAppSelector(state => state.messageRecipientAttrs.updateSuccess);

  const handleClose = () => {
    navigate('/message-recipient-attrs');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMessages({}));
    dispatch(getVykyUsers({}));
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

    const entity = {
      ...messageRecipientAttrsEntity,
      ...values,
      message: messages.find(it => it.id.toString() === values.message.toString()),
      recipient: vykyUsers.find(it => it.id.toString() === values.recipient.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...messageRecipientAttrsEntity,
          message: messageRecipientAttrsEntity?.message?.id,
          recipient: messageRecipientAttrsEntity?.recipient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterVykyApp.messageRecipientAttrs.home.createOrEditLabel" data-cy="MessageRecipientAttrsCreateUpdateHeading">
            <Translate contentKey="jhipsterVykyApp.messageRecipientAttrs.home.createOrEditLabel">
              Create or edit a MessageRecipientAttrs
            </Translate>
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
                  id="message-recipient-attrs-id"
                  label={translate('jhipsterVykyApp.messageRecipientAttrs.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterVykyApp.messageRecipientAttrs.isRead')}
                id="message-recipient-attrs-isRead"
                name="isRead"
                data-cy="isRead"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterVykyApp.messageRecipientAttrs.isDeleted')}
                id="message-recipient-attrs-isDeleted"
                name="isDeleted"
                data-cy="isDeleted"
                check
                type="checkbox"
              />
              <ValidatedField
                id="message-recipient-attrs-message"
                name="message"
                data-cy="message"
                label={translate('jhipsterVykyApp.messageRecipientAttrs.message')}
                type="select"
              >
                <option value="" key="0" />
                {messages
                  ? messages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="message-recipient-attrs-recipient"
                name="recipient"
                data-cy="recipient"
                label={translate('jhipsterVykyApp.messageRecipientAttrs.recipient')}
                type="select"
              >
                <option value="" key="0" />
                {vykyUsers
                  ? vykyUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/message-recipient-attrs" replace color="info">
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

export default MessageRecipientAttrsUpdate;
