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

import { getEntities } from './vyky-user.reducer';

export const VykyUser = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vykyUserList = useAppSelector(state => state.vykyUser.entities);
  const loading = useAppSelector(state => state.vykyUser.loading);

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
      <h2 id="vyky-user-heading" data-cy="VykyUserHeading">
        <Translate contentKey="jhipsterVykyApp.vykyUser.home.title">Vyky Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterVykyApp.vykyUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/vyky-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterVykyApp.vykyUser.home.createLabel">Create new Vyky User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vykyUserList && vykyUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.id">Id</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('version')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.version">Version</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('version')} />
                </th>
                <th className="hand" onClick={sort('login')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.login">Login</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('login')} />
                </th>
                <th className="hand" onClick={sort('password')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.password">Password</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('password')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.email">Email</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('role')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.role">Role</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('role')} />
                </th>
                <th className="hand" onClick={sort('messagesReadAt')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.messagesReadAt">Messages Read At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('messagesReadAt')} />
                </th>
                <th className="hand" onClick={sort('reactionsReadAt')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.reactionsReadAt">Reactions Read At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('reactionsReadAt')} />
                </th>
                <th className="hand" onClick={sort('lastActivity')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.lastActivity">Last Activity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastActivity')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th className="hand" onClick={sort('deletedAt')}>
                  <Translate contentKey="jhipsterVykyApp.vykyUser.deletedAt">Deleted At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deletedAt')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vykyUserList.map((vykyUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vyky-user/${vykyUser.id}`} color="link" size="sm">
                      {vykyUser.id}
                    </Button>
                  </td>
                  <td>{vykyUser.version}</td>
                  <td>{vykyUser.login}</td>
                  <td>{vykyUser.password}</td>
                  <td>{vykyUser.email}</td>
                  <td>
                    <Translate contentKey={`jhipsterVykyApp.Role.${vykyUser.role}`} />
                  </td>
                  <td>
                    {vykyUser.messagesReadAt ? <TextFormat type="date" value={vykyUser.messagesReadAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {vykyUser.reactionsReadAt ? <TextFormat type="date" value={vykyUser.reactionsReadAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {vykyUser.lastActivity ? <TextFormat type="date" value={vykyUser.lastActivity} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{vykyUser.createdAt ? <TextFormat type="date" value={vykyUser.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{vykyUser.updatedAt ? <TextFormat type="date" value={vykyUser.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{vykyUser.deletedAt ? <TextFormat type="date" value={vykyUser.deletedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vyky-user/${vykyUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/vyky-user/${vykyUser.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vyky-user/${vykyUser.id}/delete`)}
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
              <Translate contentKey="jhipsterVykyApp.vykyUser.home.notFound">No Vyky Users found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default VykyUser;
