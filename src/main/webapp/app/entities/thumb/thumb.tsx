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

import { getEntities } from './thumb.reducer';

export const Thumb = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const thumbList = useAppSelector(state => state.thumb.entities);
  const loading = useAppSelector(state => state.thumb.loading);

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
      <h2 id="thumb-heading" data-cy="ThumbHeading">
        <Translate contentKey="jhipsterVykyApp.thumb.home.title">Thumbs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterVykyApp.thumb.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/thumb/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterVykyApp.thumb.home.createLabel">Create new Thumb</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {thumbList && thumbList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterVykyApp.thumb.id">Id</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('version')}>
                  <Translate contentKey="jhipsterVykyApp.thumb.version">Version</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('version')} />
                </th>
                <th className="hand" onClick={sort('up')}>
                  <Translate contentKey="jhipsterVykyApp.thumb.up">Up</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('up')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="jhipsterVykyApp.thumb.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="jhipsterVykyApp.thumb.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th className="hand" onClick={sort('deletedAt')}>
                  <Translate contentKey="jhipsterVykyApp.thumb.deletedAt">Deleted At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deletedAt')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.thumb.post">Post</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterVykyApp.thumb.vykyUser">Vyky User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {thumbList.map((thumb, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/thumb/${thumb.id}`} color="link" size="sm">
                      {thumb.id}
                    </Button>
                  </td>
                  <td>{thumb.version}</td>
                  <td>{thumb.up ? 'true' : 'false'}</td>
                  <td>{thumb.createdAt ? <TextFormat type="date" value={thumb.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{thumb.updatedAt ? <TextFormat type="date" value={thumb.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{thumb.deletedAt ? <TextFormat type="date" value={thumb.deletedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{thumb.post ? <Link to={`/post/${thumb.post.id}`}>{thumb.post.id}</Link> : ''}</td>
                  <td>{thumb.vykyUser ? <Link to={`/vyky-user/${thumb.vykyUser.id}`}>{thumb.vykyUser.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/thumb/${thumb.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/thumb/${thumb.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/thumb/${thumb.id}/delete`)}
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
              <Translate contentKey="jhipsterVykyApp.thumb.home.notFound">No Thumbs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Thumb;
