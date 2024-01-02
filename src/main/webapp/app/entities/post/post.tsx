import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './post.reducer';

export const Post = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const postList = useAppSelector(state => state.post.entities);
  const loading = useAppSelector(state => state.post.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="post-heading" data-cy="PostHeading">
        <Translate contentKey="jhipsterVykyApp.post.home.title">Posts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterVykyApp.post.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/post/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterVykyApp.post.home.createLabel">Create new Post</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {postList && postList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterVykyApp.post.id">Id</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('version')}>
                  <Translate contentKey="jhipsterVykyApp.post.version">Version</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('version')} />
                </th>
                <th className="hand" onClick={sort('level')}>
                  <Translate contentKey="jhipsterVykyApp.post.level">Level</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('level')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="jhipsterVykyApp.post.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('content')}>
                  <Translate contentKey="jhipsterVykyApp.post.content">Content</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('content')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="jhipsterVykyApp.post.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('minimalRole')}>
                  <Translate contentKey="jhipsterVykyApp.post.minimalRole">Minimal Role</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('minimalRole')} />
                </th>
                <th className="hand" onClick={sort('parentId')}>
                  <Translate contentKey="jhipsterVykyApp.post.parentId">Parent Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('parentId')} />
                </th>
                <th className="hand" onClick={sort('upvotes')}>
                  <Translate contentKey="jhipsterVykyApp.post.upvotes">Upvotes</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('upvotes')} />
                </th>
                <th className="hand" onClick={sort('downvotes')}>
                  <Translate contentKey="jhipsterVykyApp.post.downvotes">Downvotes</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('downvotes')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="jhipsterVykyApp.post.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="jhipsterVykyApp.post.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th className="hand" onClick={sort('deletedAt')}>
                  <Translate contentKey="jhipsterVykyApp.post.deletedAt">Deleted At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deletedAt')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.post.topic">Topic</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.post.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.post.author">Author</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {postList.map((post, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/post/${post.id}`} color="link" size="sm">
                      {post.id}
                    </Button>
                  </td>
                  <td>{post.version}</td>
                  <td>{post.level}</td>
                  <td>{post.title}</td>
                  <td>{post.content}</td>
                  <td>
                    <Translate contentKey={`jhipsterVykyApp.PostStatus.${post.status}`} />
                  </td>
                  <td>
                    <Translate contentKey={`jhipsterVykyApp.Role.${post.minimalRole}`} />
                  </td>
                  <td>{post.parentId}</td>
                  <td>{post.upvotes}</td>
                  <td>{post.downvotes}</td>
                  <td>{post.createdAt ? <TextFormat type="date" value={post.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{post.updatedAt ? <TextFormat type="date" value={post.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{post.deletedAt ? <TextFormat type="date" value={post.deletedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{post.topic ? <Link to={`/topic/${post.topic.id}`}>{post.topic.id}</Link> : ''}</td>
                  <td>{post.parent ? <Link to={`/post/${post.parent.id}`}>{post.parent.id}</Link> : ''}</td>
                  <td>{post.author ? <Link to={`/vyky-user/${post.author.id}`}>{post.author.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/post/${post.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/post/${post.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/post/${post.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterVykyApp.post.home.notFound">No Posts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Post;
