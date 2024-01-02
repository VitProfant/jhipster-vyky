import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './post.reducer';

export const PostDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const postEntity = useAppSelector(state => state.post.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postDetailsHeading">
          <Translate contentKey="jhipsterVykyApp.post.detail.title">Post</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jhipsterVykyApp.post.id">Id</Translate>
            </span>
          </dt>
          <dd>{postEntity.id}</dd>
          <dt>
            <span id="version">
              <Translate contentKey="jhipsterVykyApp.post.version">Version</Translate>
            </span>
          </dt>
          <dd>{postEntity.version}</dd>
          <dt>
            <span id="level">
              <Translate contentKey="jhipsterVykyApp.post.level">Level</Translate>
            </span>
          </dt>
          <dd>{postEntity.level}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="jhipsterVykyApp.post.title">Title</Translate>
            </span>
          </dt>
          <dd>{postEntity.title}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="jhipsterVykyApp.post.content">Content</Translate>
            </span>
          </dt>
          <dd>{postEntity.content}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="jhipsterVykyApp.post.status">Status</Translate>
            </span>
          </dt>
          <dd>{postEntity.status}</dd>
          <dt>
            <span id="minimalRole">
              <Translate contentKey="jhipsterVykyApp.post.minimalRole">Minimal Role</Translate>
            </span>
          </dt>
          <dd>{postEntity.minimalRole}</dd>
          <dt>
            <span id="parentId">
              <Translate contentKey="jhipsterVykyApp.post.parentId">Parent Id</Translate>
            </span>
          </dt>
          <dd>{postEntity.parentId}</dd>
          <dt>
            <span id="upvotes">
              <Translate contentKey="jhipsterVykyApp.post.upvotes">Upvotes</Translate>
            </span>
          </dt>
          <dd>{postEntity.upvotes}</dd>
          <dt>
            <span id="downvotes">
              <Translate contentKey="jhipsterVykyApp.post.downvotes">Downvotes</Translate>
            </span>
          </dt>
          <dd>{postEntity.downvotes}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="jhipsterVykyApp.post.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{postEntity.createdAt ? <TextFormat value={postEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jhipsterVykyApp.post.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{postEntity.updatedAt ? <TextFormat value={postEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">
              <Translate contentKey="jhipsterVykyApp.post.deletedAt">Deleted At</Translate>
            </span>
          </dt>
          <dd>{postEntity.deletedAt ? <TextFormat value={postEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.post.topic">Topic</Translate>
          </dt>
          <dd>{postEntity.topic ? postEntity.topic.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.post.parent">Parent</Translate>
          </dt>
          <dd>{postEntity.parent ? postEntity.parent.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterVykyApp.post.author">Author</Translate>
          </dt>
          <dd>{postEntity.author ? postEntity.author.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/post" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostDetail;
