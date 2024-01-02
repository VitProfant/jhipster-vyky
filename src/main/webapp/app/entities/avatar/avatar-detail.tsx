import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './avatar.reducer';

export const AvatarDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const avatarEntity = useAppSelector(state => state.avatar.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="avatarDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.avatar.detail.title">Avatar</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.avatar.id">Id</Translate>
            </span>
          </dt>
          <dd>{avatarEntity.id}</dd>
          <dt>
            <span id="mime">
              <Translate contentKey="jhipsterVykyApp.avatar.mime">Mime</Translate>
            </span>
          </dt>
          <dd>{avatarEntity.mime}</dd>
          <dt>
            <span id="img">
              <Translate contentKey="jhipsterVykyApp.avatar.img">Img</Translate>
            </span>
          </dt>
          <dd>
            {avatarEntity.img ? (
              <div>
                {avatarEntity.imgContentType ? (
                  <a onClick={openFile(avatarEntity.imgContentType, avatarEntity.img)}>
                    <img src={`data:${avatarEntity.imgContentType};base64,${avatarEntity.img}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {avatarEntity.imgContentType}, {byteSize(avatarEntity.img)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="active">
              <Translate contentKey="jhipsterVykyApp.avatar.active">Active</Translate>
            </span>
          </dt>
          <dd>{avatarEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="activationAt">
              <Translate contentKey="jhipsterVykyApp.avatar.activationAt">Activation At</Translate>
            </span>
          </dt>
          <dd>
            {avatarEntity.activationAt ? <TextFormat value={avatarEntity.activationAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.avatar.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{avatarEntity.createdAt ? <TextFormat value={avatarEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.avatar.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>{avatarEntity.deletedAt ? <TextFormat value={avatarEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.avatar.vykyUser">Vyky User</Translate>
          </dt>
          <dd>{avatarEntity.vykyUser ? avatarEntity.vykyUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/avatar" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/avatar/${avatarEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AvatarDetail;
